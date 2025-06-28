package earth.terrarium.pastel.blocks.enchanter;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.block.MultiblockCrafter;
import earth.terrarium.pastel.api.block.PlayerOwned;
import earth.terrarium.pastel.blocks.InWorldInteractionBlockEntity;
import earth.terrarium.pastel.blocks.item_bowl.ItemBowlBlockEntity;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import earth.terrarium.pastel.capabilities.*;
import earth.terrarium.pastel.capabilities.item.*;
import earth.terrarium.pastel.helpers.Ench;
import earth.terrarium.pastel.helpers.ExperienceHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.networking.s2c_payloads.PlayBlockBoundSoundInstancePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import earth.terrarium.pastel.progression.PastelAdvancementCriteria;
import earth.terrarium.pastel.recipe.SimpleRecipeInput;
import earth.terrarium.pastel.recipe.enchanter.EnchanterCraftingRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchanterRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class EnchanterBlockEntity extends InWorldInteractionBlockEntity implements MultiblockCrafter, SidedCapabilityProvider {
	
	public static final String ITEM_TRANS = "container.pastel.rei.enchantment_upgrade.required_item_count";
	public static final String LEVEL_TRANS = "container.pastel.rei.enchantment_upgrade.level";
	public static final String OVERCHANTING_TOOLTIP = "container.pastel.rei.enchantment_upgrade.tooltip";
	public static final String CYCLING = "container.pastel.rei.enchantment_upgrade.button";
	
	public static final List<Vec3i> OFFSETS;
	public static final int CENTER = 0;
	public static final int XP_STORAGE = 1;
	
	protected UUID ownerUUID;
	protected boolean conflictEnchanting;
	protected boolean overenchanting;
	private boolean wasCrafting;

	protected FriendlyStackHandler inputs;

	private UpgradeHolder upgrades;
	
	private Optional<RecipeHolder<? extends EnchanterRecipe>> cachedRecipe = Optional.empty();
	private Optional<EnchantingData> enchData = Optional.empty();
	private int craftingTime;
	private EnchanterMode mode = EnchanterMode.IDLE;
	
	@Nullable
	private Direction itemFacing; // for rendering the item on the enchanter only
	
	public EnchanterBlockEntity(BlockPos pos, BlockState state) {
		super(PastelBlockEntities.ENCHANTER.get(), pos, state, XP_STORAGE + 1);
		this.inputs = new FriendlyStackHandler(10);
		this.inventory.addListener(i -> inventoryChanged());
	}

    private void tickServer() {
		if (level == null || level.isClientSide() || mode == EnchanterMode.IDLE)
			return;

		if (cachedRecipe.isEmpty() && mode.recipeBased) {
			clearRecipe();
			return;
		}
		else if(mode == EnchanterMode.ENCHANTING && enchData.isEmpty() && !findEnchantingData()) {
			clearRecipe();
			return;
		}

		wasCrafting = true;
		craftingTime -= upgrades.getSpeedDelta(level.getRandom());
		if (level.getGameTime() % 12 == 0)
			level.playSound(null, worldPosition, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS,
					0.8F * PastelCommon.CONFIG.BlockSoundVolume, 0.8F + level.getRandom().nextFloat() * 0.4F);

		if (craftingTime > 0) {
			return;
		}

		if (mode.recipeBased) {
			var recipe = cachedRecipe.get().value();
			finalizeCrafting(recipe);
		}
		else {
			finalizeEnchanting();
		}
	}

	private void tickClient() {
		if (level == null || mode == EnchanterMode.IDLE)
			return;

		var random = level.getRandom();
		if (level.random.nextFloat() < 0.2F) {
			float randomX = 0.2F + random.nextFloat() * 0.6F;
			float randomZ = 0.2F + random.nextFloat() * 0.6F;
			float randomY = -0.1F + random.nextFloat() * 0.4F;
			level.addParticle(ColoredCraftingParticleEffect.LIME, worldPosition.getX() + randomX, worldPosition.getY() + 2.5 + randomY, worldPosition.getZ() + randomZ, 0.0D, -0.1D, 0.0D);

		}

		if (level.getGameTime() % 12 == 0) {
			spawnCraftingOrbs();
		}
	}

	private void finalizeCrafting(EnchanterRecipe recipe) {
		double scaling = mode == EnchanterMode.UPGRADING ? getCenterLevel((EnchantmentUpgradeRecipe) recipe) : 1;

		var output = recipe.assemble(new SimpleRecipeInput(inputs), level.registryAccess());
		recipe.consumeIngredients(this, level.registryAccess(), scaling);

		if (!recipe.noDiscounts())
			output.setCount(Support.chanceRound(output.getCount()
					* upgrades.getEffectiveValue(UpgradeType.YIELD), level.random));

		var center = getCenterStack();
		if (center.getCount() > 1) {
			MultiblockCrafter.spawnItemStackAsEntitySplitViaMaxCount(level, worldPosition, output,
					output.getCount(), MultiblockCrafter.RECIPE_STACK_VELOCITY);
			center.shrink(1);
		}
		else {
			inputs.setStackInSlot(CENTER, output);
		}

		getOwner().ifPresent(p -> {
			if (mode == EnchanterMode.CRAFTING) {
                assert recipe instanceof EnchanterCraftingRecipe;
                PastelAdvancementCriteria.ENCHANTER_CRAFTING.trigger(p ,output, ((EnchanterCraftingRecipe) recipe)
                        .getRequiredExperience());
            }
			else
				PastelAdvancementCriteria.ENCHANTER_UPGRADING.trigger(p ,
						EnchantmentHelper.getEnchantmentsForCrafting(output),
						((EnchantmentUpgradeRecipe) recipe).getXpScaling().apply(scaling));
		});

		finalize(PastelSoundEvents.ENCHANTER_FINISH);
	}

	private void finalizeEnchanting() {
		if (enchData.isEmpty())
			throw new IllegalStateException("Enchantment finalization reached with empty enchantment data? (what?)");

		var data = enchData.get();
		var target = !getCenterStack().is(PastelItemTags.ENCHANTABLE_BOOKS) ?
				getCenterStack() : Items.ENCHANTED_BOOK.getDefaultInstance();

		// We force both as at this point the operation has been decided on.
		data.enchantments.forEach((holder, lv) ->
				Ench.addOrUpgradeEnchantment(target, holder, lv, true, true));

		inputs.setStackInSlot(CENTER, target);
		getXpStorage().ifPresent(h -> h.extract(data.xpCost, false));
		finalize(SoundEvents.ENCHANTMENT_TABLE_USE);

		updateVanillaStats(target, data.xpCost);
		getOwner().ifPresent(p ->
				PastelAdvancementCriteria.ENCHANTER_ENCHANTING.trigger(p ,target, data.xpCost));
	}

	private void updateRecipeAndMode() {
		if (findRecipe(PastelRecipeTypes.ENCHANTER)) {
			assert cachedRecipe.isPresent();
			craftingTime = cachedRecipe.get().value().getCraftingTime(1);
			mode = EnchanterMode.CRAFTING;
			enchData = Optional.empty();
		}
		else if (findRecipe(PastelRecipeTypes.ENCHANTMENT_UPGRADE)) {
			var recipe = (EnchantmentUpgradeRecipe) cachedRecipe.get().value();
			if (!canUpgrade(recipe)) {
				clearRecipe();
			}
			else {
				craftingTime = recipe.getCraftingTime(getCenterLevel(recipe)) * 5;
				mode = EnchanterMode.UPGRADING;
				enchData = Optional.empty();
			}
		}
		else if(findEnchantingData()) {
			assert enchData.isPresent();
			mode = EnchanterMode.ENCHANTING;
			craftingTime = enchData.get().enchantments.size() * 60;
			cachedRecipe = Optional.empty();
		}
		else {
			clearRecipe();
		}

		if (craftingTime > 0 && !wasCrafting)
			PlayBlockBoundSoundInstancePayload.sendPlayBlockBoundSoundInstance(PastelSoundEvents.ENCHANTER_WORKING,
					(ServerLevel) level, worldPosition, Integer.MAX_VALUE);
	}

	private boolean findEnchantingData() {
		var center = getCenterStack();

		if (center.isEmpty())
			return false;

		var gilded = center.is(PastelItems.GILDED_BOOK);
		var enchants = new Object2IntArrayMap<Holder<Enchantment>>();

		for (int slot = XP_STORAGE + 1; slot < 10; slot++) {
			var stack = inputs.getStackInSlot(slot);
			var isBook = stack.is(Items.ENCHANTED_BOOK);
			if (stack.isEmpty())
				continue;

			if (!gilded && !isBook)
				continue; // Only gilded books can draw from arbitrary items

			enchants.putAll(Ench.getUsableEnchants(stack, center, center.is(PastelItemTags.ENCHANTABLE_BOOKS), conflictEnchanting));

			if (gilded && !enchants.isEmpty())
				break; // Gilded book draws only from the first thing available
		}

		if (enchants.isEmpty())
			return false;

		var cost = 0;
		var enchantability = center.getEnchantmentValue();
		for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchants.object2IntEntrySet()) {
			cost += Ench.getEnchantmentCost(entry.getKey(), entry.getIntValue(), enchantability);
		}

		if (gilded)
			cost *= 2;

		int finalCost = cost;
		if (!getXpStorage().map(s -> s
				.extract(finalCost, true) == finalCost).orElse(false))
			return false;

		enchData = Optional.of(new EnchantingData(enchants, cost));
		return true;
	}

	private Optional<ExperienceHandler> getXpStorage() {
		assert level != null;
		return Optional.ofNullable(getXPStack().getCapability(PastelCapabilities.Misc.XP, level.registryAccess()));
	}

	private @NotNull ItemStack getCenterStack() {
		return inputs.getStackInSlot(CENTER);
	}

	private @NotNull ItemStack getXPStack() {
		return inputs.getStackInSlot(XP_STORAGE);
	}

	private void clearRecipe() {
		PlayBlockBoundSoundInstancePayload.sendCancelBlockBoundSoundInstance((ServerLevel) level, worldPosition);
		cachedRecipe = Optional.empty();
		enchData = Optional.empty();
		mode = EnchanterMode.IDLE;
		craftingTime = 0;
		wasCrafting = false;
	}

	private void updateVanillaStats(ItemStack resultStack, int experience) {
		int levels = ExperienceHelper.getLevelForExperience(experience);
		var player = getOwner();
		player.ifPresent(p -> {
			p.awardStat(Stats.ENCHANT_ITEM);
			CriteriaTriggers.ENCHANTED_ITEM.trigger(p, resultStack, levels);
		});
	}

	private Optional<ServerPlayer> getOwner() {
		return Optional.ofNullable((ServerPlayer) PlayerOwned.getPlayerEntityIfOnline(ownerUUID));
	}

	private void finalize(SoundEvent sound) {
		level.playSound(null, worldPosition,
				sound, SoundSource.BLOCKS, 1.334F, Support.varFloatCentered(level.random, 0.25F));

		PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
				(ServerLevel) level,
				worldPosition.getCenter(),
				ColoredSparkleRisingParticleEffect.LIME, 75, new Vec3(0.5D, 0.5D, 0.5D),
				new Vec3(0.1D, -0.1D, 0.1D));

		updateRecipeAndMode();
		syncInventories();
		updateInClientWorld();
	}

	public FriendlyStackHandler getInputs() {
		return inputs;
	}

	public UpgradeHolder getUpgrades() {
		return upgrades;
	}

	private void spawnCraftingOrbs() {
		assert level!=null;
		for (Vec3i offset : OFFSETS) {
            if (level.getBlockEntity(worldPosition.offset(offset)) instanceof ItemBowlBlockEntity bowl)
				bowl.spawnOrbParticles(worldPosition.getCenter());
		}
	}

	private boolean canUpgrade(EnchantmentUpgradeRecipe recipe) {
		var level = getCenterLevel(recipe);
		if (overenchanting)
			return level < recipe.getLevelCap();

		return recipe.isInNormalRange(level);
	}

	private boolean findRecipe(RecipeType<? extends EnchanterRecipe> type) {
        assert level!=null;
        cachedRecipe = (Optional<RecipeHolder<? extends EnchanterRecipe>>) (Object) level.getRecipeManager()
				.getRecipeFor(type, new SimpleRecipeInput(inputs), level);

		if (cachedRecipe.isPresent() && !cachedRecipe.get().value().canPlayerCraft(getOwnerIfOnline())) {
			cachedRecipe = Optional.empty();
		}

		return cachedRecipe.isPresent();
	}

	private int getCenterLevel(EnchantmentUpgradeRecipe recipe) {
		return getCenterStack()
				.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY)
				.getLevel(recipe.getEnchantment());
	}

	private void syncInventories() {
		if (level == null || level.isClientSide())
			return;

		inventory.getInternalList().set(CENTER, getCenterStack());
		inventory.getInternalList().set(XP_STORAGE, getXPStack());

		for (int slot = 2; slot < inputs.getSlots(); slot++) {
			setItemBowlStack(inputs.getStackInSlot(slot), level, bowlPos(slot));
		}

		updateInClientWorld();
	}

	public void inventoryChanged() {
		if (level == null || level.isClientSide())
			return;

		inputs.clear();
		inputs.setStackInSlot(CENTER, getItem(CENTER));
		inputs.setStackInSlot(XP_STORAGE, getItem(XP_STORAGE));

		for (int slot = 2; slot < inputs.getSlots(); slot++) {
			inputs.setStackInSlot(slot, getItemBowlStack(level, bowlPos(slot)));
		}

		updateRecipeAndMode();
		setChanged();
		updateInClientWorld();
	}
	
	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		this.craftingTime = nbt.getInt("crafting_time");
		mode = EnchanterMode.values()[nbt.getInt("mode")];

		this.conflictEnchanting = nbt.getBoolean("owner_can_apply_conflicting_enchantments");
		this.overenchanting = nbt.getBoolean("owner_can_overenchant");
		this.inputs = new FriendlyStackHandler(10);
		inputs.deserializeNBT(registryLookup, nbt.getCompound("inventory"));
		if (nbt.contains("item_facing", Tag.TAG_STRING)) {
			this.itemFacing = Direction.valueOf(nbt.getString("item_facing").toUpperCase(Locale.ROOT));
		}
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);

        cachedRecipe = (Optional<RecipeHolder<? extends EnchanterRecipe>>) (Object) Optional.ofNullable(MultiblockCrafter.getRecipeEntryFromNbt(level, nbt));
		
		if (nbt.contains("Upgrades", Tag.TAG_LIST)) {
			this.upgrades = UpgradeHolder.fromNbt(nbt.getList("Upgrades", Tag.TAG_COMPOUND));
		} else {
			this.upgrades = new UpgradeHolder();
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		nbt.putInt("crafting_time", this.craftingTime);
		nbt.putInt("mode", mode.ordinal());
		nbt.putBoolean("owner_conflictEnchants", this.conflictEnchanting);
		nbt.putBoolean("owner_overEnchants", this.overenchanting);
		nbt.put("inventory", inputs.serializeNBT(registryLookup));
		if (this.itemFacing != null) {
			nbt.putString("item_facing", this.itemFacing.toString().toUpperCase(Locale.ROOT));
		}
		if (this.upgrades != null) {
			nbt.put("Upgrades", this.upgrades.toNbt());
		}

		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);

        cachedRecipe.ifPresent(recipeHolder -> nbt.putString("CurrentRecipe", recipeHolder.id().toString()));
	}
	
	@Override
	public void updateInClientWorld() {
		if (level != null)
			level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), Block.UPDATE_INVISIBLE);
	}
	
	public Direction getItemFacingDirection() {
		// if placed via pipe or other sources
		return Objects.requireNonNullElse(this.itemFacing, Direction.NORTH);
	}
	
	public void setItemFacingDirection(Direction facingDirection) {
		this.itemFacing = facingDirection;
	}
	
	public ItemStack getItemBowlStack(Level level, BlockPos blockPos) {
		BlockEntity blockEntity = level.getBlockEntity(blockPos);
		if (blockEntity instanceof ItemBowlBlockEntity bowl) {
			return bowl.getItem(0);
		} else {
			return ItemStack.EMPTY;
		}
	}

	public void setItemBowlStack(ItemStack stack, Level level, BlockPos blockPos) {
		BlockEntity blockEntity = level.getBlockEntity(blockPos);
		if (blockEntity instanceof ItemBowlBlockEntity bowl) {
			bowl.setItem(0, stack);
			bowl.setChanged();
			bowl.updateInClientWorld();
		}
	}

	private @NotNull BlockPos bowlPos(int slot) {
		return worldPosition.offset(OFFSETS.get(slot - 2));
	}
	
	public void playSound(SoundEvent soundEvent, float volume) {
		if (level == null) return;
		level.playSound(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), soundEvent, SoundSource.BLOCKS, volume, 0.9F + level.random.nextFloat() * 0.15F);
	}
	
	// PLAYER OWNED
	// "owned" is not to be taken literally here. The owner
	// is always set to the last player interacted with to trigger advancements
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public void setOwner(Player playerEntity) {
		this.ownerUUID = playerEntity.getUUID();
		this.conflictEnchanting = AdvancementHelper.hasAdvancement(playerEntity, PastelAdvancements.APPLY_CONFLICTING_ENCHANTMENTS);
		this.overenchanting = AdvancementHelper.hasAdvancement(playerEntity, PastelAdvancements.OVERENCHANTING);
		setChanged();
	}
	
	// UPGRADEABLE
	@Override
	public void resetUpgrades() {
		this.upgrades = null;
		this.setChanged();
	}
	
	@Override
	public void calculateUpgrades() {
		this.upgrades = Upgradeable.calculateUpgradeMods4(level, worldPosition, 3, 0, this.ownerUUID);
		this.setChanged();
	}
	
	@Override
	public UpgradeHolder getUpgradeHolder() {
		return this.upgrades;
	}

	@Override
	public IItemHandler exposeItemHandlers(Direction dir) {
		return new StackHandlerView(inventory, 0);
	}

	public static void clientTick(Level level, BlockPos pos, BlockState state, EnchanterBlockEntity enchanter) {
		enchanter.tickClient();
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, EnchanterBlockEntity enchanter) {
		enchanter.tickServer();
	}

	private enum EnchanterMode {
		IDLE(false),
		CRAFTING(true),
		UPGRADING(true),
		ENCHANTING(false);

		private final boolean recipeBased;

        EnchanterMode(boolean recipeBased) {
            this.recipeBased = recipeBased;
        }
    }

	private record EnchantingData(Object2IntMap<Holder<Enchantment>> enchantments, int xpCost) {}

	static {
		OFFSETS = new ArrayList<>() {{
			add(new Vec3i(5, 0, -3));
			add(new Vec3i(5, 0, 3));
			add(new Vec3i(3, 0, 5));
			add(new Vec3i(-3, 0, 5));
			add(new Vec3i(-5, 0, 3));
			add(new Vec3i(-5, 0, -3));
			add(new Vec3i(-3, 0, -5));
			add(new Vec3i(3, 0, -5));
		}};
	}
}
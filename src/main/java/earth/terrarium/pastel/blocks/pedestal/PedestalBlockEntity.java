package earth.terrarium.pastel.blocks.pedestal;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.block.MultiblockCrafter;
import earth.terrarium.pastel.api.block.PedestalVariant;
import earth.terrarium.pastel.api.color.ItemColors;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.blocks.ActionableBlockEntity;
import earth.terrarium.pastel.blocks.Containerlike;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import earth.terrarium.pastel.capabilities.SidedCapabilityProvider;
import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import earth.terrarium.pastel.capabilities.item.StackHandlerView;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.inventories.PedestalScreenHandler;
import earth.terrarium.pastel.items.magic_items.CraftingTabletItem;
import earth.terrarium.pastel.networking.s2c_payloads.PlayBlockBoundSoundInstancePayload;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.progression.PastelCriteria;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PedestalBlockEntity extends ActionableBlockEntity implements MultiblockCrafter, SidedCapabilityProvider, MenuProvider, Containerlike {
	
	// 9 crafting, 5 powder, 1 craftingTablet, 1 output
	public static final int SIZE = 16;
	public static final int TABLET = 14;

	protected UUID owner;
	protected PedestalVariant variant;
	protected Optional<PedestalTier> tier = Optional.empty();
	protected UpgradeHolder upgrades;
	public Optional<RecipeHolder> recipe = Optional.empty();
	protected Optional<BlockCapabilityCache<IItemHandler, Direction>> cache = Optional.empty();

	protected int craftingTime = -1, totalTime = -1;
	protected int storedXp;
	protected boolean active, hadTablet;
	protected final ContainerData data;
	
	public PedestalBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(PastelBlockEntities.PEDESTAL.get(), blockPos, blockState, SIZE);
		inventory.addListener(i -> setChanged());
		refreshVariant();
		data = initData();
	}

	private void clientTick() {
		if (level == null || recipe.isEmpty() || !isActive())
			return;

		var random = level.getRandom();
		var particleColor = getPowderColor(random);

		if (!(recipe.get().value() instanceof PedestalRecipe pr)) {
			spawnParticles(ColoredCraftingParticleEffect.of(particleColor));
			return;
		}

		var itemColor = ItemColors.ITEM_COLORS.getMapping(pr.getResultItem(level.registryAccess()).getItem());
		spawnParticles(ColoredCraftingParticleEffect.of(
				itemColor.map(InkColor::getColorInt).orElse(particleColor)
		));
	}

	private int getPowderColor(RandomSource random) {
		var colors = new ArrayList<InkColor>();
		for (PastelGemstoneColor color : PastelGemstoneColor.values()) {
			if (!inventory.getStackInSlot(powderSlot(color)).isEmpty())
				colors.add(color.getInkColor());
		}

		if (colors.isEmpty())
			return 0xffd6b5;

        return colors.get(random.nextInt(colors.size())).getColorInt();
	}

	private void serverTick() {
		if (level == null)
			return;

		if (!active) {
			if (getBlockState().getValue(BlockStateProperties.POWERED))
				active = true;
			else
				return;
		}

		if (this.upgrades == null)
			calculateUpgrades();

		var input = getInput();
		if (recipe.isEmpty() && craftingTime > -1) {
			findRecipe(input);
		}

		if (recipe.isEmpty() || craftingTime < 0) {
			return;
		}

		if (craftingTime > 0) {
			craftingTime--;
			return;
		}

		finalizeCrafting(input);
	}

	private void finalizeCrafting(PedestalRecipeInput input) {
		assert level != null;
		assert recipe.isPresent();
		var output = actionCraft(input);

		if (recipe.get().value() instanceof PedestalRecipe pr && pr.isStructureUpgrade(level.registryAccess())) {
			consumeAndPlaySound(input, pr);
			upgrade(pr, input);
			setChanged();
			craftingTime = -1;
			totalTime = -1;
			active = false;
			return;
		}

		if (!outputToStorage(output)) {
			var pos = getBlockPos().getCenter();
			var entity = new ItemEntity(level, pos.x, pos.y + 1, pos.z, output,
					0, level.random.nextDouble() * 0.2 + 0.2, 0);
			level.addFreshEntity(entity);
		}

		consumeAndPlaySound(input, recipe.get().value());
		setChanged();
		updateInClientWorld();
		setRecipeTimings();
	}

	private void upgrade(PedestalRecipe recipe, PedestalRecipeInput input) {
		assert level != null;

		recipe.consumeIngredients(this, input);
		var upgrade = (PedestalBlockItem) recipe.getResultItem(level.registryAccess()).getItem();
		PedestalBlock.upgradeTo(level, getBlockPos(), getBlockState(), upgrade.getVariant());
		level.playSound(null, getBlockPos(), PastelSounds.PEDESTAL_UPGRADE,
				SoundSource.BLOCKS, 1.5F, 1);
	}

	private ItemStack actionCraft(PedestalRecipeInput input) {
		assert recipe.isPresent();
		assert level != null;

		boolean doYield;
		var rec = recipe.get().value();
		float xp;

		var out = ItemStack.EMPTY;
		if (rec instanceof PedestalRecipe pr) {
			out = rec.assemble(input, level.registryAccess());
			doYield = pr.allowsYield();
			xp = pr.getExperience();
		}
		else {
			out = rec.assemble(input.getCraftingGridInput(), level.registryAccess());
			doYield = !isDupe(input.getCraftingGridInput(), (CraftingRecipe) rec, out);
			xp = 1;
		}

		if (doYield)
			out.setCount(Support.chanceRound(
					out.getCount()
							* upgrades.getEffectiveValue(UpgradeType.YIELD), level.random
			));

        xp *= upgrades.getEffectiveValue(UpgradeType.EXPERIENCE);
        if (level instanceof ServerLevel sl) {
            var finalXp = xp;
            var finalOut = out;

            Support.areaCriterion(sl, Support.H_RANGE, getBlockPos(), getTier().unlockAdvancementId, p ->
                PastelCriteria.PEDESTAL_CRAFTING.trigger(p, finalOut, (int) finalXp, totalTime));
        }

		storedXp += Support.chanceRound(xp, level.random);
		return out;
	}

	private void consumeAndPlaySound(PedestalRecipeInput input, Recipe<?> rec) {
		var sound = PastelSounds.PEDESTAL_CRAFTING_FINISHED_GENERIC;
		if (rec instanceof PedestalRecipe pr) {
			pr.consumeIngredients(this, input);
			sound = pr.getSoundEvent(level.random);
		}
		else {
			for (int i = 0; i < 9; i++) {
				inventory.extractItem(i, 1, false);
			}
		}

		level.playSound(null, getBlockPos(), sound, SoundSource.BLOCKS, 1F,
				Support.varFloatCentered(level.random, 0.15F));
	}

	private boolean isDupe(CraftingInput input, CraftingRecipe recipe, ItemStack out) {
		if (!out.isStackable() || out.getRarity().ordinal() > 1)
			return true;

		if (recipe.category() == CraftingBookCategory.EQUIPMENT)
			return true;

		if (input.size() == 1 && input.getItem(0).is(PastelItemTags.PRODUCTIVITY_EXCLUDED))
			return true;

		return out.is(PastelItemTags.PRODUCTIVITY_EXCLUDED);
	}

	private void updateCache() {
		if (cache.isPresent() && cache.get().getCapability() != null)
			return;

		cache = Optional.of(BlockCapabilityCache.create(
				Capabilities.ItemHandler.BLOCK,
				(ServerLevel) level,
				getBlockPos().below(),
				Direction.UP,
				() -> !this.isRemoved(),
				() -> cache = Optional.empty()
		));

		if (cache.get().getCapability() != null)
			return;

		cache = Optional.of(BlockCapabilityCache.create(
				Capabilities.ItemHandler.BLOCK,
				(ServerLevel) level,
				getBlockPos().above(),
				Direction.DOWN,
				() -> !this.isRemoved(),
				() -> cache = Optional.empty()
		));
	}

	private boolean outputToStorage(ItemStack output) {
		if (cache.isEmpty())
			updateCache();

		var cap = cache.get().getCapability();
		if (cap == null)
			return false;

		if (ItemHandlerHelper.insertItemStacked(cap, output, true).isEmpty()) {
			ItemHandlerHelper.insertItemStacked(cap, output, false);
			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	private void findRecipe(PedestalRecipeInput input) {
        assert level!=null;
        recipe = (Optional<RecipeHolder>) (Object) level.getRecipeManager()
				.getRecipeFor(PastelRecipeTypes.PEDESTAL, input, level);

		if (recipe.isEmpty()) {
			recipe = (Optional<RecipeHolder>) (Object) level.getRecipeManager()
					.getRecipeFor(RecipeType.CRAFTING, input.getCraftingGridInput(), level);
		}

		if (!verifyRecipe())
			recipe = Optional.empty();
	}

	private boolean verifyRecipe() {
		if (recipe.isEmpty())
			return false;

		var tablet = tabletRecipe();
		if (tablet.map(t -> !t.id().equals(recipe.get().id())).orElse(false))
			return false;

		assert level != null;
		var rec = recipe.get().value();

		if (rec instanceof PedestalRecipe pr) {
            return pr.canCraft(this);
		}

		return true;
	}

	@NotNull
    public PedestalRecipeInput getInput() {
		return PedestalRecipeInput.create(inventory.getInternalList(), getOwnerIfOnline());
	}

	private void updateRecipe() {
		assert level != null;
		var input = getInput();

		if (tier.isEmpty())
			tier = Optional.of(PedestalTier.getTier(this));

		if (upgrades == null)
			calculateUpgrades();

		if (recipe.filter(r -> {
			var rec = r.value();
			if (rec instanceof CraftingRecipe)
				return rec.matches(input.getCraftingGridInput(), level);
			return rec.matches(input, level);

		}).isPresent() && verifyRecipe())
			return;

		tier = Optional.of(PedestalTier.getTier(this));
		findRecipe(input);
		setRecipeTimings();

		if (active && craftingTime == -1) {
			setActive(false);
		}
	}

	private void setRecipeTimings() {
		if (recipe.isEmpty()) {
			craftingTime = -1;
			totalTime = -1;
			return;
		}

		var rec = recipe.get();

		if (rec.value() instanceof PedestalRecipe pr) {
			craftingTime = pr.getCraftingTime();
		}
		else {
			craftingTime = PastelCommon.CONFIG.VanillaRecipeCraftingTimeTicks;
		}

		craftingTime = adjustCraftingTime(craftingTime);
		totalTime = craftingTime;
	}

	private int adjustCraftingTime(int base) {
		return Math.round(base / upgrades.getEffectiveValue(UpgradeType.SPEED));
	}

	public void giveStoredXp(Player target) {
		if (storedXp == 0)
			return;

		assert level != null;
		target.giveExperiencePoints(storedXp);
		storedXp = 0;
		level.playSound(null, getBlockPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS,
				0.3F, 1);
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (level != null && !level.isClientSide()) {
			updateRecipe();
			updateInClientWorld();
		}
	}

	@Override
	public UUID getOwnerUUID() {
		return owner;
	}

	@Override
	public void setOwner(Player player) {
		this.owner = player.getUUID();
	}

	@Override
	public void resetUpgrades() {
		upgrades = null;
		setChanged();
	}

	@Override
	public void calculateUpgrades() {
		upgrades = Upgradeable.calculateUpgradeMods4(level, worldPosition, 3, 2, owner);
		setChanged();
	}

	@Override
	public UpgradeHolder getUpgradeHolder() {
		return upgrades;
	}

	public static int powderSlot(GemstoneColor gemstoneColor) {
		return 9 + gemstoneColor.getOffset();
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.pastel.pedestal");
	}

	public void refreshVariant() {
		if (getBlockState().getBlock() instanceof PedestalBlock pedestalBlock) {
			this.variant = pedestalBlock.getVariant();
		} else {
			this.variant = PedestalVariants.BASIC_AMETHYST;
		}
	}

	private void spawnParticles(ParticleOptions particle) {
		assert level != null;
		var random = level.getRandom();
		var pos = getBlockPos().getCenter()
				.add(
						random.nextDouble() - 0.5,
						0.5,
						random.nextDouble() - 0.5
				);

		level.addParticle(
				particle, pos.x, pos.y, pos.z, 0.0D,
				0.03D, 0.0D
		);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;

		if (active) {
			PlayBlockBoundSoundInstancePayload.sendPlayBlockBoundSoundInstance(
					PastelSounds.PEDESTAL_CRAFTING, (ServerLevel) getLevel(),
					getBlockPos(), Integer.MAX_VALUE
			);
		}
		else {
			PlayBlockBoundSoundInstancePayload.sendCancelBlockBoundSoundInstance((ServerLevel) level, getBlockPos());
		}
	}

	public PedestalTier getTier() {
		return tier.orElse(PedestalTier.BASIC);
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.saveAdditional(tag, registries);
		if (this.upgrades != null) {
			tag.put("Upgrades", this.upgrades.toNbt());
		}

		tag.putBoolean("active", active);
		tag.putInt("craftingTime", craftingTime);
		recipe.ifPresent(recipeHolder -> tag.putString(
				"CurrentRecipe", recipeHolder.id()
						.toString()
		));

	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		super.loadAdditional(tag, registries);
		if (tag.contains("Upgrades", Tag.TAG_LIST)) {
			this.upgrades = UpgradeHolder.fromNbt(tag.getList("Upgrades", Tag.TAG_COMPOUND));
		} else {
			this.upgrades = new UpgradeHolder();
		}

		active = tag.getBoolean("active");
		craftingTime = tag.getInt("craftingTime");
		totalTime = craftingTime;
		recipe = Optional.ofNullable(
				MultiblockCrafter.getRecipeEntryFromNbt(level, tag));
	}

	@Override
	public void containerChanged() {
		inventoryChanged();
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new PedestalScreenHandler(containerId, playerInventory, this,
				data, variant.getRecipeTier());
	}

	@Override
	public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
		PedestalScreenHandler.ScreenOpeningData.STREAM_CODEC.encode(buffer,
				new PedestalScreenHandler.ScreenOpeningData(getBlockPos(), variant.getRecipeTier()));
	}

	public PedestalVariant getVariant() {
		return variant;
	}

	@Override
	public FriendlyStackHandler getInventory() {
		return inventory;
	}

	@Override
	public IItemHandler exposeItemHandlers(Direction dir) {
		if (dir.getAxis().isHorizontal()) {
            return powderTabletView();
		}

		if (dir == Direction.DOWN)
			return null;

		return new PedestalCraftingView(this, inventory);
	}

	protected List<Ingredient> getTabletIngredients() {
		var autoRecipe = tabletRecipe();

		if (autoRecipe.isEmpty() || !(autoRecipe.get().value() instanceof CraftingRecipe cr))
			return Collections.emptyList();

		return cr.getIngredients();
	}

	private Optional<RecipeHolder<?>> tabletRecipe() {
		return Optional.ofNullable(CraftingTabletItem.getStoredRecipe(level, inventory.getStackInSlot(TABLET)));
	}

	private @NotNull StackHandlerView powderTabletView() {
		var view = new StackHandlerView(inventory, 9, 6);

		var permittedColors = Arrays.asList(variant.getRecipeTier().gemstoneColors());
		for (PastelGemstoneColor color : PastelGemstoneColor.values()) {
			var index = powderSlot(color) - 9;

			if (!permittedColors.contains(color)) {
				view.addFilter(index);
				continue;
			}

			view.addFilter(index, s -> s.is(color.getPowder()));
		}

		view.addFilter(TABLET - 9, s -> s.is(PastelItems.CRAFTING_TABLET));
		return view;
	}

	@SuppressWarnings("unused")
	public static void clientTick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity pedestal) {
		pedestal.clientTick();
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity pedestal) {
		pedestal.serverTick();
	}

	private @NotNull ContainerData initData() {
		final ContainerData data;
		data = new ContainerData() {
			@Override
			public int get(int index) {
				if (index == 1)
					return totalTime;

				return totalTime - craftingTime;
			}

			@Override
			public void set(int index, int value) {}

			@Override
			public int getCount() {
				return 2;
			}
		};
		return data;
	}
}

package earth.terrarium.pastel.blocks.titration_barrel;

import earth.terrarium.pastel.api.block.FluidTankInventory;
import earth.terrarium.pastel.capabilities.*;
import earth.terrarium.pastel.capabilities.fluid.*;
import earth.terrarium.pastel.capabilities.item.*;
import earth.terrarium.pastel.helpers.*;
import earth.terrarium.pastel.mixin.accessors.BiomeAccessor;
import earth.terrarium.pastel.progression.SpectrumAdvancementCriteria;
import earth.terrarium.pastel.recipe.FluidRecipeInput;
import earth.terrarium.pastel.recipe.titration_barrel.ITitrationBarrelRecipe;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import earth.terrarium.pastel.registries.SpectrumRecipeTypes;
import net.minecraft.core.*;
import net.neoforged.neoforge.fluids.FluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.*;
import net.neoforged.neoforge.fluids.capability.templates.*;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Optional;

import static earth.terrarium.pastel.blocks.titration_barrel.TitrationBarrelBlock.BARREL_STATE;

// NOTE: ALWAYS DENIES IO. NON-AUTOMATABLE
public class TitrationBarrelBlockEntity extends BlockEntity implements FluidTankInventory, SidedCapabilityProvider {
	
	protected static final int INVENTORY_SIZE = 5;
	public static final int MAX_ITEM_COUNT = 64;
	protected FriendlyStackHandler inventory;
	protected FluidTank tank = new FluidTank(1000);
	
	@Override
	public FriendlyStackHandler getInventory() {
		return this.inventory;
	}
	
	@Override
	public FluidTank getTank() {
		return this.tank;
	}
	
	// Times in milliseconds using the Date class
	protected long sealTime = -1;
	protected long tapTime = -1;
	
	protected String recipe;
	protected int extractedBottles = 0;
	
	public TitrationBarrelBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.TITRATION_BARREL, pos, state);
		this.inventory = new FriendlyStackHandler(INVENTORY_SIZE);
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		inventory.save(nbt, registryLookup);
		tank.writeToNBT(registryLookup, nbt);
		nbt.putLong("SealTime", this.sealTime);
		nbt.putLong("TapTime", this.tapTime);
		nbt.putInt("ExtractedBottles", this.extractedBottles);
	}
	
	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		inventory.load(nbt, registryLookup);
		tank.readFromNBT(registryLookup, nbt);
		this.sealTime = nbt.contains("SealTime", Tag.TAG_LONG) ? nbt.getLong("SealTime") : -1;
		this.tapTime = nbt.contains("TapTime", Tag.TAG_LONG) ? nbt.getLong("TapTime") : -1;
		this.extractedBottles = nbt.contains("ExtractedBottles", Tag.TAG_ANY_NUMERIC) ? nbt.getInt("ExtractedBottles") : 0;
	}
	
	public void seal() {
		this.sealTime = new Date().getTime();
		this.setChanged();
	}
	
	public void tap() {
		this.tapTime = new Date().getTime();
		this.setChanged();
	}
	
	public void reset(Level world, BlockPos blockPos, BlockState state) {
		this.sealTime = -1;
		this.tapTime = -1;
		tank.setFluid(FluidStack.EMPTY);
		this.extractedBottles = 0;
		this.getInventory().getInternalList().clear();
		
		world.setBlockAndUpdate(worldPosition, state.setValue(BARREL_STATE, TitrationBarrelBlock.BarrelState.EMPTY));
		world.playSound(null, blockPos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
		setChanged();
	}
	
	public long getSealMilliseconds() {
		if (this.sealTime == -1) {
			return 0;
		}
		
		long tapTime;
		if (this.tapTime == -1) {
			tapTime = new Date().getTime();
		} else {
			tapTime = this.tapTime;
		}
		return tapTime - this.sealTime;
	}
	
	public long getSealSeconds() {
		return getSealMilliseconds() / 1000;
	}
	
	public int getSealMinecraftDays() {
		return (int) (getSealMilliseconds() / 1000 / 60 / 20);
	}
	
	public String getSealRealDays() {
		return Support.getWithOneDecimalAfterComma(getSealMilliseconds() / 1000F / 60 / 20 / 72);
	}
	
	private boolean isEmpty(float temperature, int extractedBottles, ITitrationBarrelRecipe recipe) {
		if (level == null || !recipe.getFluidInput().test(getTank().getFluid())) {
			return true;
		}
		return extractedBottles >= recipe.getOutputCountAfterAngelsShare(this.level, temperature, getSealSeconds());
	}
	
	public void addOneDayOfSealTime() {
		this.sealTime -= TimeHelper.EPOCH_DAY_MILLIS;
		this.setChanged();
	}
	
	public ItemStack tryHarvest(Level world, BlockPos blockPos, BlockState blockState, ItemStack handStack, @Nullable Player player) {
		ItemStack harvestedStack = ItemStack.EMPTY;
		Biome biome = world.getBiome(blockPos).value();
		
		boolean shouldReset = false;
		Component message = null;
		
		int daysSealed = getSealMinecraftDays();
		int inventoryCount = InventoryHelper.countItemsInInventory(this.getInventory());
		
		Optional<RecipeHolder<ITitrationBarrelRecipe>> optionalRecipe = getRecipeForInventory(world);
		if (optionalRecipe.isEmpty()) {
			if (getInventory().isEmpty() && tank.isEmpty()) {
				message = Component.translatable("block.pastel.titration_barrel.empty_when_tapping");
			} else {
				message = Component.translatable("block.pastel.titration_barrel.invalid_recipe_when_tapping");
			}
			shouldReset = true;
		} else {
			ITitrationBarrelRecipe recipe = optionalRecipe.get().value();
			
			long secondsFermented = (this.tapTime - this.sealTime) / 1000;
			var output = recipe.getOutputCountAfterAngelsShare(world, biome.getBaseTemperature(), secondsFermented);
			
			if (recipe.getFluidInput().test(tank.getFluid())) {
				if (recipe.canPlayerCraft(player)) {
					boolean canTap = true;
					Item tappingItem = recipe.getTappingItem();
					if (tappingItem != Items.AIR) {
						if (handStack.is(tappingItem)) {
							output = Math.min(output, handStack.getCount());
							handStack.shrink(output);
						} else {
							message = Component.translatable("block.pastel.titration_barrel.tapping_item_required").append(tappingItem.getDescription());
							canTap = false;
						}
					}
					if (canTap) {
						float downfall = ((BiomeAccessor) (Object) biome).getClimateSettings().downfall();
						harvestedStack = recipe.getTitrationResult(this.inventory, secondsFermented, downfall);
						harvestedStack.setCount(output);
						
						this.extractedBottles += output;
						shouldReset = isEmpty(biome.getBaseTemperature(), this.extractedBottles, recipe);
					}
				} else {
					message = Component.translatable("block.pastel.titration_barrel.recipe_not_unlocked");
				}
			} else {
				if (tank.isEmpty()) {
					message = Component.translatable("block.pastel.titration_barrel.missing_liquid_when_tapping");
				} else {
					message = Component.translatable("block.pastel.titration_barrel.invalid_recipe_when_tapping");
				}
				shouldReset = true;
			}
		}
		
		if (player != null) {
			SpectrumAdvancementCriteria.TITRATION_BARREL_TAPPING.trigger((ServerPlayer) player, harvestedStack, daysSealed, inventoryCount);

            player.displayClientMessage(message, true);
        }
		
		if (shouldReset) {
			reset(world, blockPos, blockState);
		}
		
		this.setChanged();
		
		return harvestedStack;
	}
	
	public Optional<RecipeHolder<ITitrationBarrelRecipe>> getRecipeForInventory(Level world) {
		return world.getRecipeManager().getRecipeFor(SpectrumRecipeTypes.TITRATION_BARREL, getRecipeInput(), world);
	}
	
	public FluidRecipeInput<FluidTank> getRecipeInput() {
		return new FluidRecipeInput<>(inventory.getInternalList(), tank);
	}
	
	public void giveRecipeRemainders(Player player) {
		for (ItemStack stack : getInventory().getInternalList()) {
			ItemStack remainder = stack.getCraftingRemainingItem();
			if (!remainder.isEmpty()) {
				player.getInventory().placeItemBackInInventory(remainder);
			}
		}
	}
	
	public boolean canBeSealed(Player player) {
		int itemCount = InventoryHelper.countItemsInInventory(getInventory());
		if (itemCount == 0 && tank.isEmpty()) {
			return true; // tap empty barrel advancement
		}
		
		if (level != null) {
			Optional<RecipeHolder<ITitrationBarrelRecipe>> optionalRecipe = getRecipeForInventory(level);
			return optionalRecipe.isPresent()
					&& optionalRecipe.get().value().canPlayerCraft(player)
					&& optionalRecipe.get().value().getFluidInput().test(tank.getFluid());
		}
		
		return false;
	}

	@Override
	public IItemHandler exposeItemHandlers(Direction dir) {
		return new StackHandlerView(inventory).disableExtraction().disableInsertion();
	}

	@Override
	public IFluidHandler exposeFluidHandlers(Direction dir) {
		return new FluidHandlerView(tank).disableExtraction().disableInsertion();
	}
}

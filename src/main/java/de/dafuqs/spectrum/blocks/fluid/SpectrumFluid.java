package de.dafuqs.spectrum.blocks.fluid;

import de.dafuqs.spectrum.api.block.MultiblockCrafter;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import de.dafuqs.spectrum.recipe.fluid_converting.FluidConvertingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class SpectrumFluid extends FlowingFluid {
	
	@Override
	public boolean isSame(Fluid fluid) {
		return fluid == getSource() || fluid == getFlowing();
	}
	
	@Override
	protected boolean canConvertToSource(Level world) {
		return false;
	}
	
	/**
	 * Perform actions when fluid flows into a replaceable block.
	 * => Drop the block
	 */
	@Override
	protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
		final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropResources(state, world, pos, blockEntity);
	}
	
	/**
	 * Lava returns true if its FluidState is above a certain height and the Fluid is Water.
	 *
	 * @return if the given Fluid can flow into this FluidState?
	 */
	@Override
	protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
		return false;
	}
	
	/**
	 * Possibly related to the distance checks for flowing into nearby holes?
	 * Water returns 4. Lava returns 2 in the Overworld and 4 in the Nether.
	 */
	@Override
	protected int getSlopeFindDistance(LevelReader worldView) {
		return 3;
	}
	
	/**
	 * Water returns 1. Lava returns 2 in the Overworld and 1 in the Nether.
	 */
	@Override
	protected int getDropOff(LevelReader worldView) {
		return 1;
	}
	
	/**
	 * Water returns 5. Lava returns 30 in the Overworld and 10 in the Nether.
	 */
	@Override
	public int getTickDelay(LevelReader worldView) {
		return 20;
	}
	
	/**
	 * Water and Lava both return 100.0F.
	 */
	@Override
	protected float getExplosionResistance() {
		return 100.0F;
	}
	
	@Override
	public Optional<SoundEvent> getPickupSound() {
		return Optional.of(SoundEvents.BUCKET_FILL);
	}
	
	public abstract ParticleOptions getSplashParticle();
	
	public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
		if (!world.isClientSide) {
			if (entity instanceof ItemEntity itemEntity && !itemEntity.hasPickUpDelay() && !itemEntity.isRemoved()) {
				if (world.random.nextInt(100) == 0) {
					ItemStack itemStack = itemEntity.getItem();
					FluidConvertingRecipe recipe = getConversionRecipeFor(getDippingRecipeType(), world, itemStack);
					if (recipe != null && !recipe.getResultItem(world.registryAccess()).is(itemStack.getItem())) { // do not try to convert items into itself for performance reasons
						world.playSound(null, itemEntity.blockPosition(), SoundEvents.WOOL_BREAK, SoundSource.NEUTRAL, 1.0F, 0.9F + world.getRandom().nextFloat() * 0.2F);
						
						ItemStack result = assemble(recipe, itemStack, world);
						int count = result.getCount() * itemStack.getCount();
						result.setCount(count);
						
						if (itemEntity.getOwner() instanceof ServerPlayer serverPlayerEntity) {
							SpectrumAdvancementCriteria.FLUID_DIPPING.trigger(serverPlayerEntity, (ServerLevel) world, pos, itemStack, result);
						}
						
						itemEntity.discard();
						MultiblockCrafter.spawnItemStackAsEntitySplitViaMaxCount(world, itemEntity.position(), result, count, Vec3.ZERO, false, itemEntity.getOwner());
					}
				}
			}
		}
	}
	
	public abstract RecipeType<? extends FluidConvertingRecipe> getDippingRecipeType();
	
	public @Nullable <R extends FluidConvertingRecipe> R getConversionRecipeFor(RecipeType<R> recipeType, @NotNull Level world, ItemStack itemStack) {
		RecipeHolder<R> entry = world.getRecipeManager().getRecipeFor(recipeType, new SingleRecipeInput(itemStack), world).orElse(null);
		return entry == null ? null : entry.value();
	}
	
	public ItemStack assemble(FluidConvertingRecipe recipe, ItemStack itemStack, Level world) {
		return recipe.assemble(new SingleRecipeInput(itemStack), world.registryAccess());
	}
	
}
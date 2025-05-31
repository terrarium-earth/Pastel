package earth.terrarium.pastel.blocks.fluid;

import earth.terrarium.pastel.particle.SpectrumParticleTypes;
import earth.terrarium.pastel.recipe.fluid_converting.FluidConvertingRecipe;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import earth.terrarium.pastel.registries.SpectrumFluidTags;
import earth.terrarium.pastel.registries.SpectrumFluids;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumRecipeTypes;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class GooFluid extends SpectrumFluid {
	
	@Override
	public Fluid getSource() {
		return SpectrumFluids.GOO.get();
	}
	
	@Override
	public Fluid getFlowing() {
		return SpectrumFluids.FLOWING_GOO.get();
	}
	
	@Override
	public Item getBucket() {
        return SpectrumItems.GOO_BUCKET.get();
	}

	@Override
	public FluidType getFluidType() {
		return SpectrumFluids.GOO_TYPE.get();
	}

	@Override
	protected BlockState createLegacyBlock(FluidState fluidState) {
		return SpectrumBlocks.GOO.get().defaultBlockState().setValue(BlockStateProperties.LEVEL, getLegacyLevel(fluidState));
	}
	
	@Override
	public boolean isSame(Fluid fluid) {
		return fluid == SpectrumFluids.GOO.get() || fluid == SpectrumFluids.FLOWING_GOO.get();
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
		BlockPos topPos = pos.above();
		BlockState topState = world.getBlockState(topPos);
		if (topState.isAir() && !topState.isSolidRender(world, topPos) && random.nextInt(1000) == 0) {
			world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SpectrumSoundEvents.GOO_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
		}
	}
	
	@Override
	protected int getSlopeFindDistance(LevelReader worldView) {
		return 1;
	}
	
	@Override
	protected int getDropOff(LevelReader worldView) {
		return 3;
	}
	
	@Override
	public int getTickDelay(LevelReader worldView) {
		return 50;
	}
	
	@Override
	public ParticleOptions getDripParticle() {
		return SpectrumParticleTypes.DRIPPING_GOO;
	}
	
	@Override
	public ParticleOptions getSplashParticle() {
		return SpectrumParticleTypes.GOO_SPLASH;
	}
	
	@Override
	public RecipeType<? extends FluidConvertingRecipe> getDippingRecipeType() {
		return SpectrumRecipeTypes.GOO_CONVERTING;
	}
	
	/**
	 * Entities colliding with goo will get a slowness effect
	 * and losing their breath far quicker
	 */
	@Override
	public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
		
		if (!world.isClientSide && entity instanceof LivingEntity livingEntity) {
			// the entity is hurt at air == -20 and then reset to air = 0
			// this way the entity loses its breath way faster, but gets damaged just as slow afterwards
			if (livingEntity.isEyeInFluid(SpectrumFluidTags.GOO) && world.getGameTime() % 2 == 0 && livingEntity.getAirSupply() > 0) {
				livingEntity.setAirSupply(livingEntity.getAirSupply() - 1);
			}
			
			// just check every 20 ticks for performance
			if (world.getGameTime() % 20 == 0) {
				MobEffectInstance slownessInstance = livingEntity.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
				if (slownessInstance == null || slownessInstance.getDuration() < 20) {
					MobEffectInstance newSlownessInstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3);
					livingEntity.addEffect(newSlownessInstance);
				}
			}
		}
	}
	
	public static class FlowingGoo extends GooFluid {
		
		@Override
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}
		
		@Override
		public int getAmount(FluidState fluidState) {
			return fluidState.getValue(LEVEL);
		}
		
		@Override
		public boolean isSource(FluidState fluidState) {
			return false;
		}
		
	}
	
	public static class StillGoo extends GooFluid {
		
		@Override
		public int getAmount(FluidState fluidState) {
			return 8;
		}
		
		@Override
		public boolean isSource(FluidState fluidState) {
			return true;
		}
		
	}
}
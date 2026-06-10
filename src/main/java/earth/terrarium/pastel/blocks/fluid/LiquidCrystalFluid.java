package earth.terrarium.pastel.blocks.fluid;

import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.recipe.fluid_converting.FluidConvertingRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFluids;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class LiquidCrystalFluid extends PastelFluid {

    @Override
    public Fluid getSource() {
        return PastelFluids.LIQUID_CRYSTAL.get();
    }

    @Override
    public Fluid getFlowing() {
        return PastelFluids.FLOWING_LIQUID_CRYSTAL.get();
    }

    @Override
    public Item getBucket() {
        return PastelItems.LIQUID_CRYSTAL_BUCKET.get();
    }

    @Override
    public FluidType getFluidType() {
        return PastelFluids.LIQUID_CRYSTAL_TYPE.get();
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return PastelBlocks.LIQUID_CRYSTAL.get()
                                          .defaultBlockState()
                                          .setValue(BlockStateProperties.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == PastelFluids.LIQUID_CRYSTAL.get() || fluid == PastelFluids.FLOWING_LIQUID_CRYSTAL.get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(Level world, BlockPos pos, FluidState state, RandomSource random) {
        BlockPos topPos = pos.above();
        BlockState topState = world.getBlockState(topPos);
        if (topState.isAir() && !topState.isSolidRender(world, topPos) && random.nextInt(1000) == 0) {
            world.playLocalSound(
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, PastelSounds.LIQUID_CRYSTAL_AMBIENT,
                SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false
            );
        }
    }

    @Override
    public boolean canExtinguish(FluidState state, BlockGetter getter, BlockPos pos) {
        return true;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        // if liquid crystal collides with a flower of any kind:
        // drop a resonant lily instead
        if (state.is(BlockTags.FLOWERS)) {
            Block.dropResources(
                PastelBlocks.RESONANT_LILY.get()
                                          .defaultBlockState(), world, pos, null
            );
        } else {
            super.beforeDestroyingBlock(world, pos, state);
        }
    }

    @Override
    public ParticleOptions getDripParticle() {
        return PastelParticleTypes.DRIPPING_LIQUID_CRYSTAL;
    }

    @Override
    public ParticleOptions getSplashParticle() {
        return PastelParticleTypes.LIQUID_CRYSTAL_SPLASH;
    }

    /**
     * Entities colliding with liquid crystal will get a slight regeneration effect
     */
    @Override
    public void onEntityCollision(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, level, pos, entity);

        if (!level.isClientSide && entity instanceof LivingEntity livingEntity) {
            // just check every x ticks for performance and slow healing
            if (level.getGameTime() % 200 == 0) {
                MobEffectInstance regenerationInstance = livingEntity.getEffect(MobEffects.REGENERATION);
                if (regenerationInstance == null) {
                    MobEffectInstance newRegenerationInstance = new MobEffectInstance(MobEffects.REGENERATION, 80);
                    livingEntity.addEffect(newRegenerationInstance);
                }
            }
        }
    }

    @Override
    public RecipeType<? extends FluidConvertingRecipe> getDippingRecipeType() {
        return PastelRecipeTypes.LIQUID_CRYSTAL_CONVERTING;
    }

    public static class Flowing extends LiquidCrystalFluid {

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

    public static class Still extends LiquidCrystalFluid {

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

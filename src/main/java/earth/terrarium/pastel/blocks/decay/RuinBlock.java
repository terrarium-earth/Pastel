package earth.terrarium.pastel.blocks.decay;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.DeeperDownPortalBlock;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelLevels;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RuinBlock extends DecayBlock {

    public static final MapCodec<RuinBlock> CODEC = simpleCodec(RuinBlock::new);

    public RuinBlock(Properties settings) {
        super(settings, PastelCommon.CONFIG.RuinDecayTickRate, PastelCommon.CONFIG.RuinCanDestroyBlockEntities, 3, 5F);
        registerDefaultState(getStateDefinition().any()
                                                 .setValue(CONVERSION, Conversion.NONE));
    }

    @Override
    protected MapCodec<? extends RuinBlock> codec() {
        return CODEC;
    }

    @Override
    public void setPlacedBy(
        Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);

        if (!world.isClientSide) {
            world.playSound(null, pos, PastelSoundEvents.RUIN_PLACED, SoundSource.BLOCKS, 0.5F, 1.0F);
        } else {
            RandomSource random = world.getRandom();
            world.addParticle(
                ParticleTypes.EXPLOSION, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                ((-1.0F + random.nextFloat() * 2.0F) / 12.0F), 0.05, ((-1.0F + random.nextFloat() * 2.0F) / 12.0F)
            );
            world.addParticle(
                ParticleTypes.EXPLOSION_EMITTER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                ((-1.0F + random.nextFloat() * 2.0F) / 12.0F), 0.05, ((-1.0F + random.nextFloat() * 2.0F) / 12.0F)
            );

            for (int i = 0; i < 40; i++) {
                world.addParticle(
                    ColoredCraftingParticleEffect.GRAY, pos.getX() - 0.5 + random.nextFloat() * 2,
                    pos.getY() + random.nextFloat(), pos.getZ() - 0.5 + random.nextFloat() * 2,
                    ((-1.0F + random.nextFloat() * 2.0F) / 12.0F), 0.05, ((-1.0F + random.nextFloat() * 2.0F) / 12.0F)
                );
            }
        }
    }

    @Override
    protected @Nullable BlockState getSpreadState(
        BlockState stateToSpreadFrom, BlockState stateToSpreadTo, Level world, BlockPos stateToSpreadToPos) {
        if (stateToSpreadTo.getCollisionShape(world, stateToSpreadToPos)
                           .isEmpty() || stateToSpreadTo.is(PastelBlockTags.RUIN_SAFE)) {
            return null;
        }

        if (stateToSpreadTo.is(PastelBlockTags.RUIN_SPECIAL_CONVERSIONS)) {
            return this.defaultBlockState()
                       .setValue(CONVERSION, Conversion.SPECIAL);
        } else if (stateToSpreadTo.is(PastelBlockTags.RUIN_CONVERSIONS)) {
            // Protect the end portal to not lock players in the dim
            if (world.dimension()
                     .equals(Level.END) && Math.abs(stateToSpreadToPos.getX()) < 8 && Math.abs(
                stateToSpreadToPos.getZ()) < 8) {
                return null;
            }

            return this.defaultBlockState()
                       .setValue(CONVERSION, Conversion.DEFAULT);
        }
        return stateToSpreadFrom.setValue(CONVERSION, Conversion.NONE);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, world, pos, newState, moved);

        if (state.getValue(RuinBlock.CONVERSION) != Conversion.NONE && newState.isAir()) {
            if (world.dimension() == Level.NETHER) {
                if (pos.getY() == world.getMinBuildHeight() + world.dimensionType()
                                                                   .logicalHeight() -
                                  1) { // Attempt to match the nether ceiling. Tricky...
                    world.setBlock(
                        pos, PastelBlocks.DEEPER_DOWN_PORTAL.get()
                                                            .defaultBlockState()
                                                            .setValue(DeeperDownPortalBlock.FACING_UP, true), 3
                    );
                } else if (pos.getY() == world.getMinBuildHeight()) {
                    world.setBlock(
                        pos, PastelBlocks.DEEPER_DOWN_PORTAL.get()
                                                            .defaultBlockState()
                                                            .setValue(DeeperDownPortalBlock.FACING_UP, false), 3
                    );
                }
            } else if (world.dimension() == Level.OVERWORLD && pos.getY() == world.getMinBuildHeight()) {
                world.setBlock(
                    pos, PastelBlocks.DEEPER_DOWN_PORTAL.get()
                                                        .defaultBlockState()
                                                        .setValue(DeeperDownPortalBlock.FACING_UP, false), 3
                );
            } else if (world.dimension() == PastelLevels.DIMENSION_KEY &&
                       pos.getY() == world.getMaxBuildHeight() - 1) { // highest layer cannot be built on
                world.setBlock(
                    pos, PastelBlocks.DEEPER_DOWN_PORTAL.get()
                                                        .defaultBlockState()
                                                        .setValue(DeeperDownPortalBlock.FACING_UP, true), 3
                );
            }
        }
    }

}

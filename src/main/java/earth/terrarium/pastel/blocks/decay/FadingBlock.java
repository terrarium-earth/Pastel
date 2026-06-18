package earth.terrarium.pastel.blocks.decay;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FadingBlock extends DecayBlock {

    public static final MapCodec<FadingBlock> CODEC = simpleCodec(FadingBlock::new);

    public FadingBlock(Properties settings) {
        super(
            settings,
            PastelCommon.CONFIG.FadingDecayTickRate,
            PastelCommon.CONFIG.FadingCanDestroyBlockEntities,
            1,
            1F
        );
    }

    @Override
    protected MapCodec<? extends FadingBlock> codec() {
        return CODEC;
    }

    @Override
    public void setPlacedBy(
        Level world,
        BlockPos pos,
        BlockState state,
        @Nullable LivingEntity placer,
        ItemStack itemStack
    ) {
        super.setPlacedBy(world, pos, state, placer, itemStack);

        if (!world.isClientSide) {
            world.playSound(null, pos, PastelSounds.FADING_PLACED, SoundSource.BLOCKS, 0.5F, 1.0F);
        } else {
            RandomSource random = world.getRandom();
            world
                .addParticle(
                    ParticleTypes.POOF,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    ((-1.0F + random.nextFloat() * 2.0F) / 12.0F),
                    0.05,
                    ((-1.0F + random.nextFloat() * 2.0F) / 12.0F)
                );

            for (
                int i = 0;
                i < 10;
                i++
            ) {
                world
                    .addParticle(
                        ColoredCraftingParticleEffect.GRAY,
                        pos.getX() + random.nextFloat(),
                        pos.getY() + 1,
                        pos.getZ() + random.nextFloat(),
                        ((-1.0F + random.nextFloat() * 2.0F) / 12.0F),
                        0.05,
                        ((-1.0F + random.nextFloat() * 2.0F) / 12.0F)
                    );
            }
        }
    }

    @Override
    protected @Nullable BlockState getSpreadState(
        BlockState stateToSpreadFrom,
        BlockState stateToSpreadTo,
        Level world,
        BlockPos stateToSpreadToPos
    ) {
        if (stateToSpreadTo.is(PastelBlockTags.FADING_SPECIAL_CONVERSIONS)) {
            return stateToSpreadFrom.setValue(CONVERSION, Conversion.SPECIAL);
        } else if (stateToSpreadTo.is(PastelBlockTags.FADING_CONVERSIONS)) {
            return stateToSpreadFrom.setValue(CONVERSION, Conversion.DEFAULT);
        }
        return null;
    }

}

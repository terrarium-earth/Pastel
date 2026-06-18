package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.blocks.geology.SnowgraveBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SnowgraveFeature extends Feature<SnowgraveFeatureConfig> {
    public SnowgraveFeature(Codec<SnowgraveFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SnowgraveFeatureConfig> context) {
        var origin = context.origin();
        var variant = context
            .config()
            .variant();
        var level = context.level();
        if (!level.getBlockState(origin).is(PastelBlocks.FROSTED_DEEPSLATE)) return false;
        if (!checkSpace(variant, origin, level)) return false;
        level
            .setBlock(
                origin.above(),
                PastelBlocks.SNOWGRAVE
                    .get()
                    .defaultBlockState()
                    .setValue(SnowgraveBlock.FROZEN_MOB, variant),
                Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_CLIENTS
            );
        if (variant == SnowgraveBlock.FrozenMob.SPIDER) {
            level
                .setBlock(
                    origin
                        .above()
                        .south(),
                    PastelBlocks.SNOWGRAVE
                        .get()
                        .defaultBlockState()
                        .setValue(SnowgraveBlock.FROZEN_MOB, variant)
                        .setValue(SnowgraveBlock.SHOULD_RENDER, false),
                    Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_CLIENTS
                );
            level
                .setBlock(
                    origin
                        .above()
                        .east(),
                    PastelBlocks.SNOWGRAVE
                        .get()
                        .defaultBlockState()
                        .setValue(SnowgraveBlock.FROZEN_MOB, variant)
                        .setValue(SnowgraveBlock.SHOULD_RENDER, false),
                    Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_CLIENTS
                );
            level
                .setBlock(
                    origin
                        .above()
                        .south()
                        .east(),
                    PastelBlocks.SNOWGRAVE
                        .get()
                        .defaultBlockState()
                        .setValue(SnowgraveBlock.FROZEN_MOB, variant)
                        .setValue(SnowgraveBlock.SHOULD_RENDER, false),
                    Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_CLIENTS
                );
        } else {
            level
                .setBlock(
                    origin
                        .above()
                        .above(),
                    PastelBlocks.SNOWGRAVE
                        .get()
                        .defaultBlockState()
                        .setValue(SnowgraveBlock.FROZEN_MOB, variant)
                        .setValue(SnowgraveBlock.SHOULD_RENDER, false),
                    Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_CLIENTS
                );
        }
        return true;
    }

    private static boolean checkSpace(SnowgraveBlock.FrozenMob variant, BlockPos origin, WorldGenLevel level) {
        if (variant == SnowgraveBlock.FrozenMob.SPIDER) {
            for (
                BlockPos testPos : BlockPos
                    .betweenClosed(
                        origin.above(),
                        origin
                            .above()
                            .south()
                            .east()
                    )
            ) {
                if (!level
                    .getBlockState(testPos)
                    .canBeReplaced()) return false;
            }
            return true;
        } else {
            return level
                .getBlockState(origin.above())
                .canBeReplaced() && level
                    .getBlockState(
                        origin
                            .above()
                            .above()
                    )
                    .canBeReplaced();
        }
    }
}

package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.registries.PastelBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class HyperspecificOutcropFeature extends Feature<HyperspecificOutcropFeatureConfig> {
    public HyperspecificOutcropFeature(Codec<HyperspecificOutcropFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HyperspecificOutcropFeatureConfig> context) {
        var level = context.level();
        var origin = context.origin();
        if (!level
            .getBlockState(origin)
            .is(PastelBlockTags.CRYSTAL_SPIKE_BASES)) return false;
        Direction increment;
        if (level
            .getBlockState(origin.below())
            .canBeReplaced() && level
                .getBlockState(origin.above())
                .is(PastelBlockTags.CRYSTAL_SPIKE_BASES)) increment = Direction.DOWN;
        else if (level
            .getBlockState(origin.above())
            .canBeReplaced() && level
                .getBlockState(origin.below())
                .is(PastelBlockTags.CRYSTAL_SPIKE_BASES)) increment = Direction.UP;
        else return false;
        level
            .setBlock(
                origin,
                context
                    .config()
                    .buddingState(),
                Block.UPDATE_SUPPRESS_DROPS
            );
        BlockPos pos = origin;
        var states = List
            .of(
                context
                    .config()
                    .baseState(),
                context
                    .config()
                    .bodyState(),
                context
                    .config()
                    .tipState()
            );
        for (
            int i = 0;
            i < 3;
            i++
        ) {
            pos = pos.relative(increment);
            level
                .setBlock(
                    pos,
                    states
                        .get(i)
                        .setValue(AmethystClusterBlock.FACING, increment),
                    Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_CLIENTS
                );
        }
        return true;
    }
}

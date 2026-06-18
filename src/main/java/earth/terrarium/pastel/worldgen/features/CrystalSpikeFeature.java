package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.registries.PastelBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.phys.Vec3;

public class CrystalSpikeFeature extends Feature<BlockStateFeatureConfig> {

    public static int SPIKE_LENGTH = 20;

    public static int SPIKE_BASE_RADIUS = 4;

    public CrystalSpikeFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateFeatureConfig> context) {
        var level = context.level();
        var pos = context.origin();
        if (!isAdjacentToAir(level::getBlockState, pos)) return false;
        var direction = new Vec3(0.0, 1.0, 0.0)
            .xRot(Mth.PI / 4.0f)
            .yRot(
                level
                    .getRandom()
                    .nextFloat() * 2 * Mth.PI
            );
        boolean up;
        if (level
            .getBlockState(pos.below())
            .canBeReplaced() && level
                .getBlockState(pos.above())
                .is(PastelBlockTags.CRYSTAL_SPIKE_BASES)) up = false;
        else if (level
            .getBlockState(pos.above())
            .canBeReplaced() && level
                .getBlockState(pos.below())
                .is(PastelBlockTags.CRYSTAL_SPIKE_BASES)) up = true;
        else return false;
        if (direction.y > 0 && !up) direction = direction.multiply(1, -1, 1);
        var radiusDecrement = (float) SPIKE_BASE_RADIUS / (float) SPIKE_LENGTH;
        for (
            int i = 0;
            i < SPIKE_LENGTH;
            i++
        ) {
            var offsetVec = direction.scale(i);
            var offsetVec3i = new Vec3i(Mth.floor(offsetVec.x()), Mth.floor(offsetVec.y()), Mth.floor(offsetVec.z()));
            var centerPos = context
                .origin()
                .offset(offsetVec3i);
            var radius = Mth.ceil(SPIKE_BASE_RADIUS - radiusDecrement * i);
            placeLayer(
                centerPos,
                context
                    .config()
                    .blockState(),
                level,
                radius,
                PerlinNoise.create(level.getRandom(), -8, 1)
            );
        }
        return true;
    }

    private void placeLayer(BlockPos pos, BlockState state, WorldGenLevel level, int radius, PerlinNoise noise) {
        BlockPos
            .betweenClosed(pos.offset(radius, 0, radius), pos.offset(-radius, 0, -radius))
            .forEach((blockPos) -> {
                if (blockPos.distSqr(pos) <= radius * radius && noise
                    .getValue(pos.getX(), pos.getY(), pos.getZ()) < 0.6) {
                    safeSetBlock(
                        level,
                        blockPos,
                        state,
                        (oldState) -> oldState
                            .getBlock()
                            .defaultDestroyTime() != -1
                    );
                }
            });
    }
}

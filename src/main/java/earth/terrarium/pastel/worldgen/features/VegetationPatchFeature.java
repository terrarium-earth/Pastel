package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class VegetationPatchFeature extends Feature<VegetationPatchFeatureConfig> {
    public VegetationPatchFeature(Codec<VegetationPatchFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<VegetationPatchFeatureConfig> p_160210_) {
        VegetationPatchFeatureConfig $$1 = p_160210_.config();
        RandomSource $$2 = p_160210_.random();
        BlockPos $$3 = p_160210_.origin();
        WorldGenLevel $$4 = p_160210_.level();
        int $$5 = 0;
        BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos();
        int $$7 = $$1.xzSpread() + 1;
        int $$8 = $$1.ySpread() + 1;

        for (
            int $$9 = 0;
            $$9 < $$1.tries();
            $$9++
        ) {
            $$6
                .setWithOffset(
                    $$3,
                    $$2.nextInt($$7) - $$2.nextInt($$7),
                    $$2.nextInt($$8) - $$2.nextInt($$8),
                    $$2.nextInt($$7) - $$2.nextInt($$7)
                );
            if (!p_160210_.config().validSoil().test($$4, $$6.below()))
                continue;
            if ($$1.feature().value().place($$4, p_160210_.chunkGenerator(), $$2, $$6)) {
                $$5++;
            }
        }

        return $$5 > 0;
    }
}

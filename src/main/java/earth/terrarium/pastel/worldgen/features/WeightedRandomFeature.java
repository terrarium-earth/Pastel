package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Optional;

public class WeightedRandomFeature extends Feature<WeightedRandomFeatureConfig> {

    public WeightedRandomFeature(Codec<WeightedRandomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<WeightedRandomFeatureConfig> context) {
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();

        WeightedRandomFeatureConfig weightedRandomFeatureConfig = context.config();
        Optional<PlacedFeature> randomPlacedFeature = weightedRandomFeatureConfig.features()
                                                                                 .getRandomValue(context.random());
        return randomPlacedFeature.map(
                                      placedFeature -> placedFeature.place(structureWorldAccess,
                                                                           context.chunkGenerator(), random, blockPos))
                                  .orElse(false);
    }

}

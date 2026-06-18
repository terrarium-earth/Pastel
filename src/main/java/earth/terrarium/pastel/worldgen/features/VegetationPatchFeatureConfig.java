package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record VegetationPatchFeatureConfig(
    int tries,
    int xzSpread,
    int ySpread,
    Holder<PlacedFeature> feature,
    BlockPredicate validSoil
) implements FeatureConfiguration {
    public static final Codec<VegetationPatchFeatureConfig> CODEC = RecordCodecBuilder
        .create(
            instance -> instance
                .group(
                    ExtraCodecs.POSITIVE_INT
                        .fieldOf("tries")
                        .orElse(128)
                        .forGetter(VegetationPatchFeatureConfig::tries),
                    ExtraCodecs.NON_NEGATIVE_INT
                        .fieldOf("xz_spread")
                        .orElse(7)
                        .forGetter(VegetationPatchFeatureConfig::xzSpread),
                    ExtraCodecs.NON_NEGATIVE_INT
                        .fieldOf("y_spread")
                        .orElse(3)
                        .forGetter(VegetationPatchFeatureConfig::ySpread),
                    PlacedFeature.CODEC
                        .fieldOf("feature")
                        .forGetter(VegetationPatchFeatureConfig::feature),
                    BlockPredicate.CODEC
                        .fieldOf("valid_soil")
                        .forGetter(VegetationPatchFeatureConfig::validSoil)
                )
                .apply(instance, VegetationPatchFeatureConfig::new)
        );
}

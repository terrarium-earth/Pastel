package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record RandomBlockProximityPatchFeatureConfig(
    Integer tries, Integer xzSpread, Integer ySpread,
    HolderSet<Block> blocksToCheckFor, Integer blockScanRange,
    Holder<PlacedFeature> closeToBlockFeature,
    Holder<PlacedFeature> fallbackFeature
) implements FeatureConfiguration {

    public static final Codec<RandomBlockProximityPatchFeatureConfig> CODEC = RecordCodecBuilder.create(
        (instance) -> instance.group(
                                  ExtraCodecs.POSITIVE_INT.fieldOf("tries")
                                                          .orElse(128)
                                                          .forGetter(RandomBlockProximityPatchFeatureConfig::tries),
                                  ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread")
                                                              .orElse(7)
                                                              .forGetter(RandomBlockProximityPatchFeatureConfig::xzSpread),
                                  ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread")
                                                              .orElse(3)
                                                              .forGetter(RandomBlockProximityPatchFeatureConfig::ySpread),
                                  RegistryCodecs.homogeneousList(Registries.BLOCK)
                                                .fieldOf("blocks_to_find")
                                                .forGetter(RandomBlockProximityPatchFeatureConfig::blocksToCheckFor),
                                  ExtraCodecs.NON_NEGATIVE_INT.fieldOf("block_scan_range")
                                                              .orElse(1)
                                                              .forGetter(RandomBlockProximityPatchFeatureConfig::ySpread),
                                  PlacedFeature.CODEC.fieldOf("close_to_block_feature")
                                                     .forGetter(RandomBlockProximityPatchFeatureConfig::closeToBlockFeature),
                                  PlacedFeature.CODEC.fieldOf("fallback_feature")
                                                     .forGetter(RandomBlockProximityPatchFeatureConfig::fallbackFeature)
                              )
                              .apply(instance, RandomBlockProximityPatchFeatureConfig::new));

}

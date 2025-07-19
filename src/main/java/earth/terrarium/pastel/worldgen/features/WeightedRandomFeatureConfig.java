package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record WeightedRandomFeatureConfig(SimpleWeightedRandomList<PlacedFeature> features) implements FeatureConfiguration {

    public static final Codec<WeightedRandomFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            SimpleWeightedRandomList.wrappedCodec(PlacedFeature.DIRECT_CODEC).fieldOf("features").forGetter((weightedRandomFeatureConfig) -> weightedRandomFeatureConfig.features)
    ).apply(instance, WeightedRandomFeatureConfig::new));

}
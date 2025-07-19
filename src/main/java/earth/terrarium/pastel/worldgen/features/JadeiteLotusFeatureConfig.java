package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record JadeiteLotusFeatureConfig(Boolean inverted) implements FeatureConfiguration {
    public static final Codec<JadeiteLotusFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                                                                                                                     Codec.BOOL.fieldOf("inverted")
                                                                                                                               .forGetter(JadeiteLotusFeatureConfig::inverted)
                                                                                                                 )
                                                                                                                 .apply(
                                                                                                                     instance,
                                                                                                                     JadeiteLotusFeatureConfig::new
                                                                                                                 ));
}

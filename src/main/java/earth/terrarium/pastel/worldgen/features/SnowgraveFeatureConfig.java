package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.blocks.geology.SnowgraveBlock;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SnowgraveFeatureConfig(SnowgraveBlock.FrozenMob variant) implements FeatureConfiguration {
    public static final Codec<SnowgraveFeatureConfig> CODEC = RecordCodecBuilder.create(i->i.group(
        SnowgraveBlock.FrozenMob.CODEC.fieldOf("variant").forGetter(SnowgraveFeatureConfig::variant)
    ).apply(i,SnowgraveFeatureConfig::new));
}

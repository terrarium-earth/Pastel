package de.dafuqs.spectrum.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record NephriteBlossomFeatureConfig(Boolean flowering) implements FeatureConfiguration {
	public static final Codec<NephriteBlossomFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Codec.BOOL.fieldOf("flowering").forGetter(NephriteBlossomFeatureConfig::flowering)
	).apply(instance, NephriteBlossomFeatureConfig::new));
}

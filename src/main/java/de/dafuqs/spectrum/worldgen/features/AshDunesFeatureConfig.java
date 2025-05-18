package de.dafuqs.spectrum.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record AshDunesFeatureConfig(
		IntProvider nodeSpread,
		IntProvider nodeQuantity,
		IntProvider cutoutQuantity,
		FloatProvider emitterStrength,
		float emitterDecayModifier,
		float emitterCutoutModifier) implements FeatureConfiguration {
	
	public static final Codec<AshDunesFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			IntProvider.POSITIVE_CODEC.fieldOf("node_spread").forGetter((AshDunesFeatureConfig::nodeSpread)),
			IntProvider.POSITIVE_CODEC.fieldOf("node_quantity").forGetter(AshDunesFeatureConfig::nodeQuantity),
			IntProvider.POSITIVE_CODEC.fieldOf("cutout_quantity").forGetter(AshDunesFeatureConfig::cutoutQuantity),
			FloatProvider.codec(0F, 256F).fieldOf("emitter_strength").forGetter(AshDunesFeatureConfig::emitterStrength),
			ExtraCodecs.POSITIVE_FLOAT.fieldOf("emitter_decay_modifier").forGetter(AshDunesFeatureConfig::emitterDecayModifier),
			ExtraCodecs.POSITIVE_FLOAT.fieldOf("emitter_cutout_modifier").forGetter(AshDunesFeatureConfig::emitterCutoutModifier)
	).apply(instance, AshDunesFeatureConfig::new));
}

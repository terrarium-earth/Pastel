package de.dafuqs.spectrum.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record TriStateVineFeatureConfig(Block vineBlock, int cutoff, IntProvider minHeight, FloatProvider overgrowth, float berryChance) implements FeatureConfiguration {
	public static final Codec<TriStateVineFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BuiltInRegistries.BLOCK.byNameCodec().fieldOf("vine_block").forGetter(TriStateVineFeatureConfig::vineBlock),
			Codec.INT.fieldOf("cutoff").forGetter(TriStateVineFeatureConfig::cutoff),
			IntProvider.POSITIVE_CODEC.fieldOf("minimum_height").forGetter(TriStateVineFeatureConfig::minHeight),
			FloatProvider.codec(0, 1).fieldOf("overgrowth").forGetter(TriStateVineFeatureConfig::overgrowth),
			Codec.FLOAT.fieldOf("berry_chance").forGetter(TriStateVineFeatureConfig::berryChance)
	).apply(instance, TriStateVineFeatureConfig::new));
}

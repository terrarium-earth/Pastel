package de.dafuqs.spectrum.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record ColumnsFeatureConfig(BlockState blockState, IntProvider reach,
                                   IntProvider height) implements FeatureConfiguration {

    public static final Codec<ColumnsFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BlockState.CODEC.fieldOf("state").forGetter((config) -> config.blockState),
            IntProvider.codec(1, 10).fieldOf("reach").forGetter((config) -> config.reach),
            IntProvider.codec(1, 10).fieldOf("height").forGetter((config) -> config.height)
    ).apply(instance, ColumnsFeatureConfig::new));

}

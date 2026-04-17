package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record HyperspecificOutcropFeatureConfig(
    BlockState buddingState, BlockState baseState, BlockState bodyState, BlockState tipState
) implements FeatureConfiguration {
    public static final Codec<HyperspecificOutcropFeatureConfig> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                                             BlockState.CODEC.fieldOf("buddingState")
                                                                                                                             .forGetter(HyperspecificOutcropFeatureConfig::buddingState),
                                                                                                             BlockState.CODEC.fieldOf("baseState")
                                                                                                                             .forGetter(HyperspecificOutcropFeatureConfig::baseState), BlockState.CODEC.fieldOf("bodyState")
                                                                                                                                                                                                       .forGetter(
                                                                                                                                                                                                           HyperspecificOutcropFeatureConfig::bodyState),
                                                                                                             BlockState.CODEC.fieldOf("tipState")
                                                                                                                             .forGetter(HyperspecificOutcropFeatureConfig::tipState)
                                                                                                         )
                                                                                                         .apply(
                                                                                                             i,
                                                                                                             HyperspecificOutcropFeatureConfig::new
                                                                                                         ));
}

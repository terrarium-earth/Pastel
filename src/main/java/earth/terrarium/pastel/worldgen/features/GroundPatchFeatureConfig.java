package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record GroundPatchFeatureConfig(
    TagKey<Block> replaceable,
    BlockStateProvider groundState,
    Holder<PlacedFeature> vegetationFeature,
    CaveSurface surface,
    IntProvider depth,
    Float extraBottomBlockChance,
    Integer verticalRange,
    Float vegetationChance,
    IntProvider horizontalRadius,
    Float extraEdgeColumnChance
) implements FeatureConfiguration {

    public static final Codec<GroundPatchFeatureConfig> CODEC = RecordCodecBuilder
        .create(
            (instance) -> instance
                .group(
                    TagKey
                        .hashedCodec(Registries.BLOCK)
                        .fieldOf("replaceable")
                        .forGetter((config) -> config.replaceable),
                    BlockStateProvider.CODEC
                        .fieldOf("ground_state")
                        .forGetter((config) -> config.groundState),
                    PlacedFeature.CODEC
                        .fieldOf("vegetation_feature")
                        .forGetter((config) -> config.vegetationFeature),
                    CaveSurface.CODEC
                        .fieldOf("surface")
                        .forGetter((config) -> config.surface),
                    IntProvider
                        .codec(1, 128)
                        .fieldOf("depth")
                        .forGetter((config) -> config.depth),
                    Codec
                        .floatRange(0.0F, 1.0F)
                        .fieldOf("extra_bottom_block_chance")
                        .forGetter((config) -> config.extraBottomBlockChance),
                    Codec
                        .intRange(1, 256)
                        .fieldOf("vertical_range")
                        .forGetter((config) -> config.verticalRange),
                    Codec
                        .floatRange(0.0F, 1.0F)
                        .fieldOf("vegetation_chance")
                        .forGetter((config) -> config.vegetationChance),
                    IntProvider.CODEC
                        .fieldOf("xz_radius")
                        .forGetter((config) -> config.horizontalRadius),
                    Codec
                        .floatRange(0.0F, 1.0F)
                        .fieldOf("extra_edge_column_chance")
                        .forGetter((config) -> config.extraEdgeColumnChance)
                )
                .apply(
                    instance,
                    GroundPatchFeatureConfig::new
                )
        );

}

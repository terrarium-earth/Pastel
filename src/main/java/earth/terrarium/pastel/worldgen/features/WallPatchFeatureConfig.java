package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public class WallPatchFeatureConfig implements FeatureConfiguration {

    public static final Codec<WallPatchFeatureConfig> CODEC = RecordCodecBuilder
        .create(
            (instance) -> instance
                .group(
                    BuiltInRegistries.BLOCK
                        .byNameCodec()
                        .fieldOf("block")
                        .forGetter((config) -> config.block),
                    Codec
                        .intRange(1, 64)
                        .fieldOf("search_range")
                        .orElse(10)
                        .forGetter((config) -> config.searchRange),
                    Codec.BOOL
                        .fieldOf("can_place_on_floor")
                        .orElse(false)
                        .forGetter((config) -> config.placeOnFloor),
                    Codec.BOOL
                        .fieldOf("can_place_on_ceiling")
                        .orElse(false)
                        .forGetter((config) -> config.placeOnCeiling),
                    Codec.BOOL
                        .fieldOf("can_place_on_wall")
                        .orElse(false)
                        .forGetter((config) -> config.placeOnWalls),
                    IntProvider.NON_NEGATIVE_CODEC
                        .fieldOf("width")
                        .forGetter((config) -> config.width),
                    IntProvider.NON_NEGATIVE_CODEC
                        .fieldOf("height")
                        .forGetter((config) -> config.height),
                    RegistryCodecs
                        .homogeneousList(Registries.BLOCK)
                        .fieldOf("can_be_placed_on")
                        .forGetter((config) -> config.canPlaceOn)
                )
                .apply(
                    instance,
                    WallPatchFeatureConfig::new
                )
        );

    public final Block block;

    public final int searchRange;

    public final boolean placeOnFloor;

    public final boolean placeOnCeiling;

    public final boolean placeOnWalls;

    public final IntProvider width;

    public final IntProvider height;

    public final HolderSet<Block> canPlaceOn;

    private final ObjectArrayList<Direction> directions;

    public WallPatchFeatureConfig(
        Block block,
        Integer searchRange,
        Boolean placeOnFloor,
        Boolean placeOnCeiling,
        Boolean placeOnWalls,
        IntProvider width,
        IntProvider height,
        HolderSet<Block> canPlaceOn
    ) {
        this.block = block;
        this.searchRange = searchRange;
        this.placeOnFloor = placeOnFloor;
        this.placeOnCeiling = placeOnCeiling;
        this.placeOnWalls = placeOnWalls;
        this.width = width;
        this.height = height;
        this.canPlaceOn = canPlaceOn;
        this.directions = new ObjectArrayList<>(6);
        if (placeOnCeiling) {
            this.directions.add(Direction.UP);
        }
        if (placeOnFloor) {
            this.directions.add(Direction.DOWN);
        }
        if (placeOnWalls) {
            Direction.Plane.HORIZONTAL.forEach(this.directions::add);
        }
    }

    public List<Direction> shuffleDirections(RandomSource random, Direction excluded) {
        return Util
            .toShuffledList(
                this.directions
                    .stream()
                    .filter((direction) -> direction != excluded),
                random
            );
    }

    public List<Direction> shuffleDirections(RandomSource random) {
        return Util.shuffledCopy(this.directions, random);
    }

}

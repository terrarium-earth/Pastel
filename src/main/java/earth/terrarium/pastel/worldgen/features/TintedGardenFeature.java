package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public class TintedGardenFeature extends Feature<TintedGardenFeature.Config> {

    public static final Codec<Config> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.FLOAT.fieldOf("chance").forGetter(Config::chance),
            IntProvider.CODEC.fieldOf("spread").forGetter(Config::spread),
            PlacedFeature.CODEC.fieldOf("tree").forGetter(Config::tree),
            IntProvider.CODEC.fieldOf("tree_attempts").forGetter(Config::treePlacements),
            PlacedFeature.LIST_CODEC.fieldOf("groundcover").forGetter(Config::groundcover),
            IntProvider.CODEC.fieldOf("ground_attempts").forGetter(Config::groundPlacements),
            PlacedFeature.CODEC.fieldOf("fern").forGetter(Config::fern),
            IntProvider.CODEC.fieldOf("fern_attempts").forGetter(Config::fernPlacements)
    ).apply(i, Config::new));

    public TintedGardenFeature(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> context) {
        var pos = context.origin();
        var level = context.level();
        var gen = context.chunkGenerator();
        var random = context.random();
        var config = context.config();

        if (random.nextFloat() > config.chance)
            return false;

        var origin = level.getChunk(pos).getPos().getMiddleBlockPosition(pos.getY());
        origin = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, origin);

        if (!level.getLevel().isInWorldBounds(origin))
            return false;

        var spread = config.spread;

        var tree = config.tree.value();
        var fern = config.fern.value();
        var cover = config.groundcover.stream().map(Holder::value).toList();

        if (!place(config.treePlacements.sample(random), random, origin, spread, () -> tree, level, gen))
            return false;

        if (!place(config.fernPlacements.sample(random), random, origin, spread, () -> fern, level, gen))
            return false;

        place(config.groundPlacements.sample(random), random, origin, spread,
                () -> cover.get(random.nextInt(cover.size())), level, gen);

        return true;
    }

    private boolean place(int tries, RandomSource random, BlockPos origin, IntProvider spread, Supplier<PlacedFeature> feature, WorldGenLevel level, ChunkGenerator gen) {
        boolean success = false;

        for (int attempt = 0; attempt < tries; attempt++) {
            var placement = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, relative(origin, random, spread));

            if (feature.get().place(level, gen, random, placement))
                success = true;
        }
        return success;
    }

    private BlockPos relative(BlockPos origin, RandomSource random, IntProvider spread) {
        var x = spread.sample(random);
        var z = spread.sample(random);

        if (random.nextBoolean())
            x *= -1;

        if (random.nextBoolean())
            z *= -1;

        return origin.offset(
                x,
                0,
                z
        );
    }

    public record Config(
            float chance,
            IntProvider spread,
            Holder<PlacedFeature> tree,
            IntProvider treePlacements,
            HolderSet<PlacedFeature> groundcover,
            IntProvider groundPlacements,
            Holder<PlacedFeature> fern,
            IntProvider fernPlacements
    ) implements FeatureConfiguration {}
}

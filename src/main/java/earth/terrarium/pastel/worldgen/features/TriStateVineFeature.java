package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.blocks.imbrifer.flora.TriStateVineBlock;
import earth.terrarium.pastel.registries.PastelBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class TriStateVineFeature extends Feature<TriStateVineFeatureConfig> {

    public TriStateVineFeature(Codec<TriStateVineFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TriStateVineFeatureConfig> context) {
        var world = context.level();
        var origin = context.origin();
        var random = context.random();
        var chunkGen = context.chunkGenerator();
        var config = context.config();

        var floorState = world.getBlockState(origin.above());

        if (!(floorState.is(BlockTags.DIRT) || floorState.is(PastelBlockTags.BASE_STONE_IMBRIFER)))
            return false;

        var berryChance = config.berryChance();
        var vineBlock = config.vineBlock();

        if (!(vineBlock instanceof TriStateVineBlock))
            throw new IllegalStateException("TriStateVineFeatures must use TriStateVineBlocks!");

        if (berryChance > 0 && !vineBlock.defaultBlockState().hasProperty(BlockStateProperties.BERRIES))
            throw new IllegalStateException("Attempted to generate fruits for a vine with no fruiting state!");

        var minHeight = config.minHeight().sample(random);
        var overgrowth = config.overgrowth().sample(random);

        // try out how far we can grow
        var stemHeight = 0;
        BlockPos.MutableBlockPos mutablePos = origin.mutable();
        while (stemHeight < minHeight * 3) {
            mutablePos.move(Direction.DOWN);

            if (mutablePos.getY() < chunkGen.getMinY() || !isReplaceable(world, mutablePos))
                break;

            if (stemHeight > minHeight && random.nextFloat() > overgrowth)
                break;

            stemHeight++;
        }

        if (stemHeight <= config.cutoff())
            return false;

        generateStem(world, random, origin, vineBlock, stemHeight, berryChance);
        return true;
    }

    private static boolean isReplaceable(LevelAccessor world, BlockPos pos) {
        return world.getBlockState(pos).isAir();
    }

    private void generateStem(
        LevelAccessor world,
        RandomSource random,
        BlockPos origin,
        Block vineBlock,
        int stemHeight,
        float berryChance
    ) {
        var stemPointer = origin.mutable();
        var stemState = vineBlock
            .defaultBlockState()
            .setValue(
                TriStateVineBlock.LIFE_STAGE,
                TriStateVineBlock.LifeStage.STALK
            );

        for (
            int height = 0;
            height <= stemHeight;
            height++
        ) {
            if (height == stemHeight) {
                if (berryChance > 0 && random.nextFloat() <= berryChance) {
                    this
                        .setBlock(
                            world,
                            stemPointer,
                            stemState
                                .setValue(TriStateVineBlock.LIFE_STAGE, TriStateVineBlock.LifeStage.MATURE)
                                .setValue(BlockStateProperties.BERRIES, true)
                        );
                } else {
                    this
                        .setBlock(
                            world,
                            stemPointer,
                            stemState.setValue(TriStateVineBlock.LIFE_STAGE, TriStateVineBlock.LifeStage.MATURE)
                        );
                }
            } else {
                if (berryChance > 0 && random.nextFloat() <= berryChance) {
                    this.setBlock(world, stemPointer, stemState.setValue(BlockStateProperties.BERRIES, true));
                } else {
                    this.setBlock(world, stemPointer, stemState);
                }
            }

            stemPointer.move(Direction.DOWN);
        }
    }

}

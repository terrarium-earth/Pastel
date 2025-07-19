package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Iterator;
import java.util.List;

public class RandomBudsFeature extends Feature<RandomBudsFeaturesConfig> {

    public RandomBudsFeature(Codec<RandomBudsFeaturesConfig> configCodec) {
		super(configCodec);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<RandomBudsFeaturesConfig> context) {
		WorldGenLevel structureWorldAccess = context.level();
		BlockPos blockPos = context.origin();
		RandomSource random = context.random();
		RandomBudsFeaturesConfig randomBudsFeaturesConfig = context.config();
		
		int placedCount = 0;
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		int j = randomBudsFeaturesConfig.xzSpread + 1;
		int k = randomBudsFeaturesConfig.ySpread + 1;
    
        for (int l = 0; l < randomBudsFeaturesConfig.tries; ++l) {
            mutable.setWithOffset(blockPos, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
            List<Direction> directions = shuffleDirections(randomBudsFeaturesConfig, random);
            BlockState state = structureWorldAccess.getBlockState(mutable);
            boolean waterlogged = false;
            if (state.is(Blocks.WATER)) {
                waterlogged = true;
            } else if (!state.isAir()) {
                continue;
            }
            if (generate(structureWorldAccess, mutable, randomBudsFeaturesConfig, random, directions, waterlogged)) {
                ++placedCount;
            }
        }
    
        return placedCount > 0;
    }
    
    public static boolean generate(WorldGenLevel world, BlockPos pos, RandomBudsFeaturesConfig config, RandomSource random, List<Direction> directions, boolean waterlogged) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        
        Iterator<Direction> directionIterator = directions.iterator();
        Direction direction;
        BlockState blockState;
        do {
            if (!directionIterator.hasNext()) {
                return false;
            }
            direction = directionIterator.next();
            blockState = world.getBlockState(mutablePos.setWithOffset(pos, direction));
        } while (!blockState.is(config.canPlaceOn));
        
        BlockState stateToPlace = config.blocks.get(random.nextInt(config.blocks.size())).defaultBlockState().setValue(BlockStateProperties.FACING, direction.getOpposite()).setValue(BlockStateProperties.WATERLOGGED, waterlogged);
        if (stateToPlace.canSurvive(world, pos)) {
            world.setBlock(pos, stateToPlace, 3);
            world.getChunk(pos).markPosForPostprocessing(pos);
            return true;
        }
        return false;
    }

    public static List<Direction> shuffleDirections(RandomBudsFeaturesConfig config, RandomSource random) {
        return Util.toShuffledList(config.directions.stream(), random);
    }

}

package earth.terrarium.pastel.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TemporaryPlatformBlock extends Block {
    public static final int MAX_AGE = 30 * 20;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);
    public TemporaryPlatformBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(state.getValue(AGE)<MAX_AGE){
            state.setValue(AGE,state.getValue(AGE)+1);
        } else {
            level.removeBlock(pos,false);
        }
    }
}

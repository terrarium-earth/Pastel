package earth.terrarium.pastel.blocks.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class TilledSlushBlock extends ExtraTickFarmlandBlock {
	
	public TilledSlushBlock(Properties settings, BlockState bareState) {
		super(settings, bareState);
		this.registerDefaultState(defaultBlockState().setValue(MOISTURE, 7));
	}

//	@Override
//	public MapCodec<? extends > getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}
	
	@Override
	protected boolean isNearWater(LevelReader world, BlockPos pos) {
		return true;
	}
	
}

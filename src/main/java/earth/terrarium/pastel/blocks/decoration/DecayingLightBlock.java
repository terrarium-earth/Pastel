package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DecayingLightBlock extends WandLightBlock {

	public static final MapCodec<DecayingLightBlock> CODEC = simpleCodec(DecayingLightBlock::new);

	public DecayingLightBlock(Properties settings) {
		super(settings);
	}

//	@Override
//	public MapCodec<? extends DecayingLightBlock> getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}

	@Override
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		super.randomTick(state, world, pos, random);
		int light = state.getValue(LightBlock.LEVEL);
		if (light < 2) {
			if (state.getValue(WATERLOGGED)) {
				world.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
			} else {
				world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
			}
		} else {
			world.setBlock(pos, state.setValue(LightBlock.LEVEL, light - 1), 3);
		}
	}
	
}

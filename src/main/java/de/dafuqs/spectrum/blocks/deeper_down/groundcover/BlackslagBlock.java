package de.dafuqs.spectrum.blocks.deeper_down.groundcover;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.SpectrumBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackslagBlock extends RotatedPillarBlock implements BonemealableBlock {

	public static final MapCodec<BlackslagBlock> CODEC = simpleCodec(BlackslagBlock::new);

	public BlackslagBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends BlackslagBlock> codec() {
		return CODEC;
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		if (!world.getBlockState(pos.above()).propagatesSkylightDown(world, pos)) {
			return false;
		}
		
		for (BlockPos currPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
			BlockState currState = world.getBlockState(currPos);
			if (currState.is(SpectrumBlockTags.SPREADS_TO_BLACKSLAG)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isBonemealSuccess(Level world, net.minecraft.util.RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}
	
	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		List<BlockState> nextStates = new ArrayList<>();
		
		// search for all valid neighboring blocks and choose a weighted random one
		for (BlockPos blockPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
			BlockState blockState = world.getBlockState(blockPos);
			if (blockState.is(SpectrumBlockTags.SPREADS_TO_BLACKSLAG)) {
				nextStates.add(blockState);
			}
		}
		
		if (nextStates.isEmpty()) {
			return;
		}
		
		Collections.shuffle(nextStates);
		world.setBlock(pos, nextStates.getFirst(), Block.UPDATE_ALL);
	}
}

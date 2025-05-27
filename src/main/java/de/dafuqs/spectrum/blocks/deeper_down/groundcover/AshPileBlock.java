package de.dafuqs.spectrum.blocks.deeper_down.groundcover;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AshPileBlock extends SnowLayerBlock {

	public static final MapCodec<AshPileBlock> CODEC = simpleCodec(AshPileBlock::new);

	public AshPileBlock(Properties settings) {
		super(settings);
	}

//	@Override
//	public MapCodec<? extends AshPileBlock> getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockState blockState = world.getBlockState(pos.below());
		if (blockState.is(SpectrumBlocks.ASH.get()))
			return true;
		return super.canSurvive(state, world, pos);
	}
	
	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
	}
}

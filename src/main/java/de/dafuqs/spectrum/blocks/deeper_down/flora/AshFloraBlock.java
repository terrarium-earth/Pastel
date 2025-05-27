package de.dafuqs.spectrum.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.blocks.SpreadableFloraBlock;
import de.dafuqs.spectrum.registries.SpectrumBlockTags;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class AshFloraBlock extends SpreadableFloraBlock {

	public static final MapCodec<AshFloraBlock> CODEC = simpleCodec(AshFloraBlock::new);

	public AshFloraBlock(Properties settings) {
		super(7, settings);
	}

//	@Override
//	public MapCodec<? extends AshFloraBlock> getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return (floor.is(SpectrumBlockTags.ASH) || floor.is(SpectrumBlocks.ASHEN_BLACKSLAG.get()) || super.mayPlaceOn(floor, world, pos))
				&& floor.isFaceSturdy(world, pos, Direction.UP);
	}
}

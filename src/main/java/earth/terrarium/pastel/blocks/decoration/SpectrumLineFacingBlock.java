package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class SpectrumLineFacingBlock extends SpectrumFacingBlock {

	public static final MapCodec<SpectrumLineFacingBlock> CODEC = simpleCodec(SpectrumLineFacingBlock::new);

	public SpectrumLineFacingBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends SpectrumLineFacingBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection());
	}
	
}

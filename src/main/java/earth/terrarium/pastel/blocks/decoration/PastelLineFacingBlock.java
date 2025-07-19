package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class PastelLineFacingBlock extends PastelFacingBlock {

	public static final MapCodec<PastelLineFacingBlock> CODEC = simpleCodec(PastelLineFacingBlock::new);

	public PastelLineFacingBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends PastelLineFacingBlock> codec() {
		return CODEC;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection());
	}

}

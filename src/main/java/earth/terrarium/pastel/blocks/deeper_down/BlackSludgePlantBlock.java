package earth.terrarium.pastel.blocks.deeper_down;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlackSludgePlantBlock extends BushBlock {

	public static final MapCodec<BlackSludgePlantBlock> CODEC = simpleCodec(BlackSludgePlantBlock::new);

	protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 6.0, 12.0);

	public BlackSludgePlantBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends BlackSludgePlantBlock> codec() {
		return CODEC;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return floor.is(PastelBlockTags.HORNSLAKE_BLOCKS) || super.mayPlaceOn(floor, world, pos);
	}

	@Override
	public float getMaxHorizontalOffset() {
		return 0.08F;
	}

}

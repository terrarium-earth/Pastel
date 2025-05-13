package de.dafuqs.spectrum.blocks.deeper_down;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class BlackSludgePlantBlock extends PlantBlock {

	public static final MapCodec<BlackSludgePlantBlock> CODEC = createCodec(BlackSludgePlantBlock::new);

	protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 6.0, 12.0);

	public BlackSludgePlantBlock(Settings settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends BlackSludgePlantBlock> getCodec() {
		return CODEC;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(SpectrumBlockTags.HORNSLAKE_BLOCKS) || super.canPlantOnTop(floor, world, pos);
	}

	@Override
	public float getMaxHorizontalModelOffset() {
		return 0.08F;
	}

}

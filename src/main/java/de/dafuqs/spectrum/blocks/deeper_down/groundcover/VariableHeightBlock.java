package de.dafuqs.spectrum.blocks.deeper_down.groundcover;

import com.mojang.serialization.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class VariableHeightBlock extends MudBlock {
	
	private final VoxelShape shape;
	
	public VariableHeightBlock(Settings settings, int height) {
		super(settings);
		shape = Block.createCuboidShape(0, 0, 0, 16, height, 16);
	}

//    @Override
//    public MapCodec<? extends VariableHeightBlock> getCodec() {
//        //TODO: Make the codec
//        return CODEC;
//    }
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return shape;
	}
    
}

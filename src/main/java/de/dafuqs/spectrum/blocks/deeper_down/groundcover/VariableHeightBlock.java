package de.dafuqs.spectrum.blocks.deeper_down.groundcover;

import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class VariableHeightBlock extends MudBlock {
	
	private final VoxelShape shape;
	
	public VariableHeightBlock(Properties settings, int height) {
		super(settings);
		shape = Block.box(0, 0, 0, 16, height, 16);
	}

//    @Override
//    public MapCodec<? extends VariableHeightBlock> getCodec() {
//        //TODO: Make the codec
//        return CODEC;
//    }
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shape;
	}
    
}

package de.dafuqs.spectrum.blocks.deeper_down.groundcover;

import com.mojang.serialization.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class RottenGroundBlock extends MudBlock {

    public static final MapCodec<RottenGroundBlock> CODEC = createCodec(RottenGroundBlock::new);
	
	public static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 13, 16);

    public RottenGroundBlock(Settings settings) {
        super(settings);
    }

//    @Override
//    public MapCodec<? extends RottenGroundBlock> getCodec() {
//        //TODO: Make the codec
//        return CODEC;
//    }
    
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    
}

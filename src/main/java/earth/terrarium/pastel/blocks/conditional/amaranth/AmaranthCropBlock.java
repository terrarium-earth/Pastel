package earth.terrarium.pastel.blocks.conditional.amaranth;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.blocks.TallCropBlock;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AmaranthCropBlock extends TallCropBlock {

    public static final MapCodec<AmaranthCropBlock> CODEC = simpleCodec(AmaranthCropBlock::new);

    protected static final int LAST_SINGLE_BLOCK_AGE = 2;

    protected static final int MAX_AGE = 7;

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[] {
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };

    public AmaranthCropBlock(Properties settings) {
        super(settings, LAST_SINGLE_BLOCK_AGE);
    }

    @Override
    public MapCodec<? extends AmaranthCropBlock> codec() {
        return CODEC;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return PastelItems.AMARANTH_GRAINS.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (state.getValue(AGE) <= this.lastSingleBlockAge) {
                return AGE_TO_SHAPE[state.getValue(this.getAgeProperty())];
            } else {
                // Fill in the bottom block if the plant is two-tall
                return Shapes.block();
            }
        } else {
            return AGE_TO_SHAPE[state.getValue(this.getAgeProperty())];
        }
    }

}

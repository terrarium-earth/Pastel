package earth.terrarium.pastel.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.blocks.SpreadableFloraBlock;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
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
        return (floor.is(PastelBlockTags.ASH) || floor.is(PastelBlocks.ASHEN_BLACKSLAG.get()) || super.mayPlaceOn(
            floor, world, pos))
               && floor.isFaceSturdy(world, pos, Direction.UP);
    }
}

package earth.terrarium.pastel.blocks.redstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockLightDetectorBlock extends DetectorBlock {

    public static final MapCodec<BlockLightDetectorBlock> CODEC = simpleCodec(BlockLightDetectorBlock::new);

    public BlockLightDetectorBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends BlockLightDetectorBlock> codec() {
        //TODO: Make the codec
        return CODEC;
    }

    @Override
    protected void updateState(BlockState state, Level world, BlockPos pos) {
        int power = world.getMaxLocalRawBrightness(pos);

        power = state.getValue(INVERTED) ? 15 - power : power;
        if (state.getValue(POWER) != power) {
            world.setBlock(pos, state.setValue(POWER, power), 3);
        }
    }

    @Override
    int getUpdateFrequencyTicks() {
        return 20;
    }

}

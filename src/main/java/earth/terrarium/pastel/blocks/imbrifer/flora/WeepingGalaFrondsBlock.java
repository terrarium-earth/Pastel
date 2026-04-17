package earth.terrarium.pastel.blocks.imbrifer.flora;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WeepingGalaFrondsBlock extends BushBlock {

    public static final MapCodec<WeepingGalaFrondsBlock> CODEC = simpleCodec(WeepingGalaFrondsBlock::new);

    public WeepingGalaFrondsBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends WeepingGalaFrondsBlock> codec() {
        return CODEC;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.1F;
    }

    @Override
    public boolean mayPlaceOn(BlockState roof, BlockGetter world, BlockPos pos) {
        return roof.is(PastelBlocks.WEEPING_GALA_LEAVES.get()) || roof.is(PastelBlocks.WEEPING_GALA_FRONDS.get());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        var roof = pos.above();
        var roofState = world.getBlockState(roof);

        if (roofState.is(this))
            return true;

        return mayPlaceOn(roofState, world, roof);
    }
}

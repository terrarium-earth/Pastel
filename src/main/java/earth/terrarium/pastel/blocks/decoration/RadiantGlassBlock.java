package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RadiantGlassBlock extends TransparentBlock {

    public static final MapCodec<RadiantGlassBlock> CODEC = simpleCodec(RadiantGlassBlock::new);

    public RadiantGlassBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends RadiantGlassBlock> codec() {
        return CODEC;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.is(this) || stateFrom.is(PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS.get())) {
            return true;
        }
        return super.skipRendering(state, stateFrom, direction);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

}

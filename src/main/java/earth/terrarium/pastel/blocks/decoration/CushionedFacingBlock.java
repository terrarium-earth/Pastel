package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CushionedFacingBlock extends SpectrumFacingBlock {

    public static final MapCodec<CushionedFacingBlock> CODEC = simpleCodec(CushionedFacingBlock::new);

    public CushionedFacingBlock(Properties settings) {
        super(settings);
    }

	@Override
	public MapCodec<? extends CushionedFacingBlock> codec() {
		return CODEC;
	}

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {}
}

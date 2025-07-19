package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CushionedCarpetBlock extends CarpetBlock {

    public static final MapCodec<CushionedCarpetBlock> CODEC = simpleCodec(CushionedCarpetBlock::new);

    public CushionedCarpetBlock(Properties settings) {
        super(settings);
    }

	@Override
	public MapCodec<? extends CushionedCarpetBlock> codec() {
		return CODEC;
	}

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {}
}

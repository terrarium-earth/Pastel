package earth.terrarium.pastel.blocks.mob_head;

import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// since SkullBlockEntity uses the fixed BlockEntityType.SKULL we have to create our own block entity :(
// but since there is no player type / redstone interaction it is a bit more performant than the vanilla one
public class PastelSkullBlockEntity extends BlockEntity {

	public PastelSkullBlockEntity(BlockPos pos, BlockState state) {
		super(PastelBlockEntities.SKULL.get(), pos, state);
	}

}

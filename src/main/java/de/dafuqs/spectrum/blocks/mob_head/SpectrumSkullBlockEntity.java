package de.dafuqs.spectrum.blocks.mob_head;

import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// since SkullBlockEntity uses the fixed BlockEntityType.SKULL we have to create our own block entity :(
// but since there is no player type / redstone interaction it is a bit more performant than the vanilla one
public class SpectrumSkullBlockEntity extends BlockEntity {
	
	public SpectrumSkullBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.SKULL, pos, state);
	}
	
}

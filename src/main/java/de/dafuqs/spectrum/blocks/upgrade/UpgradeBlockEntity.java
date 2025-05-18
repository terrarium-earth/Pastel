package de.dafuqs.spectrum.blocks.upgrade;

import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class UpgradeBlockEntity extends BlockEntity {
	
	public UpgradeBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.UPGRADE_BLOCK, pos, state);
	}
	
}

package de.dafuqs.spectrum.blocks.decoration;

import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ProjectorBlockEntity extends BlockEntity {

	final ResourceLocation texture;
	final float heightOffset, bobMultiplier, scaling;

	public ProjectorBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.PROJECTOR, pos, state);
		var projectorBlock = (ProjectorBlock) state.getBlock();
		heightOffset = projectorBlock.heightOffset;
		bobMultiplier = projectorBlock.bobMultiplier;
		texture = projectorBlock.texture;
		scaling = projectorBlock.scaling;
	}
}

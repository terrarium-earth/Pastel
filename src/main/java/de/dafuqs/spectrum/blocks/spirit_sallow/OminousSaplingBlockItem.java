package de.dafuqs.spectrum.blocks.spirit_sallow;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class OminousSaplingBlockItem extends BlockItem {
	
	
	public OminousSaplingBlockItem(Block block, Properties settings) {
		super(block, settings);
	}
	
	@Override
	public InteractionResult place(BlockPlaceContext context) {
		InteractionResult actionResult = super.place(context);
		
		BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
		if (blockEntity instanceof OminousSaplingBlockEntity ominousSaplingBlockEntity) {
			if (context.getPlayer() != null) {
				ominousSaplingBlockEntity.setOwner(context.getPlayer());
			}
		}
		
		return actionResult;
	}
}

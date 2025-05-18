package de.dafuqs.spectrum.items.item_frame;

import de.dafuqs.spectrum.entity.SpectrumEntityTypes;
import de.dafuqs.spectrum.entity.entity.PhantomFrameEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;

public class PhantomFrameItem extends SpectrumItemFrameItem {
	
	public PhantomFrameItem(EntityType<? extends HangingEntity> entityType, Properties settings) {
		super(entityType, settings);
	}
	
	@Override
	public ItemFrame getItemFrameEntity(Level world, BlockPos blockPos, Direction direction) {
		return new PhantomFrameEntity(SpectrumEntityTypes.PHANTOM_FRAME, world, blockPos, direction);
	}
	
}

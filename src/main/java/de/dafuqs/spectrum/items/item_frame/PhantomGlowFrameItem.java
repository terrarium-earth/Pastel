package de.dafuqs.spectrum.items.item_frame;

import de.dafuqs.spectrum.entity.SpectrumEntityTypes;
import de.dafuqs.spectrum.entity.entity.PhantomGlowFrameEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;

public class PhantomGlowFrameItem extends SpectrumItemFrameItem {
	
	public PhantomGlowFrameItem(EntityType<? extends HangingEntity> entityType, Properties settings) {
		super(entityType, settings);
	}
	
	@Override
	public ItemFrame getItemFrameEntity(Level world, BlockPos blockPos, Direction direction) {
		return new PhantomGlowFrameEntity(SpectrumEntityTypes.GLOW_PHANTOM_FRAME, world, blockPos, direction);
	}
	
}

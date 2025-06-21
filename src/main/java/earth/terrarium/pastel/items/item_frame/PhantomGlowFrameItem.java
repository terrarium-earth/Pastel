package earth.terrarium.pastel.items.item_frame;

import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.entity.entity.PhantomGlowFrameEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;

public class PhantomGlowFrameItem extends PastelItemFrameItem {
	
	public PhantomGlowFrameItem(EntityType<? extends HangingEntity> entityType, Properties settings) {
		super(entityType, settings);
	}
	
	@Override
	public ItemFrame getItemFrameEntity(Level world, BlockPos blockPos, Direction direction) {
		return new PhantomGlowFrameEntity(PastelEntityTypes.GLOW_PHANTOM_FRAME.get(), world, blockPos, direction);
	}
	
}

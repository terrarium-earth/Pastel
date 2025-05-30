package earth.terrarium.pastel.api.interaction.projectile_behavior;

import earth.terrarium.pastel.entity.entity.*;
import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

class ItemProjectilePlacementContext extends BlockPlaceContext {
	ItemProjectileEntity itemProjectileEntity;
	
	public ItemProjectilePlacementContext(Level world, ItemProjectileEntity itemProjectileEntity, BlockHitResult blockHitResult) {
		super(world, null, InteractionHand.MAIN_HAND, itemProjectileEntity.getItem(), blockHitResult);
		this.itemProjectileEntity = itemProjectileEntity;
	}
	
	@Override
	public Direction getNearestLookingDirection() {
		return Direction.getFacingAxis(itemProjectileEntity, Direction.Axis.Y);
	}
	
	@Override
	public Direction getNearestLookingVerticalDirection() {
		return itemProjectileEntity.getViewXRot(1.0F) < 0.0F ? Direction.UP : Direction.DOWN;
	}
	
	@Override
	public Direction[] getNearestLookingDirections() {
		Direction[] directions = Direction.orderedByNearest(itemProjectileEntity);
		if (!this.replaceClicked) {
			Direction direction = this.getClickedFace();
			
			int i;
			for (i = 0; i < directions.length && directions[i] != direction.getOpposite(); )
				i++;
			
			if (i > 0) {
				System.arraycopy(directions, 0, directions, 1, i);
				directions[0] = direction.getOpposite();
			}
		}
		return directions;
	}
}
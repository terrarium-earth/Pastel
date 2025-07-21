package earth.terrarium.pastel.api.interaction.projectile_behavior;

import earth.terrarium.pastel.entity.entity.ItemProjectileEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class ItemProjectilePlacementContext extends BlockPlaceContext {
    ItemProjectileEntity itemProjectileEntity;

    public ItemProjectilePlacementContext(
        Level world, ItemProjectileEntity itemProjectileEntity, BlockHitResult blockHitResult) {
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

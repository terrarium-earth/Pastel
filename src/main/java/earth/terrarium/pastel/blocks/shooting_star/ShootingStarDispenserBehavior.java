package earth.terrarium.pastel.blocks.shooting_star;

import earth.terrarium.pastel.entity.entity.ShootingStarEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ShootingStarDispenserBehavior extends DefaultDispenseItemBehavior {

    @Override
    public ItemStack execute(@NotNull BlockSource pointer, @NotNull ItemStack stack) {
        Direction direction = pointer.state()
                                     .getValue(DispenserBlock.FACING);

        Level world = pointer.level();
        ShootingStarItem shootingStarItem = ((ShootingStarItem) stack.getItem());
        Vec3 spawnPos = new Vec3(
            pointer.pos()
                   .getX() + direction.getStepX() * 1.125D, pointer.pos()
                                                                   .getY() + direction.getStepY() * 1.13D, pointer.pos()
                                                                                                                  .getZ() +
                                                                                                           direction.getStepZ() *
                                                                                                           1.125D
        );
        ShootingStarEntity shootingStarEntity = shootingStarItem.getEntityForStack(world, spawnPos, stack);
        shootingStarEntity.setYRot(direction.toYRot());
        shootingStarEntity.push(direction.getStepX() * 0.4, direction.getStepY() * 0.4, direction.getStepZ() * 0.4);
        world.addFreshEntity(shootingStarEntity);

        stack.shrink(1);
        return stack;
    }

}

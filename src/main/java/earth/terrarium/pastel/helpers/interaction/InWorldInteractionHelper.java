package earth.terrarium.pastel.helpers.interaction;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InWorldInteractionHelper {

    public static boolean findAndDecreaseClosestItemEntityOfItem(
        @NotNull ServerLevel world, Vec3 pos, Item item, int range) {
        List<ItemEntity> itemEntities = world.getEntitiesOfClass(
            ItemEntity.class, AABB.ofSize(pos, range, range, range));
        for (ItemEntity itemEntity : itemEntities) {
            if (itemEntity.getItem()
                          .is(item)) {
                decrementAndSpawnRemainder(itemEntity, 1);
                return true;
            }
        }
        return false;
    }

    public static boolean findAndDecreaseClosestItemEntityOfItem(
        @NotNull Level world, Vec3 pos, TagKey<Item> tag, int range, int count) {
        List<ItemEntity> itemEntities = world.getEntitiesOfClass(
            ItemEntity.class, AABB.ofSize(pos, range, range, range));
        int foundCount = 0;
        for (ItemEntity itemEntity : itemEntities) {
            ItemStack stack = itemEntity.getItem();
            if (stack.is(tag)) {
                foundCount += stack.getCount();
                if (foundCount >= count) {
                    break;
                }
            }
        }

        if (foundCount < count) {
            return false;
        }

        for (ItemEntity itemEntity : itemEntities) {
            ItemStack stack = itemEntity.getItem();
            if (stack.is(tag)) {
                int decrementCount = Math.min(stack.getCount(), count);
                decrementAndSpawnRemainder(itemEntity, decrementCount);
                count -= decrementCount;
                if (count == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean findAndDecreaseClosestItemEntityOfItem(
        @NotNull Level world, Vec3 pos, Item item, int range, int count) {
        List<ItemEntity> itemEntities = world.getEntitiesOfClass(
            ItemEntity.class, AABB.ofSize(pos, range, range, range));
        int foundCount = 0;
        for (ItemEntity itemEntity : itemEntities) {
            ItemStack stack = itemEntity.getItem();
            if (stack.is(item)) {
                foundCount += stack.getCount();
                if (foundCount >= count) {
                    break;
                }
            }
        }

        if (foundCount < count) {
            return false;
        }

        for (ItemEntity itemEntity : itemEntities) {
            ItemStack stack = itemEntity.getItem();
            if (stack.is(item)) {
                int decrementCount = Math.min(stack.getCount(), count);
                decrementAndSpawnRemainder(itemEntity, decrementCount);
                count -= decrementCount;
                if (count == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void decrementAndSpawnRemainder(ItemEntity itemEntity, int amount) {
        ItemStack stack = itemEntity.getItem();
        ItemStack remainder = stack.getItem() instanceof MobBucketItem ? Items.BUCKET.getDefaultInstance()
                                                                       : stack.getCraftingRemainingItem(); // looking
        // at you, Mojang
        if (!remainder.isEmpty()) {
            remainder.setCount(amount);
            ItemEntity remainderEntity = new ItemEntity(
                itemEntity.level(), itemEntity.position()
                                              .x(), itemEntity.position()
                                                              .y(), itemEntity.position()
                                                                              .z(), remainder
            );
            itemEntity.level()
                      .addFreshEntity(remainderEntity);
        }
        stack.shrink(amount);
    }

    public static void scatter(Level world, double x, double y, double z, ItemStack item, long amount) {
        int maxStackSize = item.getMaxStackSize();

        while (amount > 0) {
            int stackSize = (int) Math.min(maxStackSize, amount);
            ItemStack stack = item.copyWithCount(stackSize);
            Containers.dropItemStack(world, x, y, z, stack);
            amount -= stackSize;
        }
    }


}

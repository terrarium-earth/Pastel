package earth.terrarium.pastel.helpers.enchantments;

import earth.terrarium.pastel.mixin.accessors.MobEntityAccessor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisarmingHelper {

    public static void disarmEntity(LivingEntity livingEntity) {
        // since endermen save their carried block as blockState, not in hand
        // we have to use custom logic for them
        if (livingEntity instanceof EnderMan endermanEntity) {
            BlockState carriedBlockState = endermanEntity.getCarriedBlock();
            if (carriedBlockState != null) {
                Item item = carriedBlockState
                    .getBlock()
                    .asItem();
                if (item != null) {
                    endermanEntity.spawnAtLocation(item.getDefaultInstance());
                    endermanEntity.setCarriedBlock(null);
                }
            }
            return;
        }

        // choose a random slot and drop its content
        List<EquipmentSlot> slots = new ArrayList<>(List.of(EquipmentSlot.values()));
        Collections.shuffle(slots);
        for (
            EquipmentSlot slot : slots
        ) {
            ItemStack slotStack = livingEntity.getItemBySlot(slot);
            if (slotStack.isEmpty()) {
                continue;
            }

            // set to cannot drop? Skip that slot
            if (livingEntity instanceof Mob mobEntity && ((MobEntityAccessor) mobEntity)
                .invokeGetEquipmentDropChance(
                    slot
                ) <= 0) {
                continue;
            }

            livingEntity.spawnAtLocation(slotStack);
            livingEntity.setItemSlot(slot, ItemStack.EMPTY);
            livingEntity
                .level()
                .playSound(
                    null,
                    livingEntity.blockPosition(),
                    SoundEvents.BUNDLE_DROP_CONTENTS,
                    SoundSource.NEUTRAL,
                    1.0F,
                    1.0F
                );
            break;
        }
    }

}

package de.dafuqs.spectrum.items.trinkets;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import static de.dafuqs.spectrum.items.trinkets.SpectrumTrinketItem.hasEquipped;

public class TrinketCapabilityEventHandler {
    private static void handleTrinketChange(SlotContext slotContext) {
        if (slotContext.entity() instanceof ServerPlayer serverPlayerEntity) {
            SpectrumAdvancementCriteria.TRINKET_CHANGE.trigger(serverPlayerEntity);
        }
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(CuriosCapability.ITEM, (stack, context) -> new ICurio() {
            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public boolean canEquip(SlotContext slotContext) {
                if (slotContext.entity() instanceof Player playerEntity) {
                    // does the player have the matching advancement?
                    if (AdvancementHelper.hasAdvancement(playerEntity, getUnlockIdentifier())) {
                        // Can only a single trinket of that type be equipped at once?
                        if (!canEquipMoreThanOne() && hasEquipped(entity, this)) {
                            return false;
                        }
                        return super.canEquip(slotContext);
                    }
                }
                return false;
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                handleTrinketChange(slotContext);
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                handleTrinketChange(slotContext);
            }
        });
    }
}

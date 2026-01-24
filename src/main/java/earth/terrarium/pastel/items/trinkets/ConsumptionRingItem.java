package earth.terrarium.pastel.items.trinkets;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.PastelDataAttachments;
import earth.terrarium.pastel.attachments.data.ConsumptionRingData;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class ConsumptionRingItem extends PastelTrinketItem {
    // how many ticks pass before each hunger check tick?
    private static final int HUNGER_TICK_FREQUENCY = 20;
    // what portion of damage dealt to valid enemies should heal you?
    private static final float LIFESTEAL = 0.8f;
    private static final ResourceLocation MODIFIER_ID = PastelCommon.locate("ring_of_consumption");

    public ConsumptionRingItem(Properties settings) {
        super(settings, PastelAdvancements.Unlocks.Trinkets.RING_OF_CONSUMPTION);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("item.pastel.ring_of_consumption.tooltip")
                             .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltip, type);
    }

    // overheal: half as effective as regular healing, can't go beyond 2x your base max health
    public static void applyOverheal(ServerPlayer player, float amount) {
        amount *= LIFESTEAL;
        var missingHealth = player.getMaxHealth() - player.getHealth();
        if (amount <= missingHealth) {
            player.heal(amount);
            return;
        }
        var hpAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (hpAttribute == null) return;
        var currentMod = hpAttribute.getModifier(MODIFIER_ID);
        if (currentMod == null) hpAttribute.addPermanentModifier(
            new AttributeModifier(
                MODIFIER_ID, Math.min((amount - missingHealth) / 2, hpAttribute.getBaseValue()),
                AttributeModifier.Operation.ADD_VALUE
            ));
        else {
            hpAttribute.removeModifier(currentMod);
            hpAttribute.addPermanentModifier(new AttributeModifier(
                MODIFIER_ID,
                Math.min(currentMod.amount() + (amount - missingHealth) / 2, hpAttribute.getBaseValue()),
                AttributeModifier.Operation.ADD_VALUE
            ));
        }
        // recalculate final value
        hpAttribute.getValue();
        player.setHealth(player.getMaxHealth());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        ConsumptionRingData.setHasRing(slotContext.entity(), true);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        ConsumptionRingData.setHasRing(slotContext.entity(), false);
        var attribute = slotContext.entity()
                                   .getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null) attribute.removeModifier(MODIFIER_ID);
    }

    @Override
    public void curioTick(SlotContext context, ItemStack stack) {
        if (context.entity() instanceof Player player && !player.isCreative() && !player.isSpectator() && player.level()
                                                                                                                .getGameTime() %
                                                                                                          HUNGER_TICK_FREQUENCY ==
                                                                                                          0) {
            var hungerData = player.getFoodData();
            if (hungerData.getFoodLevel() == 0 && player.getMaxHealth() > 1) {
                AttributeInstance instance = player.getAttribute(Attributes.MAX_HEALTH);
                if (instance != null) {
                    AttributeModifier currentMod = instance.getModifier(MODIFIER_ID);
                    if (currentMod == null) {
                        instance.addPermanentModifier(
                            new AttributeModifier(MODIFIER_ID, -1, AttributeModifier.Operation.ADD_VALUE));
                    } else {
                        instance.removeModifier(currentMod);
                        AttributeModifier newModifier = new AttributeModifier(
                            MODIFIER_ID, currentMod.amount() - 1, AttributeModifier.Operation.ADD_VALUE);
                        instance.addPermanentModifier(newModifier);
                        instance.getValue(); // recalculate final value
                        if (player.getHealth() > player.getMaxHealth()) {
                            player.setHealth(player.getMaxHealth());
                        }
                    }
                }
            } else {
                hungerData.setFoodLevel(Math.max(hungerData.getFoodLevel() - 1, 0));
            }
        }
    }
}

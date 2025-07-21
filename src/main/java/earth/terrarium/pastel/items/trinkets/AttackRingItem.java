package earth.terrarium.pastel.items.trinkets;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;

public class AttackRingItem extends PastelTrinketItem {

    public static final ResourceLocation ATTACK_RING_DAMAGE_ID = PastelCommon.locate("jeopardant");
    public static final String ATTACK_RING_DAMAGE_NAME = "pastel:jeopardant";

    public AttackRingItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/jeopardant"));
    }

    public static double getAttackModifierForEntity(LivingEntity entity) {
        if (entity == null) {
            return 0;
        } else {
            double mod = entity.getMaxHealth() / (entity.getHealth() * entity.getHealth() +
                                                  1); // starting with 1 % damage at 14 health up to 300 % damage at
            // 1/20 health
            return Math.max(0, 1 + Math.log10(mod));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        LivingEntity entity = slotContext.entity();

        if (entity.getAttributes()
                  .hasModifier(Attributes.ATTACK_DAMAGE, AttackRingItem.ATTACK_RING_DAMAGE_ID)) {
            Multimap<Holder<Attribute>, AttributeModifier> map = Multimaps.newMultimap(
                Maps.newLinkedHashMap(), ArrayList::new);
            map.put(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(
                    AttackRingItem.ATTACK_RING_DAMAGE_ID,
                    AttackRingItem.getAttackModifierForEntity(entity),
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
            );
            entity.getAttributes()
                  .removeAttributeModifiers(map);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        Minecraft client = Minecraft.getInstance();
        long mod = Math.round(getAttackModifierForEntity(client.player) * 100);
        if (mod == 0) {
            tooltip.add(Component.translatable("item.pastel.jeopardant.tooltip.damage_zero"));
        } else {
            tooltip.add(Component.translatable("item.pastel.jeopardant.tooltip.damage", mod));
        }
    }

}

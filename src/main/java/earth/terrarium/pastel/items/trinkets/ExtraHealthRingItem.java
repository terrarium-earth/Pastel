package earth.terrarium.pastel.items.trinkets;

import com.google.common.collect.Multimap;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.energy.storage.FixedSingleInkStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class ExtraHealthRingItem extends InkDrainTrinketItem {

    public ExtraHealthRingItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/heartsingers_reward"), InkColors.PINK);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip
            .add(
                Component
                    .translatable("item.pastel.heartsingers_reward.tooltip")
                    .withStyle(ChatFormatting.GRAY)
            );
        super.appendHoverText(stack, context, tooltip, type);
    }

    public static ResourceLocation HEALTH_ATTRIBUTE_ID = PastelCommon.locate("heartsingers_reward_health");

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
        SlotContext slotContext,
        ResourceLocation id,
        ItemStack stack
    ) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(slotContext, id, stack);
        ;

        FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
        long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
        int extraHearts = getExtraHearts(storedInk);
        if (extraHearts != 0) {
            modifiers
                .put(
                    Attributes.MAX_HEALTH,
                    new AttributeModifier(HEALTH_ATTRIBUTE_ID, extraHearts, AttributeModifier.Operation.ADD_VALUE)
                );
        }

        return modifiers;
    }

    public int getExtraHearts(long storedInk) {
        if (storedInk < 100) {
            return 0;
        } else {
            return 2 + 2 * (int) (Math.log(storedInk / 100.0f) / Math.log(8));
        }
    }

}

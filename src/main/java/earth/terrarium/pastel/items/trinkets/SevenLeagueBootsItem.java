package earth.terrarium.pastel.items.trinkets;

import com.google.common.collect.Multimap;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class SevenLeagueBootsItem extends PastelTrinketItem {

    public static final ResourceLocation MOVEMENT_SPEED_ATTRIBUTE_ID = PastelCommon.locate(
        "seven_league_boots_movement_speed");
    public static final ResourceLocation STEP_UP_ATTRIBUTE_ID = PastelCommon.locate("seven_league_boots_step_up");

    public SevenLeagueBootsItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/seven_league_boots"));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        var attribute = slotContext.entity()
                                   .getAttribute(Attributes.STEP_HEIGHT);
        if (attribute == null) return;
        var hasMod = attribute.hasModifier(STEP_UP_ATTRIBUTE_ID);
        if (!slotContext.entity()
                        .isCrouching() && !hasMod) attribute.addTransientModifier(
            new AttributeModifier(STEP_UP_ATTRIBUTE_ID, 0.75, AttributeModifier.Operation.ADD_VALUE));
        if (slotContext.entity()
                       .isCrouching() && hasMod) attribute.removeModifier(STEP_UP_ATTRIBUTE_ID);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        var attribute = slotContext.entity()
                                   .getAttribute(Attributes.STEP_HEIGHT);
        if (attribute != null && attribute.hasModifier(STEP_UP_ATTRIBUTE_ID)) attribute.removeModifier(
            STEP_UP_ATTRIBUTE_ID);
    }

    // bad and naughty curios get literal tooltip strings until 1.2 delivers redemption in the form of lang datagen
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
        var result = super.getAttributesTooltip(tooltips, context, stack);
        result.add(Component.literal("When not crouching:").withStyle(ChatFormatting.GOLD));
        result.add(Component.literal("+0.75 Step Height").withStyle(ChatFormatting.BLUE).append(Component.literal(" [+0.75]").withStyle(ChatFormatting.GRAY)));
        return result;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(
        SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(slotContext, id, stack);


        int powerLevel = slotContext.entity() != null ? Ench.getLevel(
            slotContext.entity()
                       .level()
                       .registryAccess(), Enchantments.POWER, stack
        ) : 0;
        double speedBoost = 0.05 * (powerLevel + 1);
        modifiers.put(
            Attributes.MOVEMENT_SPEED, new AttributeModifier(
                MOVEMENT_SPEED_ATTRIBUTE_ID, speedBoost,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
        );

        return modifiers;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.POWER);
    }

}

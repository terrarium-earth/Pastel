package earth.terrarium.pastel.items.armor;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.item.TickingEquipmentItem;
import earth.terrarium.pastel.api.item.UnequipAwareItem;
import earth.terrarium.pastel.attachments.data.CitrineJumpsAttachment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrystalArmorItem extends ArmorItem implements TickingEquipmentItem, UnequipAwareItem {

    private static final AttributeModifier GEM_LEGGINGS_KB_RESIST = new AttributeModifier(
        PastelCommon.locate("gem_armor_kb_resist"), 0.5f, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier GEM_BOOTS_SPEED = new AttributeModifier(
        PastelCommon.locate("gem_armor_speed"), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    private static final AttributeModifier GEM_SET_KB_IMMUNITY = new AttributeModifier(
        PastelCommon.locate("gem_armor_setbonus_kb_immunity"), 0.5f, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier GEM_SET_SPEED = new AttributeModifier(
        PastelCommon.locate("gem_armor_setbonus_speed"), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public CrystalArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    public boolean isWearingFullSet(LivingEntity bearer) {
        for (var slot : List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
            if (!(bearer.getItemBySlot(slot)
                        .getItem() instanceof CrystalArmorItem)) return false;
        }
        return true;
    }

    public void tick(LivingEntity bearer, ItemStack stack) {
        if (bearer.level()
                  .isClientSide()) return;

        if (type.equals(Type.HELMET) && isWearingFullSet(bearer)) {
            // set bonus time :3
            var kb_resist = bearer.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            var speed = bearer.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null && !speed.hasModifier(GEM_SET_SPEED.id())) speed.addTransientModifier(GEM_SET_SPEED);
            if (kb_resist != null && !kb_resist.hasModifier(GEM_SET_KB_IMMUNITY.id())) kb_resist.addTransientModifier(
                GEM_SET_KB_IMMUNITY);
        }

        // Works out to half of regen 1, or regen 1 with the set bonus
        if (type.equals(Type.CHESTPLATE) && (bearer.level()
                                                   .getGameTime() % 100 == 0 || (isWearingFullSet(bearer) &&
                                                                                bearer.level()
                                                                                      .getGameTime() % 50 == 0))) {
            bearer.heal(1.0f);
        }
        if (type.equals(Type.LEGGINGS)) {
            var kb_resist = bearer.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kb_resist != null) {
                boolean hasMod = kb_resist.hasModifier(GEM_LEGGINGS_KB_RESIST.id());
                if (hasMod && !bearer.isCrouching()) kb_resist.removeModifier(GEM_LEGGINGS_KB_RESIST.id());
                if (!hasMod && bearer.isCrouching()) kb_resist.addTransientModifier(GEM_LEGGINGS_KB_RESIST);
            }
        }

        if (type.equals(Type.BOOTS) && bearer instanceof ServerPlayer player) {
            if (player.jumping && player.getData(CitrineJumpsAttachment.ATTACHMENT) > 0 && !player.onGround()) {
                PastelCommon.logWarning("initiating doublejump");
                player.jumpFromGround();
                player.setData(
                    CitrineJumpsAttachment.ATTACHMENT, player.getData(CitrineJumpsAttachment.ATTACHMENT) - 1);
            }
            if (player.onGround()) {
                var jumps = player.getData(CitrineJumpsAttachment.ATTACHMENT);
                int maxJumps = isWearingFullSet(player) ? 2 : 1;
                if (jumps < maxJumps) {
                    player.setData(CitrineJumpsAttachment.ATTACHMENT, maxJumps);
                }
            }
        }

    }

    public void onFall(ItemStack itemStack, LivingEntity targetEntity, float amount) {
        if (type.equals(Type.LEGGINGS)) itemStack.hurtAndBreak(Math.round(amount), targetEntity, type.getSlot());
    }

    public void onUnequip(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
        if (type == Type.LEGGINGS) {
            var kb_resist = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kb_resist != null) kb_resist.removeModifier(GEM_LEGGINGS_KB_RESIST);
        }
        var kb_resist = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        var speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && speed.hasModifier(GEM_SET_SPEED.id())) speed.removeModifier(GEM_SET_SPEED.id());
        if (kb_resist != null && kb_resist.hasModifier(GEM_SET_KB_IMMUNITY.id())) kb_resist.removeModifier(
            GEM_SET_KB_IMMUNITY.id());
    }

    @Override
    public void appendHoverText(
        ItemStack stack, TooltipContext context, List<Component> tooltip,
        TooltipFlag tooltipType
    ) {
        super.appendHoverText(stack, context, tooltip, tooltipType);
        addTooltip(tooltip, type);
    }

    public void addTooltip(List<Component> tooltip, @NotNull ArmorItem.Type equipmentSlot) {
        switch (equipmentSlot) {
            case HELMET -> tooltip.add(Component.translatable("item.pastel.onyx_helmet.tooltip")
                                                .withStyle(ChatFormatting.DARK_BLUE));
            case CHESTPLATE -> tooltip.add(Component.translatable("item.pastel.amethyst_chestplate.tooltip")
                                                    .withStyle(ChatFormatting.LIGHT_PURPLE));
            case LEGGINGS -> tooltip.add(Component.translatable("item.pastel.topaz_leggings.tooltip")
                                                  .withStyle(ChatFormatting.AQUA));
            case BOOTS -> tooltip.add(Component.translatable("item.pastel.citrine_boots.tooltip")
                                               .withStyle(ChatFormatting.YELLOW));
        }
    }

}

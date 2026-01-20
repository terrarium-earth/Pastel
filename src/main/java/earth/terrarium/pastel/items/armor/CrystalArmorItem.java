package earth.terrarium.pastel.items.armor;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.TickingEquipmentItem;
import earth.terrarium.pastel.api.item.UnequipAwareItem;
import earth.terrarium.pastel.attachments.data.CitrineJumpsAttachment;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
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
    public static int ENCHANTMENT_BONUS = 1;

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
        // this needs to happen on the client as well apparently
        if (type.equals(Type.BOOTS) && bearer instanceof Player player) {
            if (player.onGround()) {
                var jumps = player.getData(CitrineJumpsAttachment.ATTACHMENT);
                int maxJumps = isWearingFullSet(player) ? 2 : 1;
                if (jumps < maxJumps) {
                    player.setData(CitrineJumpsAttachment.ATTACHMENT, maxJumps);
                }
            }
        }

        if (bearer.level()
                  .isClientSide()) return;

        if (type.equals(Type.HELMET)) {
            for (EquipmentSlot slot : List.of(
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
                ItemStack equippedStack = bearer.getItemBySlot(slot);
                if (equippedStack.getItem() instanceof CrystalArmorItem && equippedStack.getOrDefault(
                    PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED, 0) < ENCHANTMENT_BONUS) {
                    ItemStack oldStack = stack.copy();
                    CrystalArmorItem.addEmpowered(equippedStack);
                    bearer.onEquipItem(slot, oldStack, stack);
                }
            }

            if (isWearingFullSet(bearer)) {
                // set bonus time :3
                var slots = List.of(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
                for (EquipmentSlot slot : slots) {
                    var heldStack = bearer.getItemBySlot(slot);
                    var oldStack = heldStack.copy();
                    if (!heldStack.is(Items.AIR) && heldStack.getOrDefault(
                        PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED, 0) < ENCHANTMENT_BONUS) {
                        CrystalArmorItem.addEmpowered(heldStack);
                        bearer.onEquipItem(slot, oldStack, heldStack);
                    }
                }

                var kb_resist = bearer.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
                var speed = bearer.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speed != null && !speed.hasModifier(GEM_SET_SPEED.id())) speed.addTransientModifier(GEM_SET_SPEED);
                if (kb_resist != null && !kb_resist.hasModifier(GEM_SET_KB_IMMUNITY.id()))
                    kb_resist.addTransientModifier(GEM_SET_KB_IMMUNITY);
            }
        }

        // Works out to a quarter of regen 1, or half of regen 1 with the set bonus
        if (type.equals(Type.CHESTPLATE) && (bearer.level()
                                                   .getGameTime() % 100 == 0 || (isWearingFullSet(bearer) &&
                                                                                 bearer.level()
                                                                                       .getGameTime() % 50 == 0))) {
            bearer.heal(1.0f);
            shortenNegativeStatusEffects(bearer,25);
        }
        if (type.equals(Type.LEGGINGS)) {
            var kb_resist = bearer.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
            if (kb_resist != null) {
                boolean hasMod = kb_resist.hasModifier(GEM_LEGGINGS_KB_RESIST.id());
                if (hasMod && !bearer.isCrouching()) kb_resist.removeModifier(GEM_LEGGINGS_KB_RESIST.id());
                if (!hasMod && bearer.isCrouching()) kb_resist.addTransientModifier(GEM_LEGGINGS_KB_RESIST);
            }
        }

    }

    public void onFall(ItemStack itemStack, LivingEntity targetEntity, float amount) {
        if (type.equals(Type.LEGGINGS)) itemStack.hurtAndBreak(Math.round(amount), targetEntity, type.getSlot());
    }

    public void onUnequip(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
        if (type == Type.HELMET) {
            for (var equippedStack : entity.getAllSlots()) {
                PastelCommon.logWarning("helmet removed");
                if (equippedStack.has(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED))
                    CrystalArmorItem.removeEmpowered(stack);
            }
        }

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
                                                    .withColor(InkColors.MAGENTA.getTextColorInt()));
            case LEGGINGS -> tooltip.add(Component.translatable("item.pastel.topaz_leggings.tooltip")
                                                  .withColor(InkColors.CYAN.getTextColorInt()));
            case BOOTS -> tooltip.add(Component.translatable("item.pastel.citrine_boots.tooltip")
                                               .withColor(InkColors.YELLOW.getTextColorInt()));
        }
    }

    public static void addEmpowered(ItemStack stack) {
        stack.set(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED, ENCHANTMENT_BONUS);
        var enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null || enchantments.isEmpty()) return;
        for (var enchantment : enchantments.keySet()) {
            if (enchantments.getLevel(enchantment) > 0 && enchantment.value()
                                                                     .getMaxLevel() >1) Ench.addOrUpgradeEnchantment(
                stack, enchantment, enchantments.getLevel(enchantment) + ENCHANTMENT_BONUS, true, true);
        }
    }

    public static ItemStack removeEmpowered(ItemStack stack) {
        if (!stack.has(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED)) return stack;
        var enchantments = stack.get(EnchantmentHelper.getComponentType(stack));
        if (enchantments != null && !enchantments.isEmpty()) {
            var newEnchants = new ItemEnchantments.Mutable(enchantments);
            for (var enchantment : enchantments.keySet()) {
                if (enchantment.value()
                               .getMaxLevel()==1)
                    continue;
                int level = enchantments.getLevel(enchantment);
                if (level < stack.getOrDefault(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED, 0)) newEnchants.set(
                    enchantment, 0);
                else newEnchants.set(
                    enchantment, level - stack.getOrDefault(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED, 0));
            }
            EnchantmentHelper.setEnchantments(stack, newEnchants.toImmutable());
        }
        stack.remove(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED);
        return stack;
    }

    public static void shortenNegativeStatusEffects(@NotNull LivingEntity entity, int duration) {
        Collection<MobEffectInstance> newEffects = new ArrayList<>();
        Collection<Holder<MobEffect>> effectTypesToClear = new ArrayList<>();

        // remove them first, so hidden "stacked" effects are preserved
        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (instance.getEffect()
                        .value()
                        .getCategory() == MobEffectCategory.HARMFUL) {
                int newDurationTicks = instance.getDuration() - duration;
                if (newDurationTicks > 0) {
                    newEffects.add(
                        new MobEffectInstance(
                            instance.getEffect(), newDurationTicks, instance.getAmplifier(), instance.isAmbient(),
                            instance.isVisible(), instance.showIcon()
                        ));
                }
                if (!effectTypesToClear.contains(instance.getEffect())) {
                    effectTypesToClear.add(instance.getEffect());
                }
            }
        }
    }
}

package earth.terrarium.pastel.helpers.enchantments;

import earth.terrarium.pastel.registries.PastelAttributeTags;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class InexorableHelper {

    public static void checkAndRemoveSlowdownModifiers(LivingEntity entity) {
        var armorInexorable = isArmorActive(entity);
        var toolInexorable = Ench.hasEnchantment(
            entity.level()
                  .registryAccess(), PastelEnchantments.INEXORABLE, entity.getItemInHand(entity.getUsedItemHand())
        );

        var armorAttributes = BuiltInRegistries.ATTRIBUTE.getTag(PastelAttributeTags.INEXORABLE_ARMOR_EFFECTIVE);
        var toolAttributes = BuiltInRegistries.ATTRIBUTE.getTag(PastelAttributeTags.INEXORABLE_HANDHELD_EFFECTIVE);

        if (armorInexorable && armorAttributes.isPresent()) {
            for (Holder<Attribute> attributeRegistryEntry : armorAttributes.get()) {

                var attributeInstance = entity.getAttribute(attributeRegistryEntry);

                if (attributeInstance == null)
                    continue;

                var badMods = attributeInstance.getModifiers()
                                               .stream()
                                               .filter(modifier -> modifier.amount() < 0)
                                               .toList();

                badMods.forEach(modifier -> attributeInstance.removeModifier(modifier.id()));
            }
        }

        if (toolInexorable && toolAttributes.isPresent()) {
            for (Holder<Attribute> attributeRegistryEntry : toolAttributes.get()) {

                var attributeInstance = entity.getAttribute(attributeRegistryEntry);

                if (attributeInstance == null)
                    continue;

                var badMods = attributeInstance.getModifiers()
                                               .stream()
                                               .filter(modifier -> modifier.amount() < 0)
                                               .toList();

                badMods.forEach(modifier -> attributeInstance.removeModifier(modifier.id()));
            }
        }
    }

    public static boolean isArmorActive(LivingEntity entity) {
        return Ench.hasEnchantment(
            entity.level()
                  .registryAccess(), PastelEnchantments.INEXORABLE, entity.getItemBySlot(EquipmentSlot.CHEST)
        );
    }
}

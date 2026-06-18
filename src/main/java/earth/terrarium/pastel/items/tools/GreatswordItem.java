package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.Preenchanted;
import earth.terrarium.pastel.registries.PastelEntityAttributes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

/**
 * A sword with additional reach
 */
public class GreatswordItem extends SwordItem implements Preenchanted {

    public GreatswordItem(Tier material, int attackDamage, float attackSpeed, float extraReach, Properties settings) {
        super(
            material,
            settings
                .attributes(
                    ItemAttributeModifiers
                        .builder()
                        .add(
                            Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID,
                                material.getAttackDamageBonus() + attackDamage,
                                AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.MAINHAND
                        )
                        .add(
                            Attributes.ATTACK_SPEED,
                            new AttributeModifier(
                                BASE_ATTACK_SPEED_ID,
                                attackSpeed,
                                AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.MAINHAND
                        )
                        .add(
                            Attributes.ENTITY_INTERACTION_RANGE,
                            new AttributeModifier(
                                PastelEntityAttributes.REACH_MODIFIER_ID,
                                extraReach,
                                AttributeModifier.Operation.ADD_VALUE
                            ),
                            EquipmentSlotGroup.MAINHAND
                        )
                        .build()
                )
        );
    }

    @Override
    public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
        return Map.of(Enchantments.SWEEPING_EDGE, 4);
    }

}

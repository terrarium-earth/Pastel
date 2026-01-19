package earth.terrarium.pastel.data.dynamicregistries;

import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

import java.util.List;
import java.util.Optional;

import static earth.terrarium.pastel.PastelCommon.locate;

public class EnchantmentRegistry {
    public static void registerEnchantments(
        BootstrapContext<Enchantment> bootstrap,
        HolderLookup.RegistryLookup<Item> items,
        HolderLookup.RegistryLookup<Enchantment> enchantments
    ) {
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.BIG_CATCH, 2, 3, 20, 50, 4,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.FISHING_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.CLOVERS_FAVOR, 2, 3, 20, 50, 4,
            List.of(EquipmentSlotGroup.MAINHAND), true
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.DISARMING, 1, 2, 10, 40, 8,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.SWORD_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.EXUBERANCE, 5, 5, 10, 40, 2,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.SWORD_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.FIRST_STRIKE, 2, 2, 10, 40, 4,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.SWORD_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.FOUNDRY, 2, 1, 15, 65, 4,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.MINING_LOOT_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.IMPROVED_CRITICAL, 2, 2, 10, 40, 4,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.SWORD_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.INDESTRUCTIBLE, 2, 1, 30, 60, 4,
            List.of(EquipmentSlotGroup.MAINHAND), true
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.INERTIA, 1, 3, 10, 40, 8,
            List.of(EquipmentSlotGroup.MAINHAND), true
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.INEXORABLE, 1, 1, 50, 100, 8,
            List.of(EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND, EquipmentSlotGroup.CHEST),
            ItemTags.CHEST_ARMOR_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.INVENTORY_INSERTION, 2, 1, 15, 45, 4,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.MINING_LOOT_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.PEST_CONTROL, 1, 1, 10, 30, 8,
            List.of(EquipmentSlotGroup.MAINHAND)
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.RAZING, 5, 3, 20, 30, 2,
            List.of(EquipmentSlotGroup.MAINHAND), true
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.RESONANCE, 1, 1, 25, 100, 8,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.MINING_LOOT_ENCHANTABLE, true
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.SERENDIPITY_REEL, 2, 2, 40, 120, 4,
            List.of(EquipmentSlotGroup.MAINHAND)
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.SNIPING, 1, 2, 20, 50, 8,
            List.of(EquipmentSlotGroup.MAINHAND), true
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.STEADFAST, 10, 1, 30, 60, 1,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.DURABILITY_ENCHANTABLE
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.TIGHT_GRIP, 2, 2, 5, 35, 4,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.SWORD_ENCHANTABLE, DataComponentMap.builder()
                                                                                              .set(
                                                                                                  EnchantmentEffectComponents.ATTRIBUTES,
                                                                                                  List.of(
                                                                                                      new EnchantmentAttributeEffect(
                                                                                                          locate(
                                                                                                              "enchantment.tight_grip"),
                                                                                                          Attributes.ATTACK_SPEED,
                                                                                                          LevelBasedValue.perLevel(
                                                                                                              0.0625f),
                                                                                                          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                                                                                      ))
                                                                                              )
                                                                                              .build(), true
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.TREASURE_HUNTER, 2, 3, 15, 45, 4,
            List.of(EquipmentSlotGroup.MAINHAND), ItemTags.SWORD_ENCHANTABLE, true
        );
        registerEnchantment(
            bootstrap, items, enchantments, PastelEnchantments.VOIDING, 2, 1, 25, 50, 4,
            List.of(EquipmentSlotGroup.MAINHAND)
        );
    }

    private static void registerEnchantment(
        BootstrapContext<Enchantment> bootstrap, HolderLookup.RegistryLookup<Item> items,
        HolderLookup.RegistryLookup<Enchantment> enchantments, ResourceKey<Enchantment> key, int weight, int maxLevel,
        int minCost, int maxCost, int anvilCost, List<EquipmentSlotGroup> hasEffectsIn,
        Optional<HolderSet<Item>> primaryItems, DataComponentMap effects, boolean hasExclusiveSet
    ) {
        bootstrap.register(
            key, new Enchantment(
                Component.translatable("enchantment.pastel." + key.location()
                                                                  .getPath()), new Enchantment.EnchantmentDefinition(
                items.getOrThrow(PastelItemTags.getEnchantableTag(key)), primaryItems, weight, maxLevel,
                new Enchantment.Cost(minCost, 0), new Enchantment.Cost(maxCost, 0), anvilCost, hasEffectsIn
            ), hasExclusiveSet ? enchantments.getOrThrow(PastelEnchantmentTags.getExclusiveSetTag(key))
                               : HolderSet.empty(), effects
            )
        );
    }

    public static void registerEnchantment(
        BootstrapContext<Enchantment> bootstrap, HolderLookup.RegistryLookup<Item> items,
        HolderLookup.RegistryLookup<Enchantment> enchantments, ResourceKey<Enchantment> key, int weight, int maxLevel,
        int minCost, int maxCost, int anvilCost, List<EquipmentSlotGroup> hasEffectsIn, TagKey<Item> primaryItems,
        DataComponentMap effects, boolean hasExclusiveSet
    ) {
        registerEnchantment(
            bootstrap, items, enchantments, key, weight, maxLevel, minCost, maxCost, anvilCost,
            hasEffectsIn, Optional.of(items.getOrThrow(primaryItems)), effects, hasExclusiveSet
        );
    }

    public static void registerEnchantment(
        BootstrapContext<Enchantment> bootstrap, HolderLookup.RegistryLookup<Item> items,
        HolderLookup.RegistryLookup<Enchantment> enchantments, ResourceKey<Enchantment> key, int weight, int maxLevel,
        int minCost, int maxCost, int anvilCost, List<EquipmentSlotGroup> hasEffectsIn, TagKey<Item> primaryItems
    ) {
        registerEnchantment(
            bootstrap, items, enchantments, key, weight, maxLevel, minCost, maxCost, anvilCost,
            hasEffectsIn, Optional.of(items.getOrThrow(primaryItems)), DataComponentMap.EMPTY, false
        );
    }

    public static void registerEnchantment(
        BootstrapContext<Enchantment> bootstrap, HolderLookup.RegistryLookup<Item> items,
        HolderLookup.RegistryLookup<Enchantment> enchantments, ResourceKey<Enchantment> key, int weight, int maxLevel,
        int minCost, int maxCost, int anvilCost, List<EquipmentSlotGroup> hasEffectsIn, TagKey<Item> primaryItems,
        boolean hasExclusiveSet
    ) {
        registerEnchantment(
            bootstrap, items, enchantments, key, weight, maxLevel, minCost, maxCost, anvilCost, hasEffectsIn,
            Optional.of(items.getOrThrow(primaryItems)), DataComponentMap.EMPTY, hasExclusiveSet
        );
    }

    public static void registerEnchantment(
        BootstrapContext<Enchantment> bootstrap, HolderLookup.RegistryLookup<Item> items,
        HolderLookup.RegistryLookup<Enchantment> enchantments, ResourceKey<Enchantment> key, int weight, int maxLevel,
        int minCost, int maxCost, int anvilCost, List<EquipmentSlotGroup> hasEffectsIn, boolean hasExclusiveSet
    ) {
        registerEnchantment(
            bootstrap, items, enchantments, key, weight, maxLevel, minCost, maxCost, anvilCost,
            hasEffectsIn, Optional.empty(), DataComponentMap.EMPTY, hasExclusiveSet
        );
    }

    public static void registerEnchantment(
        BootstrapContext<Enchantment> bootstrap, HolderLookup.RegistryLookup<Item> items,
        HolderLookup.RegistryLookup<Enchantment> enchantments, ResourceKey<Enchantment> key, int weight, int maxLevel,
        int minCost, int maxCost, int anvilCost, List<EquipmentSlotGroup> hasEffectsIn
    ) {
        registerEnchantment(
            bootstrap, items, enchantments, key, weight, maxLevel, minCost, maxCost, anvilCost,
            hasEffectsIn, Optional.empty(), DataComponentMap.EMPTY, false
        );
    }
}

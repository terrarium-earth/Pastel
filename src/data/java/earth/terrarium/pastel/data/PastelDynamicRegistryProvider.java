package earth.terrarium.pastel.data;

import earth.terrarium.pastel.data.dynamicregistries.EnchantmentRegistry;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static earth.terrarium.pastel.PastelCommon.locate;

public class PastelDynamicRegistryProvider {
    public static RegistrySetBuilder createRegistryBuilders(CompletableFuture<HolderLookup.Provider> lookupProvider) {
        HolderLookup.Provider provider;
        HolderLookup.RegistryLookup<Item> items;
        HolderLookup.RegistryLookup<Enchantment> enchantments;
        try {
            provider = lookupProvider.get();
            var itemRegistryLookup = provider.lookup(Registries.ITEM);
            var enchantmentRegistryLookup = provider.lookup(Registries.ENCHANTMENT);
            if (itemRegistryLookup.isEmpty() || enchantmentRegistryLookup.isEmpty()) return new RegistrySetBuilder();
            items = itemRegistryLookup.get();
            enchantments = enchantmentRegistryLookup.get();
        } catch (Exception e) {
            return new RegistrySetBuilder();
        }
        return new RegistrySetBuilder().add(
                                           PastelRegistryKeys.RESONANCE_PROCESSOR,
                                           registerable -> PastelResonanceProcessors.provideResonanceProcessors(
                                               new DatagenProxy.BootstrapContext<>(registerable))
                                       )
                                       .add(
                                           NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                                           registerable -> PastelPlacedFeatures.addBiomeModifications(
                                               new DatagenProxy.BootstrapContext<>(registerable))
                                       )
                                       .add(
                                           Registries.ENCHANTMENT,
                                           bootstrap -> EnchantmentRegistry.registerEnchantments(bootstrap, items, enchantments)
                                       );
    }



}

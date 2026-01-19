package earth.terrarium.pastel.data;

import earth.terrarium.pastel.data.dynamicregistries.EnchantmentRegistry;
import earth.terrarium.pastel.data.dynamicregistries.ResonanceProcessorsRegistry;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PastelDynamicRegistryProvider {
    public static RegistrySetBuilder createRegistryBuilders() {
        return new RegistrySetBuilder().add(
                                           PastelRegistryKeys.RESONANCE_PROCESSOR,
                                           ResonanceProcessorsRegistry::registerResonanceProcessors
                                       )
                                       .add(
                                           NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                                           bootstrap -> PastelPlacedFeatures.addBiomeModifications(
                                               new DatagenProxy.BootstrapContext<>(bootstrap))
                                       )
                                       .add(Registries.ENCHANTMENT, EnchantmentRegistry::registerEnchantments);
    }

}

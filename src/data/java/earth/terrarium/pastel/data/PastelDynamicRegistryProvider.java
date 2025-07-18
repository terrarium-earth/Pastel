package earth.terrarium.pastel.data;

import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelPlacedFeatures;
import earth.terrarium.pastel.registries.PastelRegistryKeys;
import earth.terrarium.pastel.registries.PastelResonanceProcessors;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PastelDynamicRegistryProvider {
    public static RegistrySetBuilder createRegistryBuilders() {
        return new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, registerable -> PastelEnchantments.provideEnchantments(
                new DatagenProxy.BootstrapContext<>(registerable))
            )
            .add(PastelRegistryKeys.RESONANCE_PROCESSOR,
                 registerable -> PastelResonanceProcessors.provideResonanceProcessors(
                     new DatagenProxy.BootstrapContext<>(registerable))
            )
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, registerable -> PastelPlacedFeatures.addBiomeModifications(
                new DatagenProxy.BootstrapContext<>(registerable))
            );
    }
}

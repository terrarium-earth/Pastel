package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.pastel.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.explosion.*;
import de.dafuqs.spectrum.items.tools.*;
import net.fabricmc.fabric.api.event.registry.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.math.random.Random;

import java.util.*;

@SuppressWarnings("unused")
public class SpectrumRegistries {
	
	public static final List<RegistryLoader.Entry<?>> DYNAMIC_UNSYNCED = new ArrayList<>();
	public static final List<RegistryLoader.Entry<?>> DYNAMIC_SYNCED = new ArrayList<>();
	
	public static final SpectrumRegistry<GemstoneColor> GEMSTONE_COLORS = register("gemstone_color");
	public static final SpectrumRegistry<InkColor> INK_COLORS = register("ink_color");
	public static final SpectrumRegistry<FusionShrineRecipeWorldEffect> WORLD_EFFECTS = register("world_effect");
	public static final SpectrumRegistry<LizardFrillVariant> LIZARD_FRILL_VARIANT = register("lizard_frill_variant");
	public static final SpectrumRegistry<LizardHornVariant> LIZARD_HORN_VARIANT = register("lizard_horn_variant");
	public static final SpectrumRegistry<KindlingVariant> KINDLING_VARIANT = register("kindling_variant");
	public static final SpectrumRegistry<GlassArrowVariant> GLASS_ARROW_VARIANT = register("glass_arrow_variant");
	public static final SpectrumRegistry<ExplosionModifierType> EXPLOSION_MODIFIER_TYPES = register("explosion_effect_family");
	public static final SpectrumRegistry<ExplosionModifier> EXPLOSION_MODIFIERS = register("explosion_effect_modifier");
	public static final SpectrumRegistry<PastelUpgradeSignature> PASTEL_UPGRADE = register("pastel_upgrade");
	public static final SpectrumRegistry<MapCodec<? extends ResonanceDropProcessor>> RESONANCE_DROP_PROCESSOR_TYPES = register("resonance_drops_processor_types");
	
	public static final RegistryKey<Registry<ResonanceDropProcessor>> RESONANCE_DROPS_KEY = registerDynamicUnsynced("resonance_drops", ResonanceDropProcessor.CODEC);
	
	private static <T> RegistryKey<Registry<T>> registerDynamicSynced(String id, Codec<T> codec) {
		RegistryKey<Registry<T>> key = RegistryKey.ofRegistry(SpectrumCommon.locate(id));
		DYNAMIC_SYNCED.add(new RegistryLoader.Entry<>(key, codec, false));
		return key;
	}
	
	private static <T> RegistryKey<Registry<T>> registerDynamicUnsynced(String id, Codec<T> codec) {
		RegistryKey<Registry<T>> key = RegistryKey.ofRegistry(SpectrumCommon.locate(id));
		DYNAMIC_UNSYNCED.add(new RegistryLoader.Entry<>(key, codec, false));
		return key;
	}
	
	private static <T> SpectrumRegistry<T> register(String id) {
		return FabricRegistryBuilder.from(new SpectrumRegistry<>(RegistryKey.<T>ofRegistry(SpectrumCommon.locate(id)), Lifecycle.stable())).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	}
	
	public static <T> T getRandomTagEntry(Registry<T> registry, TagKey<T> tag, Random random, T fallback) {
		Optional<RegistryEntryList.Named<T>> tagEntries = registry.getEntryList(tag);
		if (tagEntries.isPresent()) {
			return tagEntries.get().get(random.nextInt(tagEntries.get().size())).value();
		} else {
			return fallback;
		}
	}
	
}

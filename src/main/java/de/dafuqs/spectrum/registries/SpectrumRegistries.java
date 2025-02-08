package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
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
	
	// TODO: do all these registries need to be synced?
	public static final Registry<FusionShrineRecipeWorldEffect> WORLD_EFFECT = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.WORLD_EFFECT).buildAndRegister();
	public static final Registry<GemstoneColor> GEMSTONE_COLOR = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.GEMSTONE_COLOR).buildAndRegister();
	public static final Registry<GlassArrowVariant> GLASS_ARROW_VARIANT = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.GLASS_ARROW_VARIANT).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	public static final Registry<InkColor> INK_COLOR = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.INK_COLOR).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	public static final Registry<KindlingVariant> KINDLING_VARIANT = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.KINDLING_VARIANT).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	public static final Registry<LizardFrillVariant> LIZARD_FRILL_VARIANT = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.LIZARD_FRILL_VARIANT).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	public static final Registry<LizardHornVariant> LIZARD_HORN_VARIANT = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.LIZARD_HORN_VARIANT).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	public static final Registry<PastelUpgradeSignature> PASTEL_UPGRADE = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.PASTEL_UPGRADE).buildAndRegister();
	
	public static final Registry<MapCodec<? extends ResonanceDropProcessor>> RESONANCE_DROP_PROCESSOR_TYPE = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.RESONANCE_DROP_PROCESSOR_TYPE).buildAndRegister();
	
	public static final Registry<ExplosionModifierType> EXPLOSION_MODIFIER_TYPE = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.EXPLOSION_MODIFIER_TYPE).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	public static final Registry<ExplosionModifier> EXPLOSION_MODIFIER = FabricRegistryBuilder.createSimple(SpectrumRegistryKeys.EXPLOSION_MODIFIER).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	
	public static void register() {
		DynamicRegistries.registerSynced(SpectrumRegistryKeys.RESONANCE_DROP_PROCESSOR, ResonanceDropProcessor.CODEC);
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

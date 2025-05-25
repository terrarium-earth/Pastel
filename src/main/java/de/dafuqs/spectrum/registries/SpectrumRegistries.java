package de.dafuqs.spectrum.registries;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.interaction.ResonanceProcessor;
import de.dafuqs.spectrum.api.item.GemstoneColor;
import de.dafuqs.spectrum.api.item.StampDataCategory;
import de.dafuqs.spectrum.api.pastel.PastelUpgradeSignature;
import de.dafuqs.spectrum.api.recipe.FusionShrineRecipeWorldEffect;
import de.dafuqs.spectrum.entity.variants.KindlingVariant;
import de.dafuqs.spectrum.entity.variants.LizardFrillVariant;
import de.dafuqs.spectrum.entity.variants.LizardHornVariant;
import de.dafuqs.spectrum.items.tools.GlassArrowVariant;
import de.dafuqs.spectrum.recipe.RecipeScaling;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

import java.util.Optional;

@SuppressWarnings("unused")
public class SpectrumRegistries {
	
	// TODO: do all these registries need to be synced?
	public static final Registry<FusionShrineRecipeWorldEffect> WORLD_EFFECT = register(SpectrumRegistryKeys.WORLD_EFFECT, false);
	public static final Registry<GemstoneColor> GEMSTONE_COLOR = register(SpectrumRegistryKeys.GEMSTONE_COLOR, false);
	public static final Registry<GlassArrowVariant> GLASS_ARROW_VARIANT = register(SpectrumRegistryKeys.GLASS_ARROW_VARIANT, true);
	public static final Registry<InkColor> INK_COLOR = register(SpectrumRegistryKeys.INK_COLOR, true);
	public static final Registry<KindlingVariant> KINDLING_VARIANT = register(SpectrumRegistryKeys.KINDLING_VARIANT, true);
	public static final Registry<LizardFrillVariant> LIZARD_FRILL_VARIANT = register(SpectrumRegistryKeys.LIZARD_FRILL_VARIANT, true);
	public static final Registry<LizardHornVariant> LIZARD_HORN_VARIANT = register(SpectrumRegistryKeys.LIZARD_HORN_VARIANT, true);
	public static final Registry<PastelUpgradeSignature> PASTEL_UPGRADE = register(SpectrumRegistryKeys.PASTEL_UPGRADE, false);
	public static final Registry<RecipeScaling> RECIPE_SCALING = register(SpectrumRegistryKeys.RECIPE_SCALING, true);
	
	public static final Registry<StampDataCategory> STAMP_DATA_CATEGORY = register(SpectrumRegistryKeys.STAMP_DATA_CATEGORY, true);
	
	public static final Registry<MapCodec<? extends ResonanceProcessor>> RESONANCE_PROCESSOR_TYPE = register(SpectrumRegistryKeys.RESONANCE_PROCESSOR_TYPE, false);

	public static void register(IEventBus bus) {
		bus.addListener(SpectrumRegistries::registerDyn);
	}

	public static void registerDyn(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(SpectrumRegistryKeys.RESONANCE_PROCESSOR, ResonanceProcessor.CODEC, ResonanceProcessor.CODEC);
	}
	
	private static <T> Registry<T> register(ResourceKey<? extends Registry<T>> key, boolean synced) {
		return new RegistryBuilder<>(key).sync(synced).create();
	}
	
	public static <T> T getRandomTagEntry(Registry<T> registry, TagKey<T> tag, RandomSource random, T fallback) {
		Optional<HolderSet.Named<T>> tagEntries = registry.getTag(tag);
		if (tagEntries.isPresent()) {
			return tagEntries.get().get(random.nextInt(tagEntries.get().size())).value();
		} else {
			return fallback;
		}
	}
	
}

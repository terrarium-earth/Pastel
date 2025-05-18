package de.dafuqs.spectrum.registries;

import com.mojang.serialization.Lifecycle;
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
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;

import java.util.Optional;

@SuppressWarnings("unused")
public class SpectrumRegistries {
	
	// TODO: do all these registries need to be synced?
	public static final SpectrumRegistry<FusionShrineRecipeWorldEffect> WORLD_EFFECT = register(SpectrumRegistryKeys.WORLD_EFFECT, false);
	public static final SpectrumRegistry<GemstoneColor> GEMSTONE_COLOR = register(SpectrumRegistryKeys.GEMSTONE_COLOR, false);
	public static final SpectrumRegistry<GlassArrowVariant> GLASS_ARROW_VARIANT = register(SpectrumRegistryKeys.GLASS_ARROW_VARIANT, true);
	public static final SpectrumRegistry<InkColor> INK_COLOR = register(SpectrumRegistryKeys.INK_COLOR, true);
	public static final SpectrumRegistry<KindlingVariant> KINDLING_VARIANT = register(SpectrumRegistryKeys.KINDLING_VARIANT, true);
	public static final SpectrumRegistry<LizardFrillVariant> LIZARD_FRILL_VARIANT = register(SpectrumRegistryKeys.LIZARD_FRILL_VARIANT, true);
	public static final SpectrumRegistry<LizardHornVariant> LIZARD_HORN_VARIANT = register(SpectrumRegistryKeys.LIZARD_HORN_VARIANT, true);
	public static final SpectrumRegistry<PastelUpgradeSignature> PASTEL_UPGRADE = register(SpectrumRegistryKeys.PASTEL_UPGRADE, false);
	public static final SpectrumRegistry<RecipeScaling> RECIPE_SCALING = register(SpectrumRegistryKeys.RECIPE_SCALING, true);
	
	public static final Registry<StampDataCategory> STAMP_DATA_CATEGORY = register(SpectrumRegistryKeys.STAMP_DATA_CATEGORY, true);
	
	public static final SpectrumRegistry<MapCodec<? extends ResonanceProcessor>> RESONANCE_PROCESSOR_TYPE = register(SpectrumRegistryKeys.RESONANCE_PROCESSOR_TYPE, false);
	
	public static void register() {
		DynamicRegistries.registerSynced(SpectrumRegistryKeys.RESONANCE_PROCESSOR, ResonanceProcessor.CODEC);
	}
	
	private static <T> SpectrumRegistry<T> register(ResourceKey<? extends Registry<T>> key, boolean synced) {
		FabricRegistryBuilder<T, SpectrumRegistry<T>> builder = FabricRegistryBuilder.from(new SpectrumRegistry<>(key, Lifecycle.stable()));
		if (synced) builder.attribute(RegistryAttribute.SYNCED);
		return builder.buildAndRegister();
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

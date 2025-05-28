package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.data.DatagenProxy;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import net.neoforged.neoforge.registries.holdersets.NotHolderSet;
import net.neoforged.neoforge.registries.holdersets.OrHolderSet;

import java.util.function.Function;

public class SpectrumPlacedFeatures {

	public static ResourceKey<PlacedFeature> of(String id) {
		return ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate(id));
	}

	private static HolderSet<Biome> tag(DatagenProxy.BootstrapContext<BiomeModifier> context, TagKey<Biome> tag) {
		return context
				.registerable()
				.lookup(Registries.BIOME)
				.get(tag)
				.<HolderSet<Biome>>map(Function.identity())
				.orElse(HolderSet.empty());
	}

	private static void addFeature(DatagenProxy.BootstrapContext<BiomeModifier> context, TagKey<Biome> tag, GenerationStep.Decoration step, ResourceKey<PlacedFeature> placedFeature) {
		var biomes = context.registerable().lookup(Registries.BIOME);

		biomes.get(tag).ifPresent(set -> addFeature(context, set, step, placedFeature));
	}

	private static void addFeature(DatagenProxy.BootstrapContext<BiomeModifier> context, HolderSet<Biome> biomeSet, GenerationStep.Decoration step, ResourceKey<PlacedFeature> placedFeature) {
		var placedFeatures = context.registerable().lookup(Registries.PLACED_FEATURE);

		context.registerable().register(
			ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, placedFeature.location()),
			new BiomeModifiers.AddFeaturesBiomeModifier(
				biomeSet,
				HolderSet.direct(placedFeatures.getOrThrow(placedFeature)),
				step
			)
		);
	}

	// TODO Make this register only one modifier per biome tag
	public static void addBiomeModifications(DatagenProxy.BootstrapContext<BiomeModifier> context) {
		// Geodes
		addFeature(context, Tags.Biomes.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_STRUCTURES, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("citrine_geode")));
		addFeature(context, Tags.Biomes.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_STRUCTURES, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("topaz_geode")));
		
		// Ores
		addFeature(context, Tags.Biomes.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("shimmerstone_ore")));
		addFeature(context, Tags.Biomes.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("azurite_ore")));
		addFeature(context, Tags.Biomes.IS_NETHER, GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("stratine_ore")));
		addFeature(context, Tags.Biomes.IS_END, GenerationStep.Decoration.UNDERGROUND_ORES, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("paltaeria_ore")));
		
		addFeature(context, SpectrumBiomeTags.COLORED_TREES_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("colored_tree_patch")));
		
		// Plants
		addFeature(context, SpectrumBiomeTags.MERMAIDS_BRUSHES_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("mermaids_brushes")));
		addFeature(context, SpectrumBiomeTags.CLOVER_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("clover_patch")));

		if (SpectrumCommon.CONFIG.QuitoxicSpawnTag) {
			addFeature(context, SpectrumBiomeTags.QUITOXIC_REEDS_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("quitoxic_reeds")));
		} else {
			var overworld = tag(context, Tags.Biomes.IS_OVERWORLD);
			var aquatic = tag(context, Tags.Biomes.IS_AQUATIC);
			var hot = tag(context, Tags.Biomes.IS_HOT);
			var vegetationDense = tag(context, Tags.Biomes.IS_DENSE_VEGETATION_OVERWORLD);
			var swamp = tag(context, Tags.Biomes.IS_SWAMP);
			var wet = tag(context, Tags.Biomes.IS_WET_OVERWORLD);

			//Either the biome is hot, lush, and wet, or it is a straight-up swamp.
			var set = new AndHolderSet<>(
				overworld,
				new NotHolderSet<>(context.registerable().registryLookup(Registries.BIOME).orElseThrow(), aquatic),
				new OrHolderSet<>(
					new AndHolderSet<>(
						hot,
						vegetationDense
					),
					swamp
				),
				wet
			);

			addFeature(context, set, GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("quitoxic_reeds")));
		}

		// Dragonbone in the Overworld
		addFeature(context, SpectrumBiomeTags.DRAGONBONE_FOSSILS_GENERATING_IN, GenerationStep.Decoration.UNDERGROUND_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("dragon_fossil_overworld_buried")));
		addFeature(context, SpectrumBiomeTags.DRAGONBONE_FOSSILS_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, SpectrumCommon.locate("dragon_fossil_overworld_exposed")));
	}
	
}

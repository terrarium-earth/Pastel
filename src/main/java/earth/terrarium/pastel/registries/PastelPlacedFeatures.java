package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Function;

public class PastelPlacedFeatures {

    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate(id));
    }

    private static HolderSet<Biome> tag(BootstrapContext<BiomeModifier> bootstrap, TagKey<Biome> tag) {
        return bootstrap
            .lookup(Registries.BIOME)
            .get(tag)
            .<HolderSet<Biome>>map(Function.identity())
            .orElse(HolderSet.empty());
    }

    private static void addFeature(
        BootstrapContext<BiomeModifier> bootstrap, TagKey<Biome> tag, GenerationStep.Decoration step,
        ResourceKey<PlacedFeature> placedFeature
    ) {
        var biomes = bootstrap
                            .lookup(Registries.BIOME);

        biomes.get(tag)
              .ifPresent(set -> addFeature(bootstrap, set, step, placedFeature));
    }

    private static void addFeature(
        BootstrapContext<BiomeModifier> bootstrap, HolderSet<Biome> biomeSet, GenerationStep.Decoration step,
        ResourceKey<PlacedFeature> placedFeature
    ) {
        var placedFeatures = bootstrap
                                    .lookup(Registries.PLACED_FEATURE);

        bootstrap
               .register(
                   ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, placedFeature.location()),
                   new BiomeModifiers.AddFeaturesBiomeModifier(
                       biomeSet,
                       HolderSet.direct(placedFeatures.getOrThrow(placedFeature)),
                       step
                   )
               );
    }

    // TODO Make this register only one modifier per biome tag
    public static void addBiomeModifications(BootstrapContext<BiomeModifier> bootstrap) {
        // Geodes
        addFeature(
            bootstrap, Tags.Biomes.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("citrine_geode"))
        );
        addFeature(
            bootstrap, Tags.Biomes.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("topaz_geode"))
        );

        // Ores
        addFeature(
            bootstrap, Tags.Biomes.IS_OVERWORLD, GenerationStep.Decoration.UNDERGROUND_ORES,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("shimmerstone_ore"))
        );
        addFeature(
            bootstrap, Tags.Biomes.IS_NETHER, GenerationStep.Decoration.UNDERGROUND_ORES,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("stratine_ore"))
        );
        addFeature(
            bootstrap, Tags.Biomes.IS_END, GenerationStep.Decoration.UNDERGROUND_ORES,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("paltaeria_ore"))
        );

        addFeature(
            bootstrap, PastelBiomeTags.COLORED_TREES_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("colored_tree_patch"))
        );

        // Plants
        addFeature(
            bootstrap, PastelBiomeTags.MERMAIDS_BRUSHES_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("mermaids_brushes"))
        );
        addFeature(
            bootstrap, PastelBiomeTags.CLOVER_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("clover_patch"))
        );

        addFeature(
            bootstrap, PastelBiomeTags.QUITOXIC_REEDS_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("quitoxic_reeds"))
        );

        //TODO: find out why this is fucked
        //if (PastelCommon.CONFIG.QuitoxicSpawnTag) {
        //	addFeature(bootstrap, PastelBiomeTags.QUITOXIC_REEDS_GENERATING_IN, GenerationStep.Decoration
        //	.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("quitoxic_reeds")));
        //} else {
        //	var overworld = tag(bootstrap, Tags.Biomes.IS_OVERWORLD);
        //	var aquatic = tag(bootstrap, Tags.Biomes.IS_AQUATIC);
        //	var hot = tag(bootstrap, Tags.Biomes.IS_HOT);
        //	var vegetationDense = tag(bootstrap, Tags.Biomes.IS_DENSE_VEGETATION_OVERWORLD);
        //	var swamp = tag(bootstrap, Tags.Biomes.IS_SWAMP);
        //	var wet = tag(bootstrap, Tags.Biomes.IS_WET_OVERWORLD);
//
        //	//Either the biome is hot, lush, and wet, or it is a straight-up swamp.
        //	var set = new AndHolderSet<>(
        //		overworld,
        //		new NotHolderSet<>(bootstrap.registerable().registryLookup(Registries.BIOME).orElseThrow(), aquatic),
        //		new OrHolderSet<>(
        //			new AndHolderSet<>(
        //				hot,
        //				vegetationDense
        //			),
        //			swamp
        //		),
        //		wet
        //	);
//
        //	addFeature(bootstrap, set, GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries
        //	.PLACED_FEATURE, PastelCommon.locate("quitoxic_reeds")));
        //}

        // Dragonbone in the Overworld
        addFeature(
            bootstrap, PastelBiomeTags.DRAGONBONE_FOSSILS_GENERATING_IN, GenerationStep.Decoration.UNDERGROUND_DECORATION,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("dragon_fossil_overworld_buried"))
        );
        addFeature(
            bootstrap, PastelBiomeTags.DRAGONBONE_FOSSILS_GENERATING_IN, GenerationStep.Decoration.VEGETAL_DECORATION,
            ResourceKey.create(Registries.PLACED_FEATURE, PastelCommon.locate("dragon_fossil_overworld_exposed"))
        );
    }

}

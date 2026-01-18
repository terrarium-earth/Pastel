package earth.terrarium.pastel.data.models.block.resource;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public class GemstoneBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators){
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.TOPAZ_CLUSTER, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_TOPAZ_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.MEDIUM_TOPAZ_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_TOPAZ_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.BUDDING_TOPAZ);
        generators.blockStateOutput.accept(PastelModelHelper.simpleMirroredBlockModel(generators,PastelBlocks.TOPAZ_BLOCK.get()));

        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.CITRINE_CLUSTER, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_CITRINE_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.MEDIUM_CITRINE_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_CITRINE_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.BUDDING_CITRINE);
        generators.blockStateOutput.accept(PastelModelHelper.simpleMirroredBlockModel(generators,PastelBlocks.CITRINE_BLOCK.get()));
        
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.ONYX_CLUSTER, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_ONYX_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.MEDIUM_ONYX_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_ONYX_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.BUDDING_ONYX);
        generators.blockStateOutput.accept(PastelModelHelper.simpleMirroredBlockModel(generators,PastelBlocks.ONYX_BLOCK.get()));
        
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.MOONSTONE_CLUSTER, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_MOONSTONE_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.MEDIUM_MOONSTONE_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_MOONSTONE_BUD, ModelTemplates.CROSS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.BUDDING_MOONSTONE);
        generators.blockStateOutput.accept(PastelModelHelper.simpleMirroredBlockModel(generators,PastelBlocks.MOONSTONE_BLOCK.get()));
        
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.TOPAZ_POWDER_BLOCK);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.AMETHYST_POWDER_BLOCK);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.CITRINE_POWDER_BLOCK);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.ONYX_POWDER_BLOCK);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.MOONSTONE_POWDER_BLOCK);

        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.BISMUTH_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_BISMUTH_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_BISMUTH_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.BISMUTH_BLOCK);

        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_COAL_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_COAL_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.COAL_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_IRON_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_IRON_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.IRON_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_GOLD_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_GOLD_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.GOLD_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_DIAMOND_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_DIAMOND_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.DIAMOND_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_EMERALD_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_EMERALD_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.EMERALD_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_REDSTONE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_REDSTONE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.REDSTONE_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_LAPIS_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_LAPIS_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LAPIS_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_COPPER_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_COPPER_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.COPPER_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_QUARTZ_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_QUARTZ_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.QUARTZ_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_NETHERITE_SCRAP_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_NETHERITE_SCRAP_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.NETHERITE_SCRAP_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_ECHO_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_ECHO_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.ECHO_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_GLOWSTONE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_GLOWSTONE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.GLOWSTONE_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_PRISMARINE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_PRISMARINE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.PRISMARINE_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);

        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.AZURITE_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_AZURITE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_AZURITE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.MALACHITE_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_MALACHITE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_MALACHITE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.BLOODSTONE_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.LARGE_BLOODSTONE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, PastelBlocks.SMALL_BLOODSTONE_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
    }
}

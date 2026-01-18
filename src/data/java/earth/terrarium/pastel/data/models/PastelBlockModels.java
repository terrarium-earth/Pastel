package earth.terrarium.pastel.data.models;

import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.compat.ae2.AE2Compat;
import earth.terrarium.pastel.compat.create.CreateCompat;
import earth.terrarium.pastel.data.models.block.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class PastelBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators){
        CraftingBlockModels.generateBlockModels(generators);
        DecoBlockModels.generateBlockModels(generators);
        FluidBlockModels.generateBlockModels(generators);
        FunctionalBlockModels.generateBlockModels(generators);
        PastelNetworkBlockModels.generateBlockModels(generators);
        PlantBlockModels.generateBlockModels(generators);
        ResourceBlockModels.generateBlockModels(generators);
        StructureBlockModels.generateBlockModels(generators);

        if(PastelIntegrationPacks.isIntegrationPackActive("ae2"))AE2Compat.generateBlockModels(generators);
        if(PastelIntegrationPacks.isIntegrationPackActive("create")) CreateCompat.generateBlockModels(generators);
    }
    public static void generateItemModels(ItemModelGenerators generators){
        DecoBlockModels.generateItemModels(generators);
        FunctionalBlockModels.generateItemModels(generators);
        PlantBlockModels.generateItemModels(generators);
        StructureBlockModels.generateItemModels(generators);
    }
    public static void setupRenderLayers(FMLClientSetupEvent event) {

	}
}

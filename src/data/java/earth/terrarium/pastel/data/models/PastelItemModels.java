package earth.terrarium.pastel.data.models;

import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.compat.ae2.AE2Compat;
import earth.terrarium.pastel.compat.create.CreateCompat;
import earth.terrarium.pastel.data.models.item.*;
import net.minecraft.data.models.ItemModelGenerators;

public class PastelItemModels {
    // these are named after the creative tabs so blame whoever sorted those
    public static void generateItemModels(ItemModelGenerators generators) {
        CuisineItemModels.generateItemModels(generators);
        DecorationItemModels.generateItemModels(generators);
        InstrumentItemModels.generateItemModels(generators);
        NaturalBlockItemModels.generateItemModels(generators);
        ResourceItemModels.generateItemModels(generators);
        TechnicalItemModels.generateItemModels(generators);
        ToolItemModels.generateItemModels(generators);
        VanillaItemGroupItemModels.generateItemModels(generators);

        if (PastelIntegrationPacks.isIntegrationPackActive("ae2")) AE2Compat.generateItemModels(generators);
        if (PastelIntegrationPacks.isIntegrationPackActive("create")) CreateCompat.generateItemModels(generators);
    }
}

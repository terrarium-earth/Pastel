package earth.terrarium.pastel.data.models;

import earth.terrarium.pastel.data.models.item.*;
import net.minecraft.data.models.ItemModelGenerators;

public class PastelItemModels {
    // these are named after the creative tabs so blame whoever sorted those
    public static void generateItemModels(ItemModelGenerators generators){
        CuisineItemModels.generateItemModels(generators);
        DecorationItemModels.generateItemModels(generators);
        InstrumentItemModels.generateItemModels(generators);
        NaturalBlockItemModels.generateItemModels(generators);
        ResourceItemModels.generateItemModels(generators);
        TechnicalItemModels.generateItemModels(generators);
        ToolItemModels.generateItemModels(generators);
        VanillaItemGroupItemModels.generateItemModels(generators);
    }
}

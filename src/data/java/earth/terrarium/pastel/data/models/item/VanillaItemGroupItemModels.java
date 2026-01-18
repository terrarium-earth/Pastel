package earth.terrarium.pastel.data.models.item;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.data.models.ItemModelGenerators;

public class VanillaItemGroupItemModels {
    public static void generateItemModels(ItemModelGenerators generators){
        // Spawning items
        PastelModelHelper.ITEM.simple(generators, PastelItems.BUCKET_OF_ERASER);
        PastelModelHelper.ITEM.parented(generators,PastelItems.EGG_LAYING_WOOLY_PIG_SPAWN_EGG,PastelModels.SPAWN_EGG);
        PastelModelHelper.ITEM.parented(generators,PastelItems.PRESERVATION_TURRET_SPAWN_EGG,PastelModels.SPAWN_EGG);
        PastelModelHelper.ITEM.parented(generators,PastelItems.KINDLING_SPAWN_EGG,PastelModels.SPAWN_EGG);
        PastelModelHelper.ITEM.parented(generators,PastelItems.LIZARD_SPAWN_EGG,PastelModels.SPAWN_EGG);
        PastelModelHelper.ITEM.parented(generators,PastelItems.ERASER_SPAWN_EGG,PastelModels.SPAWN_EGG);
    }
}

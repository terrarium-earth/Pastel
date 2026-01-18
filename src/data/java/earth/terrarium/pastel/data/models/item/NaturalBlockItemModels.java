package earth.terrarium.pastel.data.models.item;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.models.ItemModelGenerators;

public class NaturalBlockItemModels {
    public static void generateItemModels(ItemModelGenerators generators){
        // it's ash.
        PastelModelHelper.ITEM.simple(generators, PastelItems.ASH_FLAKES);
    }
}

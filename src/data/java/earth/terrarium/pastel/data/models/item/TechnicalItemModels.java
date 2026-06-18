package earth.terrarium.pastel.data.models.item;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.models.ItemModelGenerators;

import static net.minecraft.world.item.Items.BUNDLE;

public class TechnicalItemModels {
    public static void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.ITEM.parented(generators, PastelItems.EXTENDED_BUNDLE_ITEM, BUNDLE);
    }
}

package earth.terrarium.pastel.data.models.item.tools;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.models.ItemModelGenerators;

import static net.minecraft.world.item.Items.PAINTING;

public class MagicToolItemModels {
    public static void generateItemModels(ItemModelGenerators generators) {
        // Magical Tools
        PastelModelHelper.ITEM.simple(generators, PastelItems.BAG_OF_HOLDING);
        PastelModelHelper.ITEM.simple(generators, PastelItems.WIRE_HOOK);
        PastelModelHelper.ITEM.handheld(generators, PastelItems.EXCHANGING_STAFF);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BLOCK_FLOODER);
        PastelModelHelper.ITEM.parented(generators, PastelItems.ENDER_CANVAS, PAINTING);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PERTURBED_EYE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PRIMORDIAL_LIGHTER);

        PastelModelHelper.ITEM.simple(generators, PastelItems.NIGHT_SALTS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SOOTHING_BOUQUET);
        PastelModelHelper.ITEM.layered(generators, PastelItems.CONCEALING_OILS, "", "_tint", "_overlay");
        PastelModelHelper.ITEM.simple(generators, PastelItems.BITTER_OILS);

        // Specialty Magical Tools
        PastelModelHelper.ITEM.simple(generators, PastelItems.CELESTIAL_POCKETWATCH);
        PastelModelHelper.ITEM.simple(generators, PastelItems.ARTISANS_ATLAS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.GILDED_BOOK);
        PastelModelHelper.ITEM.simple(generators, PastelItems.EVERPROMISE_RIBBON);
    }
}

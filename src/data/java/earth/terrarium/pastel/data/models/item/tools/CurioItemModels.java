package earth.terrarium.pastel.data.models.item.tools;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.models.ItemModelGenerators;

public class CurioItemModels {
    public static void generateItemModels(ItemModelGenerators generators) {
        // Trinkets
        PastelModelHelper.ITEM.simple(generators, PastelItems.FANCIFUL_TUFF_RING);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FANCIFUL_BELT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FANCIFUL_PENDANT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FANCIFUL_CIRCLET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FANCIFUL_GLOVES);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FANCIFUL_BISMUTH_RING);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PRISCILLENT_SPECTACLES);
        PastelModelHelper.ITEM.simple(generators, PastelItems.JEOPARDANT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SEVEN_LEAGUE_BOOTS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.COTTON_CLOUD_BOOTS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.RADIANCE_PIN);
        PastelModelHelper.ITEM.simple(generators, PastelItems.TOTEM_PENDANT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.TAKEOFF_BELT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.AZURE_DIKE_BELT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.AZURE_DIKE_RING);
        PastelModelHelper.ITEM.simple(generators, PastelItems.AZURESQUE_DIKE_CORE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SHIELDGRASP_AMULET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.HEARTSINGERS_REWARD);
        PastelModelHelper.ITEM.simple(generators, PastelItems.RING_OF_CONSUMPTION);
        PastelModelHelper.ITEM.simple(generators, PastelItems.GLOVES_OF_DAWNS_GRASP);
        PastelModelHelper.ITEM.simple(generators, PastelItems.RING_OF_PURSUIT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.RING_OF_DENSER_STEPS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.RING_OF_AETHERIAL_GRACE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.LAURELS_OF_SERENITY);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PIGMENT_PALETTE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.ARTISTS_PALETTE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.GLEAMING_PIN);
        PastelModelHelper.ITEM.layered(generators, PastelItems.LESSER_POTION_PENDANT, "_base", "_overlay");
        PastelModelHelper.ITEM
            .layered(
                generators,
                PastelItems.GREATER_POTION_PENDANT,
                "_base",
                "_overlay_1",
                "_overlay_2",
                "_overlay_3"
            );
        PastelModelHelper.ITEM.simple(generators, PastelItems.WEEPING_CIRCLET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PUFF_CIRCLET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.WHISPY_CIRCLET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CIRCLET_OF_ARROGANCE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.NEAT_RING);
        PastelModelHelper.ITEM.simple(generators, PastelItems.AETHER_GRACED_NECTAR_GLOVES);
    }
}

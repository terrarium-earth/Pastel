package earth.terrarium.pastel.data.models.item;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.models.ItemModelGenerators;

public class InstrumentItemModels {
    public static void generateItemModels(ItemModelGenerators generators){
        PastelModelHelper.ITEM.simple(generators,PastelItems.PEDESTAL_TIER_1_STRUCTURE_PLACER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PEDESTAL_TIER_2_STRUCTURE_PLACER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.PEDESTAL_TIER_3_STRUCTURE_PLACER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.FUSION_SHRINE_STRUCTURE_PLACER);
        PastelModelHelper.ITEM.simple(generators, PastelItems.ENCHANTER_STRUCTURE_PLACER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.SPIRIT_INSTILLER_STRUCTURE_PLACER);
        PastelModelHelper.ITEM.simple(generators,PastelItems.CINDERHEARTH_STRUCTURE_PLACER);
    }
}

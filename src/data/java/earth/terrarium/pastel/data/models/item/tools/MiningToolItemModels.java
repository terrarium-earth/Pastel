package earth.terrarium.pastel.data.models.item.tools;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.models.ItemModelGenerators;

public class MiningToolItemModels {
    public static void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.ITEM.handheld(generators, PastelItems.MULTITOOL);
        PastelModelHelper.ITEM.handheld(generators, PastelItems.TENDER_PICKAXE);
        PastelModelHelper.ITEM.handheld(generators, PastelItems.LUCKY_PICKAXE);
        PastelModelHelper.ITEM.handheld(generators, PastelItems.OBLIVION_PICKAXE);
        PastelModelHelper.ITEM.handheld(generators, PastelItems.RESONANT_PICKAXE);
        PastelModelHelper.ITEM.handheld(generators, PastelItems.DRAGONRENDING_PICKAXE);

        PastelModelHelper.ITEM.handheld(generators, PastelItems.BEDROCK_PICKAXE);
        PastelModelHelper.ITEM.handheld(generators, PastelItems.BEDROCK_SHOVEL);
        PastelModelHelper.ITEM.handheld(generators, PastelItems.BEDROCK_HOE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_SHEARS);

    }
}

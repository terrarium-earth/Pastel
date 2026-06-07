package earth.terrarium.pastel.data.models.item;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.data.models.item.tools.CurioItemModels;
import earth.terrarium.pastel.data.models.item.tools.MagicToolItemModels;
import earth.terrarium.pastel.data.models.item.tools.MiningToolItemModels;
import earth.terrarium.pastel.data.models.item.tools.WeaponArmorItemModels;
import earth.terrarium.pastel.registries.*;
import net.minecraft.data.models.ItemModelGenerators;

public class ToolItemModels {
    public static void generateItemModels(ItemModelGenerators generators){
        CurioItemModels.generateItemModels(generators);
        MagicToolItemModels.generateItemModels(generators);
        MiningToolItemModels.generateItemModels(generators);
        WeaponArmorItemModels.generateItemModels(generators);

        PastelModelHelper.ITEM.simple(generators,PastelItems.GUIDEBOOK);
        PastelModelHelper.ITEM.simple(generators,PastelItems.CRAFTING_TABLET);

        PastelModelHelper.ITEM.simple(generators,PastelItems.BOTTLE_OF_FADING);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BOTTLE_OF_FAILING);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BOTTLE_OF_RUIN);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BOTTLE_OF_FORFEITURE);
        PastelModelHelper.ITEM.simple(generators,PastelItems.BOTTLE_OF_DECAY_AWAY);

        PastelModelHelper.ITEM.simple(generators,PastelItems.DIVINATION_HEART);

        PastelModelHelper.ITEM.simple(generators,PastelItems.MUSIC_DISC_DISCOVERY);
        PastelModelHelper.ITEM.simple(generators,PastelItems.MUSIC_DISC_CREDITS);
        PastelModelHelper.ITEM.simple(generators,PastelItems.MUSIC_DISC_DIVINITY);
        PastelModelHelper.ITEM.parented(generators, PastelItems.MUSIC_DISC_MEMORIAL, PastelItems.MUSIC_DISC_DISCOVERY);

        PastelModelHelper.ITEM.simple(generators,PastelItems.INK_ASSORTMENT);
        PastelModelHelper.ITEM.parented(generators,PastelItems.CREATIVE_INK_ASSORTMENT,PastelItems.INK_ASSORTMENT);
    }
}

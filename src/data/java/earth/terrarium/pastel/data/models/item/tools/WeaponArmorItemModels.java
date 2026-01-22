package earth.terrarium.pastel.data.models.item.tools;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.data.models.ItemModelGenerators;

public class WeaponArmorItemModels {
    public static void generateItemModels(ItemModelGenerators generators) {

        PastelModelHelper.ITEM.handheld(generators, PastelItems.RAZOR_FALCHION);

        PastelModelHelper.ITEM.simple(generators, PastelItems.MALACHITE_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.TOPAZ_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.AMETHYST_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CITRINE_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.ONYX_GLASS_ARROW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MOONSTONE_GLASS_ARROW);

        PastelModelHelper.registerLayeredItemModel(
            generators, PastelItems.NIGHTFALLS_BLADE.get(), PastelModels.HANDHELD_THREE_LAYERS, "", "_tint",
            "_overlay"
        );

        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_HELMET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_CHESTPLATE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_LEGGINGS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BEDROCK_BOOTS);

        PastelModelHelper.ITEM.simple(generators, PastelItems.ONYX_HELMET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.AMETHYST_CHESTPLATE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.TOPAZ_LEGGINGS);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CITRINE_BOOTS);
    }
}

package earth.terrarium.pastel.data.models.item;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.*;
import net.minecraft.data.models.ItemModelGenerators;

import static net.minecraft.world.item.Items.GOLDEN_CARROT;

public class CuisineItemModels {
    public static void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.ITEM.simple(generators, PastelItems.BLOODBOIL_SYRUP);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MILKY_RESIN);

        // Food & drinks
        PastelModelHelper.ITEM.simple(generators, PastelItems.MOONSTRUCK_NECTAR);
        PastelModelHelper.ITEM.simple(generators, PastelItems.JADE_JELLY);
        PastelModelHelper.ITEM.simple(generators, PastelItems.GLASS_PEACH);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FISSURE_PLUM);
        PastelModelHelper.ITEM.simple(generators, PastelItems.NIGHTDEW_SPROUT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.NECTARDEW_BURGEON);
        PastelModelHelper.ITEM.simple(generators, PastelItems.RESTORATION_TEA);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CLOTTED_CREAM);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FRESH_CHOCOLATE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.HOT_CHOCOLATE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.KARAK_CHAI);
        PastelModelHelper.ITEM.simple(generators, PastelItems.AZALEA_TEA);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BODACIOUS_BERRY_BAR);
        PastelModelHelper.ITEM.simple(generators, PastelItems.DEMON_TEA);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SCONE);

        PastelModelHelper.ITEM.layered(generators, PastelItems.CHEONG, "", "_overlay", "_cap");
        PastelModelHelper.ITEM.simple(generators, PastelItems.MERMAIDS_JAM);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MERMAIDS_POPCORN);
        PastelModelHelper.ITEM.simple(generators, PastelItems.LE_FISHE_AU_CHOCOLAT);

        //	PastelModelHelper.ITEM.simple(generators, PastelItems.STUFFED_PETALS);
        //	PastelModelHelper.ITEM.simple(generators, PastelItems.PASTICHE);
        //	PastelModelHelper.ITEM.simple(generators, PastelItems.VITTORIAS_ROAST);

        PastelModelHelper.ITEM.layered(generators, PastelItems.INFUSED_BEVERAGE, "", "_highlight");
        PastelModelHelper.ITEM.simple(generators, PastelItems.SUSPICIOUS_BREW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.REPRISE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PURE_ALCOHOL);
        PastelModelHelper.ITEM.simple(generators, PastelItems.JADE_WINE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CHRYSOCOLLA);

        PastelModelHelper.ITEM.simple(generators, PastelItems.HONEY_PASTRY);
        PastelModelHelper.ITEM.simple(generators, PastelItems.LUCKY_ROLL);
        PastelModelHelper.ITEM.simple(generators, PastelItems.TRIPLE_MEAT_POT_PIE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.GLISTERING_JELLY_TEA);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FREIGEIST);

        PastelModelHelper.ITEM.simple(generators, PastelItems.STAR_CANDY);
        PastelModelHelper.ITEM.simple(generators, PastelItems.ENCHANTED_STAR_CANDY);

        PastelModelHelper.ITEM.parented(generators, PastelItems.ENCHANTED_GOLDEN_CARROT, GOLDEN_CARROT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.JARAMEL);

        PastelModelHelper.ITEM.simple(generators, PastelItems.JARAMEL_TART);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SALTED_JARAMEL_TART);
        PastelModelHelper.ITEM.simple(generators, PastelItems.ASHEN_TART);
        PastelModelHelper.ITEM.simple(generators, PastelItems.WEEPING_TART);
        PastelModelHelper.ITEM.simple(generators, PastelItems.WHISPY_TART);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PUFF_TART);

        PastelModelHelper.ITEM.simple(generators, PastelItems.JARAMEL_TRIFLE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SALTED_JARAMEL_TRIFLE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MONSTER_TRIFLE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.DEMON_TRIFLE);

        PastelModelHelper.ITEM.simple(generators, PastelItems.MYCEYLON);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MYCEYLON_APPLE_PIE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MYCEYLON_PUMPKIN_PIE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MYCEYLON_COOKIE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.ALOE_LEAF);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SAWBLADE_HOLLY_BERRY);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PRICKLY_BAYLEAF);
        PastelModelHelper.ITEM.simple(generators, PastelItems.TRIPLE_MEAT_POT_STEW);
        PastelModelHelper.ITEM.simple(generators, PastelItems.WYRMSCALE_JELLY);

        // Cookbooks
        PastelModelHelper.ITEM.simple(generators, PastelItems.MELOCHITES_COOKBOOK_VOL_1);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MELOCHITES_COOKBOOK_VOL_2);
        PastelModelHelper.ITEM.simple(generators, PastelItems.IMBRIFER_COOKBOOK);
        PastelModelHelper.ITEM.simple(generators, PastelItems.IMPERIAL_COOKBOOK);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BREWERS_HANDBOOK);
        //public static final Item VARIA_COOKBOOK = register(simple(item("varia_cookbook", () -> new CookbookItem(IS.of()
        // .maxCount(1).rarity(Rarity.UNCOMMON), GuidebookItem.addressOf(GuidebookItem.CUISINE_CATEGORY_ID, PastelCommon
        // .locate("cuisine/cookbooks/varia_cookbook"))), InkColors.PURPLE)));
        PastelModelHelper.ITEM.simple(generators, PastelItems.POISONERS_HANDBOOK);

        PastelModelHelper.ITEM.simple(generators, PastelItems.AQUA_REGIA);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BAGNUN);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BANYASH);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BERLINER);
        PastelModelHelper.ITEM.simple(generators, PastelItems.BRISTLE_MEAD);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CHAUVE_SOURIS_AU_VIN);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CRAWFISH);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CRAWFISH_COCKTAIL);
        PastelModelHelper.ITEM.simple(generators, PastelItems.CREAM_PASTRY);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FADED_KOI);
        PastelModelHelper.ITEM.simple(generators, PastelItems.FISHCAKE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.LIZARD_MEAT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.COOKED_LIZARD_MEAT);
        PastelModelHelper.ITEM.simple(generators, PastelItems.GOLDEN_BRISTLE_TEA);
        PastelModelHelper.ITEM.simple(generators, PastelItems.HARE_ROAST);
        PastelModelHelper.ITEM.simple(generators, PastelItems.JUNKET);
        PastelModelHelper.ITEM.simple(generators, PastelItems.KOI);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MEATLOAF);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MEATLOAF_SANDWICH);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MELLOW_SHALLOT_SOUP);
        PastelModelHelper.ITEM.simple(generators, PastelItems.MORCHELLA);
        PastelModelHelper.ITEM.simple(generators, PastelItems.NECTERED_VIOGNIER);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PEACHES_FLAMBE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PEACH_CREAM);
        PastelModelHelper.ITEM.simple(generators, PastelItems.PEACH_JAM);
        PastelModelHelper.ITEM.simple(generators, PastelItems.RABBIT_CREAM_PIE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SEDATIVES);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SLUSHSLIDE);
        PastelModelHelper.ITEM.simple(generators, PastelItems.SURSTROMMING);
        PastelModelHelper.ITEM.simple(generators, PastelItems.EVERNECTAR);
    }
}

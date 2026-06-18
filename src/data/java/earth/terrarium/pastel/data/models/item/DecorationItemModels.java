package earth.terrarium.pastel.data.models.item;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.models.ItemModelGenerators;

public class DecorationItemModels {
    public static void generateItemModels(ItemModelGenerators generators) {
        // don't ask why these are in this tab
        PastelModelHelper.ITEM.simple(generators, PastelItems.PYRITE_CHUNK);

        PastelModelHelper.ITEM.banner(generators, PastelItems.COLOR_THEORY_BANNER_PATTERN);
        PastelModelHelper.ITEM.banner(generators, PastelItems.AMETHYST_SHARD_BANNER_PATTERN);
        PastelModelHelper.ITEM.banner(generators, PastelItems.AMETHYST_CLUSTER_BANNER_PATTERN);
        PastelModelHelper.ITEM.banner(generators, PastelItems.ASTROLOGER_BANNER_PATTERN);
        PastelModelHelper.ITEM.banner(generators, PastelItems.VELVET_ASTROLOGER_BANNER_PATTERN);
        PastelModelHelper.ITEM.banner(generators, PastelItems.POISONBLOOM_BANNER_PATTERN);
        PastelModelHelper.ITEM.banner(generators, PastelItems.DEEP_LIGHT_BANNER_PATTERN);

        PastelModelHelper.ITEM.simple(generators, PastelItems.PHANTOM_FRAME);
        PastelModelHelper.ITEM.simple(generators, PastelItems.GLOW_PHANTOM_FRAME);
    }
}

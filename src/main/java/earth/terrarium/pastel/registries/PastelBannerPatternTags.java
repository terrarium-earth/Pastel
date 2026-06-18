package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class PastelBannerPatternTags {

    public static final TagKey<BannerPattern> COLOR_THEORY_TAG = of("pattern_item/color_theory");

    public static final TagKey<BannerPattern> AMETHYST_CLUSTER_TAG = of("pattern_item/amethyst_cluster");

    public static final TagKey<BannerPattern> AMETHYST_SHARD_TAG = of("pattern_item/amethyst_shard");

    public static final TagKey<BannerPattern> ASTROLOGER_TAG = of("pattern_item/astrologer");

    public static final TagKey<BannerPattern> VELVET_ASTROLOGER_TAG = of("pattern_item/velvet_astrologer");

    public static final TagKey<BannerPattern> POISONBLOOM_TAG = of("pattern_item/poisonbloom");

    public static final TagKey<BannerPattern> DEEP_LIGHT_TAG = of("pattern_item/deep_light");

    private static TagKey<BannerPattern> of(String id) {
        return TagKey.create(Registries.BANNER_PATTERN, PastelCommon.locate(id));
    }

}

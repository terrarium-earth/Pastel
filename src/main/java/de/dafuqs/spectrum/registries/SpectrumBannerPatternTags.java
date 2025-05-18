package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class SpectrumBannerPatternTags {

	public static final TagKey<BannerPattern> SPECTRUM_LOGO_TAG = of("pattern_item/logo");
	public static final TagKey<BannerPattern> AMETHYST_CLUSTER_TAG = of("pattern_item/amethyst_cluster");
	public static final TagKey<BannerPattern> AMETHYST_SHARD_TAG = of("pattern_item/amethyst_shard");
	public static final TagKey<BannerPattern> ASTROLOGER_TAG = of("pattern_item/astrologer");
	public static final TagKey<BannerPattern> VELVET_ASTROLOGER_TAG = of("pattern_item/velvet_astrologer");
	public static final TagKey<BannerPattern> POISONBLOOM_TAG = of("pattern_item/poisonbloom");
	public static final TagKey<BannerPattern> DEEP_LIGHT_TAG = of("pattern_item/deep_light");
	
	private static TagKey<BannerPattern> of(String id) {
		return TagKey.create(Registries.BANNER_PATTERN, SpectrumCommon.locate(id));
	}
	
}

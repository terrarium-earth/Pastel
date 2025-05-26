package de.dafuqs.spectrum.api.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public interface LoomPatternProvider {
	
	Component PATTERN_AVAILABLE_TOOLTIP_TEXT = Component.translatable("item.pastel.tooltip.loom_pattern_available").withStyle(ChatFormatting.GRAY);

	ResourceKey<BannerPattern> getPattern();

	static ImmutableList<Holder<BannerPattern>> getPatterns(HolderGetter<BannerPattern> lookup, LoomPatternProvider provider) {
		return lookup.get(provider.getPattern()).map(pattern -> ImmutableList.of((Holder<BannerPattern>)pattern)).orElse(ImmutableList.of());
	}

	default void addBannerPatternProviderTooltip(List<Component> tooltips) {
		tooltips.add(PATTERN_AVAILABLE_TOOLTIP_TEXT);
	}

}

package de.dafuqs.spectrum.items;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.item.LoomPatternProvider;
import de.dafuqs.spectrum.items.conditional.CloakedItem;
import de.dafuqs.spectrum.registries.SpectrumBannerPatterns;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public class PigmentItem extends CloakedItem implements LoomPatternProvider {
	
	private static final Object2ObjectArrayMap<InkColor, PigmentItem> PIGMENTS = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public PigmentItem(Properties settings, InkColor color, Item cloakItem) {
		super(settings, SpectrumCommon.locate("craft_colored_sapling"), cloakItem);
		this.color = color;
		PIGMENTS.put(color, this);
	}
	
	public InkColor getInkColor() {
		return this.color;
	}
	
	public static PigmentItem byColor(InkColor inkColor) {
		return PIGMENTS.get(inkColor);
	}
	
	@Override
	public ResourceKey<BannerPattern> getPattern() {
		return SpectrumBannerPatterns.PIGMENT;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		addBannerPatternProviderTooltip(tooltip);
	}
	
}

package earth.terrarium.pastel.items;

import earth.terrarium.pastel.api.item.LoomPatternProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public class ItemWithLoomPattern extends Item implements LoomPatternProvider {
	
	private final ResourceKey<BannerPattern> patternItemTag;
	
	public ItemWithLoomPattern(Properties settings, ResourceKey<BannerPattern> patternItemTag) {
		super(settings);
		this.patternItemTag = patternItemTag;
	}
	
	@Override
	public ResourceKey<BannerPattern> getPattern() {
		return patternItemTag;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		addBannerPatternProviderTooltip(tooltip);
	}
	
}

package earth.terrarium.pastel.items.conditional;

import earth.terrarium.pastel.api.item.LoomPatternProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public class CloakedItemWithLoomPattern extends CloakedItem implements LoomPatternProvider {
	
	private final ResourceKey<BannerPattern> patternItemTag;
	
	public CloakedItemWithLoomPattern(Properties settings, ResourceLocation cloakAdvancementIdentifier, Item cloakItem, ResourceKey<BannerPattern> patternItemTag) {
		super(settings, cloakAdvancementIdentifier, cloakItem);
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

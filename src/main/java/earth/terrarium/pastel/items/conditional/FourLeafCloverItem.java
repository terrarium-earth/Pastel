package earth.terrarium.pastel.items.conditional;

import earth.terrarium.pastel.api.item.LoomPatternProvider;
import earth.terrarium.pastel.registries.PastelBannerPatterns;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.List;

public class FourLeafCloverItem extends BlockItem implements LoomPatternProvider {

    public FourLeafCloverItem(Block block, Item.Properties settings) {
        super(block, settings);
    }

    @Override
    public ResourceKey<BannerPattern> getPattern() {
        return PastelBannerPatterns.FOUR_LEAF_CLOVER;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        addBannerPatternProviderTooltip(tooltip);
    }

}

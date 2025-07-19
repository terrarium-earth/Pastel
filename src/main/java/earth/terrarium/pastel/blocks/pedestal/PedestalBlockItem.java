package earth.terrarium.pastel.blocks.pedestal;

import earth.terrarium.pastel.api.block.PedestalVariant;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class PedestalBlockItem extends BlockItem {
	
	private final PedestalVariant pedestalVariant;
	private final Component tooltipText;
	
	public PedestalBlockItem(Block block, Properties settings, PedestalVariant pedestalVariant, String tooltipTextString) {
		super(block, settings);
		this.pedestalVariant = pedestalVariant;
		this.tooltipText = Component.translatable(tooltipTextString);
	}
	
	public PedestalVariant getVariant() {
		return this.pedestalVariant;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(tooltipText);
	}
	
}

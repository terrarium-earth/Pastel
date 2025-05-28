package earth.terrarium.pastel.items;

import earth.terrarium.pastel.registries.SpectrumBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;

import java.util.List;

public class DecayPlacerItem extends ItemNameBlockItem {
	
	protected final List<Component> tooltips;
	
	public DecayPlacerItem(Block block, Properties settings, List<Component> tooltips) {
		super(block, settings);
		this.tooltips = tooltips;
	}
	
	@Override
    public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		InteractionResult actionResult = super.useOn(context);
		if (actionResult.consumesAction()) {
			BlockPlaceContext itemPlacementContext = this.updatePlacementContext(new BlockPlaceContext(context));
			if (itemPlacementContext != null) {
				BlockPos blockPos = itemPlacementContext.getClickedPos();

				BlockState placedBlockState = context.getLevel().getBlockState(blockPos);
				if (placedBlockState.is(SpectrumBlockTags.DECAY)) {
					context.getLevel().scheduleTick(blockPos, placedBlockState.getBlock(), 40 + world.random.nextInt(200), TickPriority.EXTREMELY_LOW);
				}
			}
		}
		if (!world.isClientSide && actionResult.consumesAction() && context.getPlayer() != null && !context.getPlayer().isCreative()) {
			context.getPlayer().getInventory().placeItemBackInInventory(Items.GLASS_BOTTLE.getDefaultInstance());
		}
		return actionResult;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.addAll(tooltips);
	}
	
}

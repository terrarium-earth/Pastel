package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.registries.SpectrumBlockTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;

import java.util.List;

public class MultiToolItem extends DiggerItem {
	
	public MultiToolItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
		super(material, SpectrumBlockTags.MULTITOOL_MINEABLE, settings.attributes(DiggerItem.createAttributes(material, attackDamage, attackSpeed)));
	}
	
	/**
	 * Invoke shovel, axe and hoe right click actions (in this order)
	 * Like stripping logs, tilling grass paths etc.
	 * To get farmland it has to be converted to path and then tilled again
	 */
	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult actionResult = InteractionResult.PASS;
		
		if (canTill(context.getItemInHand())) {
			actionResult = Items.IRON_SHOVEL.useOn(context);
			if (!actionResult.consumesAction()) {
				actionResult = Items.IRON_AXE.useOn(context);
				if (!actionResult.consumesAction()) {
					actionResult = Items.IRON_HOE.useOn(context);
				}
			}
		}
		
		if (actionResult.consumesAction()) {
			return actionResult;
		} else {
			return super.useOn(context);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		
		if (canTill(stack)) {
			tooltip.add(Component.translatable("item.pastel.workstaff.tooltip.right_click_actions").withStyle(ChatFormatting.GRAY));
		} else {
			tooltip.add(Component.translatable("item.pastel.workstaff.tooltip.right_click_actions_disabled").withStyle(ChatFormatting.DARK_RED));
		}
	}
	
	public boolean canTill(ItemStack stack) {
		return true;
	}
	
}

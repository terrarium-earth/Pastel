package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.registries.PastelBlockTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;

public class MultiToolItem extends DiggerItem {
	
	public MultiToolItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
		super(material, PastelBlockTags.MULTITOOL_MINEABLE, settings.attributes(DiggerItem.createAttributes(material, attackDamage, attackSpeed)));
	}
	
	/**
	 * Invoke shovel, axe and hoe right click actions (in this order)
	 * Like stripping logs, tilling grass paths etc.
	 * To get farmland it has to be converted to path and then tilled again
	 */
	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult actionResult = InteractionResult.PASS;
		
		if (itemAbilitiesEnabled(context.getItemInHand())) {
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
		
		if (itemAbilitiesEnabled(stack)) {
			tooltip.add(Component.translatable("item.pastel.workstaff.tooltip.right_click_actions").withStyle(ChatFormatting.GRAY));
		} else {
			tooltip.add(Component.translatable("item.pastel.workstaff.tooltip.right_click_actions_disabled").withStyle(ChatFormatting.DARK_RED));
		}
	}
	
	public boolean itemAbilitiesEnabled(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
		return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility) ||  ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility) ||  ItemAbilities.DEFAULT_HOE_ACTIONS.contains(itemAbility);
	}
}

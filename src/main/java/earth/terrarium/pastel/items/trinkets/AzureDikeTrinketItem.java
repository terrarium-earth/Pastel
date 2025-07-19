package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.api.item.AzureDikeItem;
import top.theillusivec4.curios.api.SlotContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class AzureDikeTrinketItem extends PastelTrinketItem implements AzureDikeItem {
	
	public AzureDikeTrinketItem(Properties settings, ResourceLocation unlockIdentifier) {
		super(settings, unlockIdentifier);
	}

	@Override
	public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
		super.onEquip(slotContext, prevStack, stack);
		recalculate(slotContext.entity());
	}

	@Override
	public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
		super.onUnequip(slotContext, newStack, stack);
		recalculate(slotContext.entity());
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.azure_dike_provider.tooltip", maxAzureDike(stack)));
	}
	
}

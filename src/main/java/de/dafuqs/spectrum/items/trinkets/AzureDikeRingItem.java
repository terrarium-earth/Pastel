package de.dafuqs.spectrum.items.trinkets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AzureDikeRingItem extends AzureDikeTrinketItem {
	
	public AzureDikeRingItem(Properties settings, ResourceLocation unlockIdentifier) {
		super(settings, unlockIdentifier);
	}
	
	@Override
	public boolean canEquipMoreThanOne() {
		return true;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.spectrum.azure_dike_ring.tooltip"));
	}
	
	@Override
	public int maxAzureDike(ItemStack stack) {
		return 6;
	}
	
	@Override
	public float azureDikeRechargeSpeedModifier(ItemStack stack) {
		return 2.0F;
	}
	
}

package de.dafuqs.spectrum.items.bundles;

import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.screen.slot.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class PlaceableBundleBlockItem extends BlockItem {
	
	private final ExtendedBundleComponent component;
	
	public PlaceableBundleBlockItem(ExtendedBundleComponent component, Block block, Settings settings) {
		super(block, settings);
		this.component = component;
	}
	
	@Override
	public ItemStack getDefaultStack() {
		ItemStack stack = super.getDefaultStack();
		stack.set(SpectrumDataComponentTypes.EXTENDED_BUNDLE, component);
		return stack;
	}
	
	@Override
	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.onStackClicked(stack, slot, clickType, player);
	}
	
	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}
	
	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.isItemBarVisible(stack);
	}
	
	@Override
	public int getItemBarStep(ItemStack stack) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.getItemBarStep(stack);
	}
	
	@Override
	public int getItemBarColor(ItemStack stack) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.getItemBarColor(stack);
	}
	
	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		return SpectrumItems.EXTENDED_BUNDLE_ITEM.getTooltipData(stack);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		SpectrumItems.EXTENDED_BUNDLE_ITEM.appendTooltip(stack, context, tooltip, type);
	}
	
	@Override
	public void onItemEntityDestroyed(ItemEntity entity) {
		SpectrumItems.EXTENDED_BUNDLE_ITEM.onItemEntityDestroyed(entity);
		super.onItemEntityDestroyed(entity);
	}
	
}

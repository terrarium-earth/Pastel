package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.items.bundles.ExtendedBundleItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BundleItem.class)
public class BundleItemMixin {
	
	@ModifyVariable(method = "overrideStackedOnOther", at = @At(value = "STORE"))
	private BundleContents.Mutable spectrum$onStackClicked$replaceBuilder(BundleContents.Mutable builder, ItemStack stack) {
		return getBuilder(stack);
	}
	
	@ModifyVariable(method = "overrideOtherStackedOnMe", at = @At(value = "STORE"))
	private BundleContents.Mutable spectrum$onClicked$replaceBuilder(BundleContents.Mutable builder, ItemStack stack) {
		return getBuilder(stack);
	}
	
	@Unique
	private static BundleContents.Mutable getBuilder(ItemStack stack) {
		var component = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
		if (stack.getItem() instanceof ExtendedBundleItem)
			return new ExtendedBundleItem.ComponentBuilder(component, ExtendedBundleItem.getMaxOccupancy(stack), ExtendedBundleItem.getMaxStacks(stack));
		return new BundleContents.Mutable(component);
	}
}

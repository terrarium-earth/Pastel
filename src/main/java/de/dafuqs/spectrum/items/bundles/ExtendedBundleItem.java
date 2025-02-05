package de.dafuqs.spectrum.items.bundles;

import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.mixin.accessors.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.apache.commons.lang3.math.*;

import java.util.*;

public class ExtendedBundleItem extends BundleItem {
	
	// TODO: Currently, this isn't displayed properly by the tooltip component. If we make one, we can probably replace PresentTooltipComponent and BottomlessBundleTooltipComponent with it
	
	public ExtendedBundleItem(Settings settings) {
		super(settings.component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT));
	}
	
	@Override
	public int getItemBarStep(ItemStack stack) {
		// If we're not considering max stacks, report the fullness by occupancy. Otherwise, by stacks.
		if (ignoreStacks(stack))
			return super.getItemBarStep(stack);
		return Math.min(1 + MathHelper.multiplyFraction(Fraction.getFraction(getStacks(stack).size(), getMaxStacks(stack)), 12), 13);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		
		// Tick stacks inside the bundle. Technically slot is incorrect, so it might break
		for (var bundled : getStacks(stack)) {
			bundled.inventoryTick(world, entity, slot, selected);
		}
	}
	
	public static List<ItemStack> getStacks(ItemStack stack) {
		return stack.getOrDefault(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT).stream().toList();
	}
	
	public static Fraction getMaxOccupancy(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.EXTENDED_BUNDLE, ExtendedBundleComponent.DEFAULT).maxOccupancy();
	}
	
	public static int getMaxStacks(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.EXTENDED_BUNDLE, ExtendedBundleComponent.DEFAULT).maxStacks();
	}
	
	public static boolean ignoreStacks(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.EXTENDED_BUNDLE, ExtendedBundleComponent.DEFAULT).ignoreStacks();
	}
	
	public static class ComponentBuilder extends BundleContentsComponent.Builder {
		
		private final Fraction maxOccupancy;
		private final int maxStacks;
		
		public ComponentBuilder(BundleContentsComponent base, Fraction maxOccupancy, int maxStacks) {
			super(base);
			this.maxOccupancy = maxOccupancy;
			this.maxStacks = maxStacks;
		}
		
		@Override
		protected int addInternal(ItemStack stack) {
			if (stack.isStackable()) {
				var stacks = ((BundleContentsComponentBuilderAccessor) this).getStacks();
				for (int i = 0; i < stacks.size(); ++i) {
					var slotStack = stacks.get(i);
					if (ItemStack.areItemsAndComponentsEqual(slotStack, stack) && slotStack.getCount() < slotStack.getMaxCount()) {
						return i;
					}
				}
			}
			return -1;
		}
		
		@Override
		public int add(ItemStack stack) {
			var total = 0;
			int added;
			while ((added = super.add(stack)) > 0)
				total += added;
			return total;
		}
		
		@Override
		protected int getMaxAllowed(ItemStack stack) {
			var remainingOccupancy = maxOccupancy.subtract(getOccupancy());
			var itemOccupancy = BundleContentsComponentAccessor.invokeGetOccupancy(stack);
			var allowedByOccupancy = Math.max(remainingOccupancy.divideBy(itemOccupancy).intValue(), 0);
			
			var stacks = ((BundleContentsComponentBuilderAccessor) this).getStacks();
			var allowedByStacks = 0;
			for (int i = 0; i < Math.min(maxStacks, stacks.size()); i++) {
				var slotStack = stacks.get(i);
				if (slotStack.isEmpty())
					allowedByStacks += stack.getMaxCount();
				if (ItemStack.areItemsAndComponentsEqual(slotStack, stack))
					allowedByStacks += slotStack.getMaxCount() - stack.getCount();
			}
			
			return Math.min(allowedByOccupancy, allowedByStacks);
		}
		
	}
	
}

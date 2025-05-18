package de.dafuqs.fractal.api;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import net.minecraft.world.level.ItemLike;

import java.util.Collection;
import java.util.Set;

public class DefaultStackEntryCollector implements CreativeModeTab.Output {
	
	public final Collection<ItemStack> parentTabStacks = ItemStackLinkedSet.createTypeAndComponentsSet();
	public final Set<ItemStack> searchTabStacks = ItemStackLinkedSet.createTypeAndComponentsSet();
	private final CreativeModeTab group;
	private final FeatureFlagSet enabledFeatures;
	
	public DefaultStackEntryCollector(CreativeModeTab group, FeatureFlagSet enabledFeatures) {
		this.group = group;
		this.enabledFeatures = enabledFeatures;
	}
	
	@Override
	public void accept(ItemLike item, CreativeModeTab.TabVisibility visibility) {
		this.accept(item.asItem().getDefaultInstance(), visibility);
	}
	
	@Override
	public void accept(ItemStack stack, CreativeModeTab.TabVisibility visibility) {
		if (stack.getCount() != 1) {
			throw new IllegalArgumentException("Stack size must be exactly 1");
		} else {
			if (this.parentTabStacks.contains(stack) && visibility != CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY) {
				throw new IllegalStateException("Accidentally adding the same item stack twice " + stack.getDisplayName().getString() + " to a Creative Mode Tab: " + this.group.getDisplayName().getString());
			} else {
				if (stack.getItem().isEnabled(this.enabledFeatures)) {
					switch (visibility) {
						case PARENT_AND_SEARCH_TABS -> {
							this.parentTabStacks.add(stack);
							this.searchTabStacks.add(stack);
						}
						case PARENT_TAB_ONLY -> this.parentTabStacks.add(stack);
						case SEARCH_TAB_ONLY -> this.searchTabStacks.add(stack);
					}
				}
			}
		}
	}
	
}
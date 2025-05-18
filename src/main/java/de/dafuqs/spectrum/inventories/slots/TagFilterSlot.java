package de.dafuqs.spectrum.inventories.slots;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TagFilterSlot extends Slot {
	
	private final TagKey<Item> acceptedTag;
	
	public TagFilterSlot(Container inventory, int index, int x, int y, TagKey<Item> acceptedTag) {
		super(inventory, index, x, y);
		this.acceptedTag = acceptedTag;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return stack.is(acceptedTag);
	}
	
}

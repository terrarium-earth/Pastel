package de.dafuqs.fractal.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.*;

public final class ModifyItemSubGroupEntriesEvent extends Event {

	private final ItemSubGroup group;
	private final CreativeModeTab.Output entries;
	private final ResourceLocation id;

	public ModifyItemSubGroupEntriesEvent(ItemSubGroup group, CreativeModeTab.Output entries, ResourceLocation id) {
		this.group = group;
		this.entries = entries;
		this.id = id;
	}

	public ItemSubGroup getGroup() {
		return group;
	}

	public CreativeModeTab.Output getEntries() {
		return entries;
	}

	public ResourceLocation getId() {
		return id;
	}
}
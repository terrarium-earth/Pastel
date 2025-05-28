package de.dafuqs.fractal.api;

import de.dafuqs.fractal.interfaces.ItemGroupParent;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.*;

import java.util.ArrayList;
import java.util.List;

public class ItemSubGroup extends CreativeModeTab {
	
	public static final List<ItemSubGroup> SUB_GROUPS = new ArrayList<>();
	
	protected final CreativeModeTab parent;
	protected final ResourceLocation identifier;
	protected final ResourceKey<CreativeModeTab> key;
	protected final int indexInParent;
	protected final ItemSubGroupStyle style;
	
	public static final ItemSubGroupStyle DEFAULT_STYLE = new ItemSubGroupStyle.Builder().build();

	protected ItemSubGroup(CreativeModeTab parent, ResourceLocation identifier, Component displayName, DisplayItemsGenerator entryCollector, ItemSubGroupStyle style) {
		super(
				parent.row(),
				parent.column(),
				parent.getType(),
				displayName,
				() -> ItemStack.EMPTY,
				entryCollector,
				parent.getScrollerSprite(),
				parent.hasSearchBar(),
				parent.getSearchBarWidth(),
				parent.getTabsImage(),
				parent.getLabelColor(),
				parent.getSlotColor(),
				parent.tabsBefore,
				parent.tabsAfter
		);
		this.style = style;
		this.identifier = identifier;
		this.key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, identifier);
		this.parent = parent;

		ItemGroupParent groupParent = (ItemGroupParent) parent;

        this.indexInParent = groupParent.fractal$getChildren().size();
		groupParent.fractal$getChildren().add(this);
		if (groupParent.fractal$getSelectedChild() == null) {
			groupParent.fractal$setSelectedChild(this);
		}
	}
	
	public ResourceLocation getIdentifier() {
		return identifier;
	}
	
	/**
	 * 100 % the vanilla code, but the check for registered item groups was removed
	 * (we do not want to register our subgroups, so other mods do not pick them up)
	 */
	@Override
	public void buildContents(ItemDisplayParameters context) {
		ItemDisplayBuilder entries = new ItemDisplayBuilder(this, context.enabledFeatures);
		this.displayItemsGenerator.accept(context, entries);
		this.displayItems = entries.tabContents;
		this.displayItemsSearchTab = entries.searchTabContents;

		EventHooks.onCreativeModeTabBuildContents(this, key, this.displayItemsGenerator, context, entries);
		NeoForge.EVENT_BUS.post(new ModifyItemSubGroupEntriesEvent(this, entries, identifier));
		
		this.parent.displayItemsSearchTab.addAll(this.displayItemsSearchTab);
		this.parent.displayItems.addAll(this.displayItems);
	}
	
	@Override
	public ItemStack getIconItem() {
		return ItemStack.EMPTY;
	}
	
	public CreativeModeTab getParent() {
		return parent;
	}
	
	public int getIndexInParent() {
		return indexInParent;
	}
	
	public ItemSubGroupStyle getStyle() {
		return style;
	}
	
	public static class Builder {
		
		protected CreativeModeTab parent;
		protected final ResourceLocation identifier;
		protected Component displayName;
		protected ItemSubGroupStyle style = DEFAULT_STYLE;
		private DisplayItemsGenerator entryCollector;
		
		public Builder(CreativeModeTab parent, ResourceLocation identifier, Component displayName) {
			this.parent = parent;
			this.identifier = identifier;
			this.displayName = displayName;
		}
		
		public ItemSubGroup.Builder styled(ItemSubGroupStyle style) {
			this.style = style;
			return this;
		}
		
		public ItemSubGroup.Builder entries(DisplayItemsGenerator entryCollector) {
			this.entryCollector = entryCollector;
			return this;
		}
		
		public ItemSubGroup build() {
			ItemSubGroup subGroup = new ItemSubGroup(parent, identifier, displayName, entryCollector, style);
			SUB_GROUPS.add(subGroup);
			return subGroup;
		}
	}
	
}

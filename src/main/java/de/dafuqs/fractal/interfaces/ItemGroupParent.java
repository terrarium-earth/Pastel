package de.dafuqs.fractal.interfaces;

import de.dafuqs.fractal.api.ItemSubGroup;

import java.util.List;

public interface ItemGroupParent {

	default List<ItemSubGroup> fractal$getChildren() {
		return List.of();
	}

	default ItemSubGroup fractal$getSelectedChild() {
		return null;
	}

	default void fractal$setSelectedChild(ItemSubGroup group) {}
	
}

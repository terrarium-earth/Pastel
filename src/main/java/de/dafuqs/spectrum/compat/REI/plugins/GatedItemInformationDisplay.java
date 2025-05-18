package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.api.recipe.DescriptiveGatedRecipe;
import de.dafuqs.spectrum.compat.REI.GatedSpectrumDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;

public abstract class GatedItemInformationDisplay extends GatedSpectrumDisplay {
	
	protected final Item item;
	protected final Component description;
	
	public GatedItemInformationDisplay(RecipeHolder<? extends DescriptiveGatedRecipe<?>> recipe) {
		super(recipe, Collections.singletonList(EntryIngredients.of(recipe.value().getItem())), Collections.emptyList());
		this.item = recipe.value().getItem();
		this.description = recipe.value().getDescription();
	}
	
	public Item getItem() {
		return this.item;
	}
	
	public Component getDescription() {
		return this.description;
	}
	
}
package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.compat.REI.GatedSpectrumDisplay;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.recipe.InkConvertingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InkConvertingDisplay extends GatedSpectrumDisplay {
	
	protected final InkColor color;
	protected final long amount;
	
	public InkConvertingDisplay(@NotNull RecipeHolder<InkConvertingRecipe> recipe) {
		super(recipe, EntryIngredients.ofIngredients(recipe.value().getIngredients()), List.of());
		this.color = recipe.value().getInkColor();
		this.amount = recipe.value().getInkAmount();
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.INK_CONVERTING;
	}
	
	@Override
    public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return AdvancementHelper.hasAdvancement(client.player, InkConvertingRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
	}
	
}

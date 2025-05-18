package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.api.item.GemstoneColor;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.compat.REI.GatedSpectrumDisplay;
import de.dafuqs.spectrum.compat.REI.REIHelper;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.recipe.pedestal.BuiltinGemstoneColor;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipe;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;
import java.util.List;

public class PedestalCraftingDisplay extends GatedSpectrumDisplay {
	
	protected final PedestalRecipeTier pedestalRecipeTier;
	protected final int width;
	protected final int height;
	protected final float experience;
	protected final int craftingTime;
	public boolean shapeless;

	/**
	 * When using the REI recipe functionality
	 *
	 * @param recipe The recipe
	 */
	public PedestalCraftingDisplay(RecipeHolder<PedestalRecipe> recipe) {
		super(recipe, mapIngredients(recipe.value()), Collections.singletonList(EntryIngredients.of(recipe.value().getResultItem(BasicDisplay.registryAccess()))));
		this.pedestalRecipeTier = recipe.value().getTier();
		this.width = recipe.value().getWidth();
		this.height = recipe.value().getHeight();
		this.experience = recipe.value().getExperience();
		this.craftingTime = recipe.value().getCraftingTime();
		this.shapeless = recipe.value().isShapeless();
	}
	
	private static List<EntryIngredient> mapIngredients(PedestalRecipe recipe) {
		int powderSlotCount = recipe.getTier().getPowderSlotCount();
		List<IngredientStack> ingredients = recipe.getIngredientStacks();
		int ingredientCount = ingredients.size();
		
		List<EntryIngredient> list = NonNullList.withSize(9 + powderSlotCount, EntryIngredient.empty());
		
		for (int i = 0; i < ingredientCount; i++) {
			list.set(recipe.getGridSlotId(i), REIHelper.ofIngredientStack(recipe.getIngredientStacks().get(i)));
		}
		for (int i = 0; i < powderSlotCount; i++) {
			GemstoneColor color = BuiltinGemstoneColor.values()[i];
			int powderAmount = recipe.getPowderInputs().getOrDefault(color, 0);
			if (powderAmount > 0) {
				list.set(9 + i, EntryIngredients.of(color.getGemstonePowderItem(), powderAmount));
			}
		}
		return list;
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.PEDESTAL_CRAFTING;
	}
	
	@Override
    public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return this.pedestalRecipeTier.hasUnlocked(client.player) && super.isUnlocked();
	}
	
	public PedestalRecipeTier getTier() {
		return this.pedestalRecipeTier;
	}
	
}

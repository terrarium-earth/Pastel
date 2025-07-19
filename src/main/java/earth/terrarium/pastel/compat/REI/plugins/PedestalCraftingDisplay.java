package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.REIHelper;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.pedestal.BuiltinGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipeTier;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;
import java.util.List;

public class PedestalCraftingDisplay extends PastelDisplay {
	
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
		return PastelPlugins.PEDESTAL_CRAFTING;
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

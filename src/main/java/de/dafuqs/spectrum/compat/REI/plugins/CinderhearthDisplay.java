package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.compat.REI.GatedSpectrumDisplay;
import de.dafuqs.spectrum.compat.REI.REIHelper;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CinderhearthDisplay extends GatedSpectrumDisplay {
	
	protected final float experience;
	protected final int craftingTime;
	protected final List<Tuple<ItemStack, Float>> outputsWithChance;
	
	public CinderhearthDisplay(@NotNull RecipeHolder<CinderhearthRecipe> recipe) {
		super(recipe, REIHelper.toEntryIngredients(recipe.value().getIngredientStacks()), List.of(EntryIngredients.ofItemStacks(recipe.value().getPossibleOutputs())));
		this.outputsWithChance = recipe.value().getResultsWithChance();
		this.experience = recipe.value().getExperience();
		this.craftingTime = recipe.value().getCraftingTime();
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.CINDERHEARTH;
	}
	
	@Override
	public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return AdvancementHelper.hasAdvancement(client.player, CinderhearthRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
	}
	
}

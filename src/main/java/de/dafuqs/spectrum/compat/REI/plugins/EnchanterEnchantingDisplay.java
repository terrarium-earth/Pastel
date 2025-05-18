package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.items.magic_items.KnowledgeGemItem;
import de.dafuqs.spectrum.recipe.enchanter.EnchanterRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnchanterEnchantingDisplay extends EnchanterDisplay {
	
	protected final int requiredExperience;
	protected final int craftingTime;
	
	// first input is the center, all others around clockwise
	public EnchanterEnchantingDisplay(@NotNull RecipeHolder<EnchanterRecipe> recipe) {
		super(recipe, buildIngredients(recipe.value()), Collections.singletonList(EntryIngredients.of(recipe.value().getResultItem(BasicDisplay.registryAccess()))));
		this.requiredExperience = recipe.value().getRequiredExperience();
		this.craftingTime = recipe.value().getCraftingTime();
	}
	
	private static List<EntryIngredient> buildIngredients(EnchanterRecipe recipe) {
		List<EntryIngredient> inputs = recipe.getIngredients().stream().map(EntryIngredients::ofIngredient).collect(Collectors.toCollection(ArrayList::new));
		inputs.add(EntryIngredients.of(KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getRequiredExperience(), true)));
		return inputs;
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.ENCHANTER_CRAFTING;
	}
	
	@Override
    public boolean isUnlocked() {
		Minecraft client = Minecraft.getInstance();
		return AdvancementHelper.hasAdvancement(client.player, EnchanterRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
	}
	
}

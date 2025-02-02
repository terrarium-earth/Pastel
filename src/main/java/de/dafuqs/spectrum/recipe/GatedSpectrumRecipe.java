package de.dafuqs.spectrum.recipe;

import de.dafuqs.spectrum.api.recipe.*;
import net.minecraft.item.*;
import net.minecraft.recipe.input.*;
import net.minecraft.util.*;

import java.util.*;

public abstract class GatedSpectrumRecipe<C extends RecipeInput> implements GatedRecipe<C> {
	
	public final String group;
	public final boolean secret;
	public final Optional<Identifier> requiredAdvancementIdentifier;
	
	protected GatedSpectrumRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier) {
		this.group = group;
		this.secret = secret;
		this.requiredAdvancementIdentifier = requiredAdvancementIdentifier;
	}
	
	@Override
	public String getGroup() {
		return this.group;
	}
	
	@Override
	public boolean isSecret() {
		return this.secret;
	}
	
	/**
	 * The advancement the player has to have for the recipe be craftable
	 *
	 * @return The advancement identifier. A null value means the player is always able to craft this recipe
	 */
	@Override
	public Optional<Identifier> getRequiredAdvancementIdentifier() {
		return this.requiredAdvancementIdentifier;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return null;
	}
	
	@Override
	public boolean isIgnoredInRecipeBook() {
		return true;
	}
	
	protected static ItemStack getDefaultStackWithCount(Item item, int count) {
		ItemStack stack = item.getDefaultStack();
		stack.setCount(count);
		return stack;
	}
	
}

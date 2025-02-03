package de.dafuqs.spectrum.api.enchantment;

import net.minecraft.recipe.*;
import net.minecraft.util.*;

import java.util.*;

public interface IRecipeGenerator {
	
	List<? extends Recipe<?>> getAdditionalRecipes();
	
	/**
	 * Transforms the generating recipe's identifier to the generated identifier
	 *
	 * @param original The generating recipe's identifier
	 * @param index    Index 0 is the generating recipe. Indices 1+ are from getAdditionalRecipes()
	 * @return The transformed identifier
	 */
	Identifier transformRecipeId(Identifier original, int index);
	
}

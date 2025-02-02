package de.dafuqs.spectrum.recipe.pedestal.dynamic;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public class StarCandyRecipe extends ShapedPedestalRecipe {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/food/star_candy");
	public static final float ENCHANTED_STAR_CANDY_CHANCE = 0.02F;
	
	public StarCandyRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER), PedestalRecipeTier.BASIC, 3, 3, generateInputs(), Map.of(BuiltinGemstoneColor.YELLOW, 1), SpectrumItems.STAR_CANDY.getDefaultStack(), 1.0F, 20, false, false);
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		//TODO is there any way to get rand from the world?
		if (new Random().nextFloat() < ENCHANTED_STAR_CANDY_CHANCE)
			return SpectrumItems.ENCHANTED_STAR_CANDY.getDefaultStack();
		return this.output.copy();
	}
	
	private static List<IngredientStack> generateInputs() {
		return List.of(
				IngredientStack.ofItems(1, Items.SUGAR),
				IngredientStack.ofItems(1, Items.SUGAR),
				IngredientStack.ofItems(1, Items.SUGAR),
				IngredientStack.ofItems(1, SpectrumItems.STARDUST),
				IngredientStack.ofItems(1, SpectrumItems.STARDUST),
				IngredientStack.ofItems(1, SpectrumItems.STARDUST),
				IngredientStack.ofItems(1, SpectrumItems.AMARANTH_GRAINS),
				IngredientStack.ofItems(1, SpectrumItems.AMARANTH_GRAINS),
				IngredientStack.ofItems(1, SpectrumItems.AMARANTH_GRAINS)
		);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.PEDESTAL_STAR_CANDY;
	}
	
	
}

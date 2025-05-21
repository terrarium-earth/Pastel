package de.dafuqs.spectrum.recipe.titration_barrel.dynamic;

import de.dafuqs.spectrum.capabilities.item.*;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.recipe.titration_barrel.FermentationData;
import de.dafuqs.spectrum.recipe.titration_barrel.TitrationBarrelRecipe;
import de.dafuqs.spectrum.registries.SpectrumItemTags;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CheongRecipe extends TitrationBarrelRecipe {
	
	public static final Item TAPPING_ITEM = Items.GLASS_BOTTLE;
	public static final int MIN_FERMENTATION_TIME_HOURS = 4;
	public static final ItemStack OUTPUT_STACK = getDefaultStackWithCount(SpectrumItems.CHEONG, 4);
	public static final ItemStack OUTPUT_STACK_MERMAIDS = getDefaultStackWithCount(SpectrumItems.MERMAIDS_JAM, 4);
	
	public static final List<IngredientStack> INGREDIENT_STACKS = new ArrayList<>() {{
		add(IngredientStack.ofTag(SpectrumItemTags.FRUITS, 8));
		add(IngredientStack.ofItems(Items.SUGAR, 16));
	}};
	
	public CheongRecipe() {
		super("", false, Optional.empty(), INGREDIENT_STACKS, FluidIngredient.of(Fluids.WATER),
				OUTPUT_STACK, TAPPING_ITEM, MIN_FERMENTATION_TIME_HOURS, FermentationData.DEFAULT);
	}
	
	@Override
	public ItemStack tap(FriendlyStackHandler inventory, long secondsFermented, float downfall) {
		ItemStack result = inventory.hasAnyOf(Collections.singleton(SpectrumItems.MERMAIDS_GEM))
				? OUTPUT_STACK_MERMAIDS.copy()
				: OUTPUT_STACK.copy();
		result.setCount(1);
		return result;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.TITRATION_BARREL_CHEONG;
	}
	
}

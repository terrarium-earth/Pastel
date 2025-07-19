package earth.terrarium.pastel.compat.REI;

import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class REIHelper {
	
	public static List<EntryIngredient> toEntryIngredients(List<IngredientStack> ingredientStacks) {
		return ingredientStacks.stream().map(REIHelper::ofIngredientStack).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public static EntryIngredient ofIngredientStack(@NotNull IngredientStack ingredientStack) {
		return EntryIngredients.ofItemStacks(ingredientStack.getItems().toList());
	}
	
	public static EntryIngredient ofFluidIngredient(FluidIngredient fluidIngredient) {
		var fluids = Arrays.stream(fluidIngredient.getStacks()).map(stack -> EntryStacks.of(stack.getFluid(), stack.getAmount()));
		return EntryIngredient.of(fluids.toList());
	}
	
}

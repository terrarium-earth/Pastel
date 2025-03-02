package de.dafuqs.spectrum.recipe.fluid_converting.dynamic;

import com.neep.neepmeat.init.*;
import de.dafuqs.spectrum.compat.*;
import de.dafuqs.spectrum.recipe.fluid_converting.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

import java.util.*;

public class MeatToRottenFleshRecipe extends DragonrotConvertingRecipe {
	
	public MeatToRottenFleshRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER), getMeatsIngredient(), Items.ROTTEN_FLESH.getDefaultStack());
	}
	
	private static Ingredient getMeatsIngredient() {
		return Ingredient.ofStacks(Registries.ITEM.getOrCreateEntryList(ItemTags.MEAT)
				.stream()
				.filter(item -> item.value() == Items.ROTTEN_FLESH && !(SpectrumIntegrationPacks.isIntegrationPackActive(SpectrumIntegrationPacks.NEEPMEAT_ID) && item == NMItems.MEAT_SCRAP))
				.map(ItemStack::new));
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.DRAGONROT_MEAT_TO_ROTTEN_FLESH;
	}
	
}

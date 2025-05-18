package de.dafuqs.spectrum.recipe.fluid_converting.dynamic;

import com.neep.neepmeat.init.NMItems;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.recipe.fluid_converting.DragonrotConvertingRecipe;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Optional;

public class MeatToRottenFleshRecipe extends DragonrotConvertingRecipe {
	
	public MeatToRottenFleshRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER), getMeatsIngredient(), Items.ROTTEN_FLESH.getDefaultInstance());
	}
	
	private static Ingredient getMeatsIngredient() {
		return Ingredient.of(BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.MEAT)
				.stream()
				.filter(item -> item.value() == Items.ROTTEN_FLESH && !(SpectrumIntegrationPacks.isIntegrationPackActive(SpectrumIntegrationPacks.NEEPMEAT_ID) && item == NMItems.MEAT_SCRAP))
				.map(ItemStack::new));
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.DRAGONROT_MEAT_TO_ROTTEN_FLESH;
	}
	
}

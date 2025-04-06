package de.dafuqs.spectrum.recipe.pedestal.dynamic;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class StarCandyRecipe extends ShapedPedestalRecipe {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/food/star_candy");
	public static final float PURPLE_STAR_CANDY_CHANCE = 0.01F;
	
	public StarCandyRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER), PedestalRecipeTier.BASIC, new RawShapedPedestalRecipe(3, 3, generateInputs(), Optional.empty()), Map.of(BuiltinGemstoneColor.YELLOW, 1), SpectrumItems.STAR_CANDY.getDefaultStack(), 1.0F, 20, false, false);
	}
	
	@Override
	public ItemStack craft(PedestalRecipeInput input, RegistryWrapper.WrapperLookup wrapperLookup) {
		@Nullable PlayerEntity player = input.getPlayer();
		if (player != null && player.getRandom().nextFloat() < PURPLE_STAR_CANDY_CHANCE + player.getAttributeValue(EntityAttributes.GENERIC_LUCK)) {
			return SpectrumItems.ENCHANTED_STAR_CANDY.getDefaultStack();
		}
		return this.output.copy();
	}
	
	private static DefaultedList<IngredientStack> generateInputs() {
		return DefaultedList.copyOf(IngredientStack.EMPTY,
				IngredientStack.ofItems(Items.SUGAR),
				IngredientStack.ofItems(Items.SUGAR),
				IngredientStack.ofItems(Items.SUGAR),
				IngredientStack.ofItems(SpectrumItems.STARDUST),
				IngredientStack.ofItems(SpectrumItems.STARDUST),
				IngredientStack.ofItems(SpectrumItems.STARDUST),
				IngredientStack.ofItems(SpectrumItems.AMARANTH_GRAINS),
				IngredientStack.ofItems(SpectrumItems.AMARANTH_GRAINS),
				IngredientStack.ofItems(SpectrumItems.AMARANTH_GRAINS)
		);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.PEDESTAL_STAR_CANDY;
	}
	
	
}

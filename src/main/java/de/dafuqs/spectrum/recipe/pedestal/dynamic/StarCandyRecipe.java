package de.dafuqs.spectrum.recipe.pedestal.dynamic;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.blocks.pedestal.PedestalRecipeInput;
import de.dafuqs.spectrum.recipe.pedestal.BuiltinGemstoneColor;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import de.dafuqs.spectrum.recipe.pedestal.RawShapedPedestalRecipe;
import de.dafuqs.spectrum.recipe.pedestal.ShapedPedestalRecipe;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class StarCandyRecipe extends ShapedPedestalRecipe {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/food/star_candy");
	public static final float PURPLE_STAR_CANDY_CHANCE = 0.01F;
	
	public StarCandyRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER), PedestalRecipeTier.BASIC, new RawShapedPedestalRecipe(3, 3, generateInputs(), Optional.empty()), Map.of(BuiltinGemstoneColor.YELLOW, 1), SpectrumItems.STAR_CANDY.getDefaultInstance(), 1.0F, 20, false, false);
	}
	
	@Override
	public ItemStack assemble(PedestalRecipeInput input, HolderLookup.Provider wrapperLookup) {
		@Nullable Player player = input.getPlayer();
		if (player != null && player.getRandom().nextFloat() < PURPLE_STAR_CANDY_CHANCE + player.getAttributeValue(Attributes.LUCK)) {
			return SpectrumItems.ENCHANTED_STAR_CANDY.getDefaultInstance();
		}
		return this.output.copy();
	}
	
	private static NonNullList<IngredientStack> generateInputs() {
		return NonNullList.of(IngredientStack.EMPTY,
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

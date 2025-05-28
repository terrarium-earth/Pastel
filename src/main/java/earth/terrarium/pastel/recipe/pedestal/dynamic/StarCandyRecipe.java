package earth.terrarium.pastel.recipe.pedestal.dynamic;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.pedestal.PedestalRecipeInput;
import earth.terrarium.pastel.recipe.pedestal.BuiltinGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipeTier;
import earth.terrarium.pastel.recipe.pedestal.RawShapedPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapedPedestalRecipe;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumRecipeSerializers;
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
		super("", false, Optional.of(UNLOCK_IDENTIFIER), PedestalRecipeTier.BASIC, new RawShapedPedestalRecipe(3, 3, generateInputs(), Optional.empty()), Map.of(BuiltinGemstoneColor.YELLOW, 1), SpectrumItems.STAR_CANDY.get().getDefaultInstance(), 1.0F, 20, false, false);
	}
	
	@Override
	public ItemStack assemble(PedestalRecipeInput input, HolderLookup.Provider wrapperLookup) {
		@Nullable Player player = input.getPlayer();
		if (player != null && player.getRandom().nextFloat() < PURPLE_STAR_CANDY_CHANCE + player.getAttributeValue(Attributes.LUCK)) {
			return SpectrumItems.ENCHANTED_STAR_CANDY.get().getDefaultInstance();
		}
		return this.output.copy();
	}
	
	private static NonNullList<IngredientStack> generateInputs() {
		return NonNullList.of(IngredientStack.EMPTY,
				IngredientStack.ofItems(Items.SUGAR),
				IngredientStack.ofItems(Items.SUGAR),
				IngredientStack.ofItems(Items.SUGAR),
				IngredientStack.ofItems(SpectrumItems.STARDUST.get()),
				IngredientStack.ofItems(SpectrumItems.STARDUST.get()),
				IngredientStack.ofItems(SpectrumItems.STARDUST.get()),
				IngredientStack.ofItems(SpectrumItems.AMARANTH_GRAINS.get()),
				IngredientStack.ofItems(SpectrumItems.AMARANTH_GRAINS.get()),
				IngredientStack.ofItems(SpectrumItems.AMARANTH_GRAINS.get())
		);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.PEDESTAL_STAR_CANDY;
	}
	
	
}

package earth.terrarium.pastel.recipe.fluid_converting;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MidnightSolutionConvertingRecipe extends FluidConvertingRecipe {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("midgame/create_midnight_aberration");
	private static final Set<Item> outputItems = new HashSet<>();
	
	public MidnightSolutionConvertingRecipe(String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier, @NotNull Ingredient inputIngredient, ItemStack outputItemStack) {
		super(group, secret, requiredAdvancementIdentifier, inputIngredient, outputItemStack);
		outputItems.add(outputItemStack.getItem());
	}
	
	public static boolean isExistingOutputItem(@NotNull ItemStack itemStack) {
		return outputItems.contains(itemStack.getItem());
	}
	
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(PastelItems.MIDNIGHT_SOLUTION_BUCKET.get());
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.MIDNIGHT_SOLUTION_CONVERTING_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return PastelRecipeTypes.MIDNIGHT_SOLUTION_CONVERTING;
	}
	
	@Override
	public ResourceLocation getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "midnight_solution_converting";
	}
	
}

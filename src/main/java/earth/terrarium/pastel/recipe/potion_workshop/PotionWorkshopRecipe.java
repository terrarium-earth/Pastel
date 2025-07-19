package earth.terrarium.pastel.recipe.potion_workshop;


import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.potion_workshop.PotionWorkshopBlockEntity;
import earth.terrarium.pastel.recipe.GatedStackPastelRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class PotionWorkshopRecipe extends GatedStackPastelRecipe<RecipeInput> {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("unlocks/blocks/potion_workshop");
	public static final int[] INGREDIENT_SLOTS = new int[]{2, 3, 4};
	
	protected final int craftingTime;
	protected final int color;
	
	protected final IngredientStack ingredient1;
	protected final IngredientStack ingredient2;
	protected final IngredientStack ingredient3;
	
	public PotionWorkshopRecipe(
			String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier, int craftingTime, int color,
			IngredientStack ingredient1, IngredientStack ingredient2, IngredientStack ingredient3
	) {
		super(group, secret, requiredAdvancementIdentifier);
		this.color = color;
		this.craftingTime = craftingTime;
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.ingredient3 = ingredient3;
	}
	
	public List<IngredientStack> getOtherIngredients() {
		ArrayList<IngredientStack> ingredients = new ArrayList<>();
		ingredients.add(ingredient1);
		if (!ingredient2.isEmpty()) {
			ingredients.add(ingredient2);
			if (!ingredient3.isEmpty()) {
				ingredients.add(ingredient3);
			}
		}
		return ingredients;
	}
	
	protected void addIngredientStacks(NonNullList<IngredientStack> ingredients) {
		ingredients.add(this.ingredient1);
		ingredients.add(this.ingredient2);
		ingredients.add(this.ingredient3);
	}
	
	@Override
	public boolean matches(@NotNull RecipeInput inv, Level world) {
		if (inv.size() > 4 && inv.getItem(0).is(PastelItems.MERMAIDS_GEM.get()) && isValidBaseIngredient(inv.getItem(1))) {
			
			if (usesReagents()) {
				if (!areStacksInReagentSlotsAllReagents(inv)) return false;
			} else {
				if (!areReagentSlotsEmpty(inv)) return false;
			}
			
			// check ingredients
			return matchIngredientStacksExclusively(inv, getOtherIngredients(), INGREDIENT_SLOTS);
		} else {
			return false;
		}
	}
	
	private boolean areStacksInReagentSlotsAllReagents(@NotNull RecipeInput inv) {
		for (int i : PotionWorkshopBlockEntity.REAGENT_SLOTS) {
			ItemStack itemStack = inv.getItem(i);
			if (!itemStack.isEmpty() && !PotionWorkshopReactingRecipe.isReagent(itemStack.getItem())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean areReagentSlotsEmpty(@NotNull RecipeInput inv) {
		for (int i : PotionWorkshopBlockEntity.REAGENT_SLOTS) {
			if (!inv.getItem(i).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public abstract boolean isValidBaseIngredient(ItemStack itemStack);
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getToastSymbol() {
		return PastelBlocks.POTION_WORKSHOP.get().asItem().getDefaultInstance();
	}
	
	public int getCraftingTime() {
		return this.craftingTime;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.ANVIL_CRUSHING_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return PastelRecipeTypes.ANVIL_CRUSHING;
	}
	
	public abstract boolean usesReagents();
	
	public abstract int getMinOutputCount(ItemStack baseItemStack);
	
	public int getColor() {
		return this.color;
	}
	
	@Override
	public @Nullable ResourceLocation getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	public int getRequiredExperience() {
		return 0;
	}
	
}

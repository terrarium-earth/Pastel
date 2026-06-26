package earth.terrarium.pastel.recipe;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GatedSizedPastelRecipe<C extends RecipeInput> extends GatedPastelRecipe<C> {

    protected GatedSizedPastelRecipe(
        String group,
        boolean secret,
        Optional<ResourceLocation> requiredAdvancementIdentifier
    ) {
        super(group, secret, requiredAdvancementIdentifier);
    }

    public abstract List<SizedIngredient> getSizedIngredients();

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.copyOf(getSizedIngredients().stream().map(SizedIngredient::ingredient).collect(Collectors.toList()));
    }

    protected boolean matchIngredientStacksExclusively(
        RecipeInput recipeInput,
        List<SizedIngredient> ingredientStacks
    ) {
        // does the recipe fit into that inventory in the first place?
        if (recipeInput.size() < ingredientStacks.size()) {
            return false;
        }

        // collect all non-empty stacks
        List<ItemStack> nonEmptyStacks = new ArrayList<>();
        for (
            int i = 0;
            i < recipeInput.size();
            i++
        ) {
            ItemStack stack = recipeInput.getItem(i);
            if (!stack.isEmpty()) {
                nonEmptyStacks.add(stack);
            }
        }
        // do we have an exact count match?
        if (nonEmptyStacks.size() != ingredientStacks.size()) {
            return false;
        }

        // match each IngredientStack exclusively
        ObjectArraySet<SizedIngredient> ingredients = ObjectArraySet
            .of(
                ingredientStacks.toArray(new SizedIngredient[0])
            );
        for (
            ItemStack stack : nonEmptyStacks
        ) {
            SizedIngredient foundStack = null;
            for (
                SizedIngredient ingredientStack : ingredients
            ) {
                if (ingredientStack.test(stack)) {
                    foundStack = ingredientStack;
                    break;
                }
            }
            if (foundStack == null) {
                return false;
            }
            ingredients.remove(foundStack);
            if (ingredients.isEmpty()) {
                break;
            }
        }

        return true;
    }

    protected boolean matchIngredientStacksExclusively(
        RecipeInput recipeInput,
        List<SizedIngredient> ingredients,
        int[] slots
    ) {
        int inputStackCount = 0;
        for (
            int slot : slots
        ) {
            if (!recipeInput
                .getItem(slot)
                .isEmpty()) {
                inputStackCount++;
            }
        }
        if (inputStackCount != ingredients.size()) {
            return false;
        }

        for (
            SizedIngredient ingredient : ingredients
        ) {
            boolean found = false;
            for (
                int slot : slots
            ) {
                if (ingredient.test(recipeInput.getItem(slot))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }

}

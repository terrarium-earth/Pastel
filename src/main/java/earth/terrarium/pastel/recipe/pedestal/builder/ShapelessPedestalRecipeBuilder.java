package earth.terrarium.pastel.recipe.pedestal.builder;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.recipe.pedestal.ShapelessPedestalRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ShapelessPedestalRecipeBuilder extends PedestalRecipeBuilder<ShapelessPedestalRecipeBuilder> {
    private final List<IngredientStack> ingredients = new ArrayList<>();


    public ShapelessPedestalRecipeBuilder(ItemStack result) {
        super (result);
    }


    public ShapelessPedestalRecipeBuilder requires(IngredientStack stack) {
        ingredients.add(stack);
        return this;
    }

    public ShapelessPedestalRecipeBuilder requires(ItemLike item) {
        return requires(IngredientStack.ofItems(item.asItem()));
    }

    public ShapelessPedestalRecipeBuilder requires(TagKey<Item> tag) {
        return requires(IngredientStack.ofTag(tag));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Objects.requireNonNull(tier, "tier must be defined before saving recipe!");
        var recipe = new ShapelessPedestalRecipe(
                this.group,
                this.secret,
                Optional.ofNullable(this.requiredAdvancementIdentifier),
                this.tier,
                this.ingredients,
                this.powderInputs,
                this.result,
                this.experience,
                this.craftingTime,
                this.skipRemainders,
                this.disableYieldUpgrades
        );
        recipeOutput.accept(id, recipe, null, this.conditions.toArray(ICondition[]::new));
    }
}

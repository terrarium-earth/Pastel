package earth.terrarium.pastel.data.recipe.builder.pedestal;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.recipe.pedestal.RawShapedPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapedPedestalRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.*;

public final class ShapedPedestalRecipeBuilder extends PedestalRecipeBuilder<ShapedPedestalRecipeBuilder> {
    private final List<String> rows = new ArrayList<>();
    private final Map<Character, Ingredient> keyMap = new HashMap<>();

    public ShapedPedestalRecipeBuilder(ItemStack result) {
        super(result);
    }

    public ShapedPedestalRecipeBuilder pattern(String row) {
        if (!rows.isEmpty() && row.length() != rows.getFirst().length()) {
            throw new IllegalArgumentException("All patterns must be the same length!");
        } else {
            rows.add(row);
        }
        return this;
    }

    public ShapedPedestalRecipeBuilder define(char key, Ingredient ingredient) {
        keyMap.put(key, ingredient);
        return this;
    }

    public ShapedPedestalRecipeBuilder define(char key, ItemLike item) {
        return this.define(key, Ingredient.of(item.asItem()));
    }

    public ShapedPedestalRecipeBuilder define(char key, TagKey<Item> tagKey) {
        return this.define(key, Ingredient.of(tagKey));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Objects.requireNonNull(this.tier, "tier must be defined before saving the recipe!");
        var rawRecipe = ShapedRecipePattern.of(keyMap, rows);
        ShapedPedestalRecipe realRecipe =
                new ShapedPedestalRecipe(
                        this.group,
                        this.secret,
                        Optional.ofNullable(this.requiredAdvancementIdentifier),
                        this.tier,
                        rawRecipe,
                        this.powderInputs,
                        this.result,
                        this.experience,
                        this.craftingTime,
                        this.skipRemainders,
                        this.disableYieldUpgrades
                );


        recipeOutput.accept(id, realRecipe, null, this.conditions.toArray(ICondition[]::new));
    }
}

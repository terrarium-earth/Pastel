package earth.terrarium.pastel.recipe.pedestal.builder;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.recipe.pedestal.RawShapedPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapedPedestalRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public final class ShapedPedestalRecipeBuilder extends PedestalRecipeBuilder<ShapedPedestalRecipeBuilder> {
    private final List<String> rows = new ArrayList<>();
    private final Map<Character, IngredientStack> keyMap = new HashMap<>();

    public ShapedPedestalRecipeBuilder(ItemStack result) {
        super(result);
    }

    @Override
    public ShapedPedestalRecipeBuilder self() {
        return this;
    }

    public ShapedPedestalRecipeBuilder pattern(String row) {
        if (!rows.isEmpty() && row.length() != rows.getFirst().length()) {
            throw new IllegalArgumentException("All patterns must be the same length!");
        } else {
            rows.add(row);
        }
        return this;
    }

    public ShapedPedestalRecipeBuilder key(char key, IngredientStack ingredient) {
        keyMap.put(key, ingredient);
        return this;
    }

    public ShapedPedestalRecipeBuilder key(char key, Item item) {
        return this.key(key, IngredientStack.ofItems(item));
    }

    public ShapedPedestalRecipeBuilder key(char key, TagKey<Item> tagKey) {
        return this.key(key, IngredientStack.ofTag(tagKey));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Objects.requireNonNull(this.tier, "tier must be defined before saving the recipe!");
        RawShapedPedestalRecipe rawRecipe = RawShapedPedestalRecipe.create(keyMap, rows);
        ShapedPedestalRecipe realRecipe =
                new ShapedPedestalRecipe(
                        this.group != null ? this.group : "",
                        this.secret,
                        Optional.ofNullable(this.requiredAdvancementIdentifier),
                        this.tier,
                        rawRecipe,
                        this.powderInputs,
                        this.result,
                        this.experience,
                        this.craftingTime,
                        this.skipRemainders,
                        this.ignoreYieldUpgrades
                );
        recipeOutput.accept(id, realRecipe, null);
    }
}

package earth.terrarium.pastel.data.recipe.builder.cinderhearth;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.recipe.cinderhearth.CinderhearthRecipe;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class CinderhearthRecipeBuilder extends GatedRecipeBuilder<CinderhearthRecipeBuilder> {
    private final SizedIngredient input;
    private float experience = 0.0f;
    private final List<Tuple<ItemStack, Float>> resultsWithChance = new ArrayList<>();
    private final int time;

    private CinderhearthRecipeBuilder(SizedIngredient input, int time) {
        super(ItemStack.EMPTY);
        this.input = input;
        this.time = time;
    }

    public static CinderhearthRecipeBuilder of(SizedIngredient input, int time) {
        return new CinderhearthRecipeBuilder(input, time);
    }

    public static CinderhearthRecipeBuilder of(Ingredient input, int count, int time) {
        return of(new SizedIngredient(input, count), time);
    }

    public static CinderhearthRecipeBuilder of(Ingredient input, int time) {
        return of(new SizedIngredient(input, 1), time);
    }

    @Override
    public Item getResult() {
        if (this.resultsWithChance.isEmpty()) {
            throw new IllegalStateException("A cinderhearth recipe must define at least one result!");
        }
        return resultsWithChance.getFirst().getA().getItem();
    }

    public CinderhearthRecipeBuilder result(ItemStack stack, float chance) {
        resultsWithChance.add(new Tuple<>(stack, chance));
        return this;
    }

    public CinderhearthRecipeBuilder result(ItemStack stack) {
        return result(stack, 1.0f);
    }

    public CinderhearthRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {

        saveHelperGated(
                recipeOutput,
                id,
                daId ->
                        new CinderhearthRecipe(
                                this.group,
                                this.secret,
                                daId,
                                this.input,
                                this.time,
                                this.experience,
                                this.resultsWithChance
                        )
        );
    }
}

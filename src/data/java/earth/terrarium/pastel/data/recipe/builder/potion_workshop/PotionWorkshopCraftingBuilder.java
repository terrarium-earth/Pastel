package earth.terrarium.pastel.data.recipe.builder.potion_workshop;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.data.recipe.builder.SimpleRecipeBuilder;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.Optional;

public final class PotionWorkshopCraftingBuilder extends PotionWorkshopRecipeBuilder<PotionWorkshopCraftingBuilder> {
    private final SizedIngredient baseIngredient;
    private boolean consumeBaseIngredient = true;
    private int requiredExperience = 0;
    private int color = 0;
    private int craftingTime = 200;

    public PotionWorkshopCraftingBuilder(
            SizedIngredient baseIngredient,
            ItemStack result
    ) {
        super(result);
        this.baseIngredient = baseIngredient;
    }

    public PotionWorkshopCraftingBuilder dontConsumeBaseIngredient() {
        this.consumeBaseIngredient = false;
        return this;
    }

    public PotionWorkshopCraftingBuilder requiredExperience(int value) {
        this.requiredExperience = value;
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var recipe =
                new PotionWorkshopCraftingRecipe(
                        this.group,
                        this.secret,
                        this.getRequiredAdvancement(),
                        this.craftingTime,
                        this.color,
                        this.ingredient1,
                        Optional.ofNullable(this.ingredient2),
                        Optional.ofNullable(this.ingredient3),
                        baseIngredient,
                        consumeBaseIngredient,
                        this.requiredExperience,
                        this.result
                );
        saveHelper(recipeOutput, id, recipe);
    }

    public PotionWorkshopCraftingBuilder color(int color) {
        this.color = color;
        return this;
    }

    public PotionWorkshopCraftingBuilder craftingTime(int craftingTime) {
        this.craftingTime = craftingTime;
        return this;
    }
}

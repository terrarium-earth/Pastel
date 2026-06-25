package earth.terrarium.pastel.data.recipe.builder;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public final class SpiritInstillerRecipeBuilder extends GatedRecipeBuilder<SpiritInstillerRecipeBuilder> {
    private final SizedIngredient firstIngredient;
    private final SizedIngredient secondIngredient;
    private final SizedIngredient centerIngredient;
    private int craftingTime = 200;
    private float experience = 1.0f;
    private boolean noBenefitsFromYieldAndEfficiencyUpgrades = false;

    public SpiritInstillerRecipeBuilder(
            SizedIngredient center,
            SizedIngredient first,
            SizedIngredient second,
            ItemStack result) {
        super(result);
        this.firstIngredient = first;
        this.secondIngredient = second;
        this.centerIngredient = center;
    }

    public SpiritInstillerRecipeBuilder craftingTime(int craftingTime) {
        this.craftingTime = craftingTime;
        return this;
    }

    public SpiritInstillerRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }

    public SpiritInstillerRecipeBuilder disableYieldAndBenefitUpgrades() {
        this.noBenefitsFromYieldAndEfficiencyUpgrades = true;
        return this;
    }

    private static IngredientStack stackFromSize(SizedIngredient ing) {
        return new IngredientStack(ing.ingredient(), ing.count());
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var recipe = new SpiritInstillerRecipe(
                this.group,
                this.secret,
                this.getRequiredAdvancement(),
                stackFromSize(this.centerIngredient),
                stackFromSize(this.firstIngredient),
                stackFromSize(this.secondIngredient),
                this.result,
                this.craftingTime,
                this.experience,
                this.noBenefitsFromYieldAndEfficiencyUpgrades
        );
        recipeOutput.accept(id, recipe, null, this.conditions());
    }
}

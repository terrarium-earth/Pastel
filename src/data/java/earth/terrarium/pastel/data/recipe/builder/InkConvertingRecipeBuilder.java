package earth.terrarium.pastel.data.recipe.builder;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.recipe.InkConvertingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.Optional;

public final class InkConvertingRecipeBuilder extends GatedRecipeBuilder<InkConvertingRecipeBuilder> {
    private final InkColor color;
    private final Ingredient input;
    private final long amount;


    public InkConvertingRecipeBuilder(InkColor color, Ingredient input, long amount) {
        super(ItemStack.EMPTY);
        this.color = color;
        this.input = input;
        this.amount = amount;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var recipe = new InkConvertingRecipe(
                this.group,
                this.secret,
                Optional.ofNullable(this.requiredAdvancementIdentifier),
                this.input,
                this.color,
                this.amount
        );
        recipeOutput.accept(id, recipe, null, this.conditions());
    }
}

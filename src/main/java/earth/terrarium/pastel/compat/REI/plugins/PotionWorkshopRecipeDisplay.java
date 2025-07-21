package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.REIHelper;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopRecipe;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;

public abstract class PotionWorkshopRecipeDisplay extends PastelDisplay {

    protected final int craftingTime;

    /**
     * When using the REI recipe functionality
     *
     * @param recipe The recipe
     */
    public PotionWorkshopRecipeDisplay(RecipeHolder<? extends PotionWorkshopRecipe> recipe) {
        super(recipe, REIHelper.toEntryIngredients(recipe.value()
                                                         .getIngredientStacks()), Collections.singletonList(
            EntryIngredients.of(recipe.value()
                                      .getResultItem(BasicDisplay.registryAccess())))
        );
        this.craftingTime = recipe.value()
                                  .getCraftingTime();
    }

}

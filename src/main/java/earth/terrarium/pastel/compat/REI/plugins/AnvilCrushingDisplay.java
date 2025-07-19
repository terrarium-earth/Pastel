package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.anvil_crushing.AnvilCrushingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;

public class AnvilCrushingDisplay extends PastelDisplay {
    public final float experience;
    public final float crushedItemsPerPointOfDamage;

    public AnvilCrushingDisplay(RecipeHolder<AnvilCrushingRecipe> recipe) {
        super(recipe, recipe.value()
                            .getIngredients()
                            .stream()
                            .map(EntryIngredients::ofIngredient)
                            .toList(), Collections.singletonList(EntryIngredients.of(recipe.value()
                                                                                           .getResultItem(
                                                                                               BasicDisplay.registryAccess())))
        );
        this.experience = recipe.value()
                                .getExperience();
        this.crushedItemsPerPointOfDamage = recipe.value()
                                                  .getCrushedItemsPerPointOfDamage();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PastelPlugins.ANVIL_CRUSHING;
    }

}

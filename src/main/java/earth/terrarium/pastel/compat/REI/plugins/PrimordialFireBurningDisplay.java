package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;

public class PrimordialFireBurningDisplay extends PastelDisplay {

    public PrimordialFireBurningDisplay(RecipeHolder<PrimordialFireBurningRecipe> recipe) {
        super(
            recipe,
            recipe
                .value()
                .getIngredients()
                .stream()
                .map(EntryIngredients::ofIngredient)
                .toList(),
            Collections
                .singletonList(
                    EntryIngredients
                        .of(
                            recipe
                                .value()
                                .getResultItem(
                                    BasicDisplay.registryAccess()
                                )
                        )
                )
        );
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PastelPlugins.PRIMORDIAL_FIRE_BURNING;
    }

}

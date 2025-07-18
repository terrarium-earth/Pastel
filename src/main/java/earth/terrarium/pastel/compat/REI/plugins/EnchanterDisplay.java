package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.recipe.GatedPastelRecipe;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class EnchanterDisplay extends PastelDisplay {

    // first input is the center, all others around clockwise
    public EnchanterDisplay(
        @NotNull RecipeHolder<? extends GatedPastelRecipe<?>> recipe, List<EntryIngredient> inputs,
        List<EntryIngredient> outputs
    ) {
        super(recipe, inputs, outputs);
    }

}

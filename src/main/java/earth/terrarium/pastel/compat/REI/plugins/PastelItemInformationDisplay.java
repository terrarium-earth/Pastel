package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.api.recipe.DescriptiveGatedRecipe;
import earth.terrarium.pastel.compat.REI.PastelDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;

public abstract class PastelItemInformationDisplay extends PastelDisplay {

    protected final Item item;
    protected final Component description;

    public PastelItemInformationDisplay(RecipeHolder<? extends DescriptiveGatedRecipe<?>> recipe) {
        super(recipe, Collections.singletonList(EntryIngredients.of(recipe.value()
                                                                          .getItem())), Collections.emptyList()
        );
        this.item = recipe.value()
                          .getItem();
        this.description = recipe.value()
                                 .getDescription();
    }

    public Item getItem() {
        return this.item;
    }

    public Component getDescription() {
        return this.description;
    }

}

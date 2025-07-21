package earth.terrarium.pastel.api.recipe;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeInput;

public interface DescriptiveGatedRecipe<C extends RecipeInput> extends GatedRecipe<C> {

    Component getDescription();

    Item getItem();

}

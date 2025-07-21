package earth.terrarium.pastel.recipe.fluid_converting.dynamic;

import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Optional;

public class MeatToRottenFleshRecipe extends DragonrotConvertingRecipe {

    public MeatToRottenFleshRecipe() {
        super("", false, Optional.of(UNLOCK_IDENTIFIER), getMeatsIngredient(), Items.ROTTEN_FLESH.getDefaultInstance());
    }

    private static Ingredient getMeatsIngredient() {
        return Ingredient.of(BuiltInRegistries.ITEM.getOrCreateTag(ItemTags.MEAT)
                                                   .stream()
                                                   .filter(item -> item.value() == Items.ROTTEN_FLESH)
                                                   .map(ItemStack::new));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.DRAGONROT_MEAT_TO_ROTTEN_FLESH;
    }

}

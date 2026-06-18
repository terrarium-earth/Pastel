package earth.terrarium.pastel.compat.REI.plugins;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;

public class PotionWorkshopCraftingDisplay extends PotionWorkshopRecipeDisplay {

    protected final IngredientStack baseIngredient;

    protected final boolean consumeBaseIngredient;

    /**
     * When using the REI recipe functionality
     *
     * @param recipe The recipe
     */
    public PotionWorkshopCraftingDisplay(RecipeHolder<PotionWorkshopCraftingRecipe> recipe) {
        super(recipe);
        this.baseIngredient = recipe.value().getBaseIngredient();
        this.consumeBaseIngredient = recipe.value().consumesBaseIngredient();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PastelPlugins.POTION_WORKSHOP_CRAFTING;
    }

    @Override
    public boolean isUnlocked() {
        Minecraft client = Minecraft.getInstance();
        return DatabankUtils
            .hasAdvancement(client.player, PotionWorkshopRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
    }

}

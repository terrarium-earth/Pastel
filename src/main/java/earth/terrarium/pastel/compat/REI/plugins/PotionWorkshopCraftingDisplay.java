package earth.terrarium.pastel.compat.REI.plugins;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopCraftingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.Objects;

public class PotionWorkshopCraftingDisplay extends PotionWorkshopRecipeDisplay {

    protected final SizedIngredient baseIngredient;

    protected final boolean consumeBaseIngredient;

    /**
     * When using the REI recipe functionality
     *
     * @param recipe The recipe
     */
    public PotionWorkshopCraftingDisplay(RecipeHolder<PotionWorkshopCraftingRecipe> recipe) {
        super(recipe);
        this.baseIngredient = Objects
            .requireNonNull(recipe.value().getBaseIngredient(), "Base ingredients should never be null");
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

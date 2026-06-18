package earth.terrarium.pastel.compat.REI.plugins;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopReactingRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;

public class PotionWorkshopReactingDisplay extends PastelItemInformationDisplay {

    public PotionWorkshopReactingDisplay(RecipeHolder<PotionWorkshopReactingRecipe> recipe) {
        super(recipe);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PastelPlugins.POTION_WORKSHOP_REACTING;
    }

    @Override
    public boolean isUnlocked() {
        Minecraft client = Minecraft.getInstance();
        return DatabankUtils.hasAdvancement(client.player, PotionWorkshopRecipe.UNLOCK_IDENTIFIER) && DatabankUtils
            .hasAdvancement(client.player, PotionWorkshopRecipe.UNLOCK_IDENTIFIER) && super.isUnlocked();
    }

}

package earth.terrarium.pastel.compat.REI.plugins;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.compat.REI.FluidIngredientREI;
import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.REIHelper;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationData;
import earth.terrarium.pastel.recipe.titration_barrel.ITitrationBarrelRecipe;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TitrationBarrelDisplay extends PastelDisplay {

    protected final EntryIngredient tappingIngredient;
    protected final int minFermentationTimeHours;
    protected final FermentationData fermentationData;

    public TitrationBarrelDisplay(@NotNull RecipeHolder<ITitrationBarrelRecipe> recipe) {
        super(recipe, buildInputs(recipe.value()), List.of(buildOutputs(recipe.value())));
        if (recipe.value()
                  .getTappingItem() == Items.AIR) {
            this.tappingIngredient = EntryIngredient.empty();
        } else {
            this.tappingIngredient = EntryIngredients.of(recipe.value()
                                                               .getTappingItem()
                                                               .getDefaultInstance());
        }
        this.minFermentationTimeHours = recipe.value()
                                              .getMinFermentationTimeHours();
        this.fermentationData = recipe.value()
                                      .getFermentationData();
    }

    private static EntryIngredient buildOutputs(ITitrationBarrelRecipe recipe) {
        if (recipe instanceof TitrationBarrelRecipe titrationBarrelRecipe &&
            titrationBarrelRecipe.getFermentationData() != null) {
            return EntryIngredients.ofItemStacks(titrationBarrelRecipe.getOutputVariations(
                TitrationBarrelRecipe.FERMENTATION_DURATION_DISPLAY_TIME_MULTIPLIERS));
        } else {
            return EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()));
        }
    }

    public static List<EntryIngredient> buildInputs(ITitrationBarrelRecipe recipe) {
        List<EntryIngredient> inputs = REIHelper.toEntryIngredients(recipe.getIngredientStacks());
        if (!recipe.getFluidInput()
                   .isEmpty()) {
            inputs.add(FluidIngredientREI.into(recipe.getFluidInput()));
        }
        return inputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PastelPlugins.TITRATION_BARREL;
    }

    @Override
    public boolean isUnlocked() {
        Minecraft client = Minecraft.getInstance();
        return DatabankUtils.hasAdvancement(client.player, TitrationBarrelRecipe.UNLOCK_ADVANCEMENT_IDENTIFIER) &&
               super.isUnlocked();
    }

}

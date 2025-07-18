package earth.terrarium.pastel.compat.REI.plugins;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.compat.REI.PastelDisplay;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumCatalyst;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CrystallarieumDisplay extends PastelDisplay {

    protected final List<EntryIngredient> growthStages;
    protected final List<CrystallarieumCatalyst> catalysts;
    protected final InkColor inkColor;
    protected final boolean growsWithoutCatalyst;
    protected final int secondsPerStage;

    public CrystallarieumDisplay(@NotNull RecipeHolder<CrystallarieumRecipe> recipe) {
        super(recipe, inputs(recipe.value()), outputs(recipe.value()));

        this.growthStages = new ArrayList<>();
        for (BlockState state : recipe.value()
                                      .getGrowthStages()) {
            growthStages.add(EntryIngredients.of(state.getBlock()
                                                      .asItem()));
        }
        this.catalysts = recipe.value()
                               .getCatalysts();
        this.inkColor = recipe.value()
                              .getInkColor();
        this.growsWithoutCatalyst = recipe.value()
                                          .growsWithoutCatalyst();
        this.secondsPerStage = recipe.value()
                                     .getSecondsPerGrowthStage();
    }

    public static List<EntryIngredient> inputs(CrystallarieumRecipe recipe) {
        List<EntryIngredient> inputs = new ArrayList<>();
        inputs.add(EntryIngredients.ofIngredient(recipe.getIngredientStack()));

        Item firstBlockStateItem = recipe.getGrowthStages()
                                         .get(0)
                                         .getBlock()
                                         .asItem();
        if (firstBlockStateItem != Items.AIR) {
            inputs.add(EntryIngredients.of(firstBlockStateItem));
        }
        return inputs;
    }

    public static List<EntryIngredient> outputs(CrystallarieumRecipe recipe) {
        List<EntryIngredient> outputs = new ArrayList<>();
        outputs.add(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess())));
        for (ItemStack additionalOutput : recipe.getAdditionalResults()) {
            outputs.add(EntryIngredients.of(additionalOutput));
        }

        for (BlockState growthStageState : recipe.getGrowthStages()) {
            Item blockStateItem = growthStageState.getBlock()
                                                  .asItem();
            if (blockStateItem != Items.AIR) {
                outputs.add(EntryIngredients.of(blockStateItem));
            }
        }
        return outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PastelPlugins.CRYSTALLARIEUM;
    }

    @Override
    public boolean isUnlocked() {
        Minecraft client = Minecraft.getInstance();
        return AdvancementHelper.hasAdvancement(client.player, CrystallarieumRecipe.UNLOCK_IDENTIFIER) &&
               super.isUnlocked();
    }

}

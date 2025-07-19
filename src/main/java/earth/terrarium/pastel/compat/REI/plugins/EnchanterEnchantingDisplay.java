package earth.terrarium.pastel.compat.REI.plugins;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import earth.terrarium.pastel.recipe.enchanter.EnchanterCraftingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnchanterEnchantingDisplay extends EnchanterDisplay {

    protected final int requiredExperience;
    protected final int craftingTime;

    // first input is the center, all others around clockwise
    public EnchanterEnchantingDisplay(@NotNull RecipeHolder<EnchanterCraftingRecipe> recipe) {
        super(recipe, buildIngredients(recipe.value()), Collections.singletonList(EntryIngredients.of(recipe.value()
                                                                                                            .getResultItem(
                                                                                                                BasicDisplay.registryAccess())))
        );
        this.requiredExperience = recipe.value()
                                        .getRequiredExperience();
        this.craftingTime = recipe.value()
                                  .getCraftingTime(1);
    }

    private static List<EntryIngredient> buildIngredients(EnchanterCraftingRecipe recipe) {
        List<EntryIngredient> inputs = recipe.getIngredients()
                                             .stream()
                                             .map(EntryIngredients::ofIngredient)
                                             .collect(Collectors.toCollection(ArrayList::new));
        inputs.add(
            EntryIngredients.of(KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getRequiredExperience(), true)));
        return inputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PastelPlugins.ENCHANTER_CRAFTING;
    }

    @Override
    public boolean isUnlocked() {
        Minecraft client = Minecraft.getInstance();
        return DatabankUtils.hasAdvancement(client.player, EnchanterCraftingRecipe.UNLOCK_IDENTIFIER) &&
               super.isUnlocked();
    }

}

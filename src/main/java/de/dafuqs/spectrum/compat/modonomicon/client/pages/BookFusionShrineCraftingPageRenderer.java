package de.dafuqs.spectrum.compat.modonomicon.client.pages;

import com.mojang.blaze3d.systems.RenderSystem;
import de.dafuqs.spectrum.SpectrumCommon;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.compat.modonomicon.ModonomiconHelper;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookGatedRecipePage;
import de.dafuqs.spectrum.recipe.fusion_shrine.FusionShrineRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookFusionShrineCraftingPageRenderer extends BookGatedRecipePageRenderer<FusionShrineRecipe, BookGatedRecipePage<FusionShrineRecipe>> {

    private static final ResourceLocation BACKGROUND_TEXTURE = SpectrumCommon.locate("textures/gui/modonomicon/fusion_shrine.png");

    public BookFusionShrineCraftingPageRenderer(BookGatedRecipePage<FusionShrineRecipe> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 68;
    }

    @Override
    protected void drawRecipe(GuiGraphics drawContext, RecipeHolder<FusionShrineRecipe> recipeEntry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        FusionShrineRecipe recipe = recipeEntry.value();
        Level world = Minecraft.getInstance().level;
        if (world == null) return;

        RenderSystem.enableBlend();
        drawContext.blit(BACKGROUND_TEXTURE, recipeX - 2, recipeY - 2, 0, 0, 104, 97, 128, 256);

        renderTitle(drawContext, recipeY, second);

        // the ingredients
        List<IngredientStack> ingredients = recipe.getIngredientStacks();
        int startX = Math.max(-10, 30 - ingredients.size() * 8);
        for (int i = 0; i < ingredients.size(); i++) {
            ModonomiconHelper.renderIngredientStack(drawContext, parentScreen, recipeX + startX + i * 16, recipeY + 3, mouseX, mouseY, ingredients.get(i));
        }
        
        if (recipe.getFluid() != FluidIngredient.EMPTY) {
            @NotNull Ingredient fluidIngredient = recipe.getFluid().into();
            parentScreen.renderItemStack(drawContext, recipeX + 14, recipeY + 31, mouseX, mouseY, recipe.getToastSymbol()); // the shrine
            parentScreen.renderIngredient(drawContext, recipeX + 30, recipeY + 31, mouseX, mouseY, fluidIngredient); // the fluid
        } else {
            parentScreen.renderItemStack(drawContext, recipeX + 22, recipeY + 31, mouseX, mouseY, recipe.getToastSymbol()); // the shrine
        }

        // the output
        parentScreen.renderItemStack(drawContext, recipeX + 78, recipeY + 31, mouseX, mouseY, recipe.getResultItem(world.registryAccess()));
    }

}

package de.dafuqs.spectrum.compat.modonomicon.client.pages;

import com.mojang.blaze3d.systems.RenderSystem;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.compat.modonomicon.ModonomiconHelper;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookGatedRecipePage;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class BookPotionWorkshopPageRenderer<T extends PotionWorkshopRecipe> extends BookGatedRecipePageRenderer<T, BookGatedRecipePage<T>> {

    private static final ResourceLocation BACKGROUND_TEXTURE = SpectrumCommon.locate("textures/gui/modonomicon/potion_workshop.png");

    public BookPotionWorkshopPageRenderer(BookGatedRecipePage<T> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 97;
    }

    @Override
    protected void drawRecipe(GuiGraphics drawContext, RecipeHolder<T> recipeEntry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        T recipe = recipeEntry.value();
        Level world = Minecraft.getInstance().level;
        if (world == null) return;

        RenderSystem.enableBlend();
        drawContext.blit(BACKGROUND_TEXTURE, recipeX - 2, recipeY - 2, 0, 0, 104, 97, 128, 256);

        renderTitle(drawContext, recipeY, second);

        // the ingredients
        List<IngredientStack> ingredients = recipe.getIngredientStacks();
        ModonomiconHelper.renderIngredientStack(drawContext, parentScreen, recipeX + 20, recipeY + 62, mouseX, mouseY, ingredients.get(0));
        ModonomiconHelper.renderIngredientStack(drawContext, parentScreen, recipeX + 58, recipeY + 5, mouseX, mouseY, ingredients.get(1));
        ModonomiconHelper.renderIngredientStack(drawContext, parentScreen, recipeX + 20, recipeY + 9, mouseX, mouseY, ingredients.get(2));
        ModonomiconHelper.renderIngredientStack(drawContext, parentScreen, recipeX + 3, recipeY + 32, mouseX, mouseY, ingredients.get(3));
        ModonomiconHelper.renderIngredientStack(drawContext, parentScreen, recipeX + 37, recipeY + 32, mouseX, mouseY, ingredients.get(4));

        // the potion workshop
        parentScreen.renderItemStack(drawContext, recipeX + 82, recipeY + 42, mouseX, mouseY, recipe.getToastSymbol());

        // the output
        parentScreen.renderItemStack(drawContext, recipeX + 82, recipeY + 24, mouseX, mouseY, recipe.getResultItem(world.registryAccess()));
    }

}

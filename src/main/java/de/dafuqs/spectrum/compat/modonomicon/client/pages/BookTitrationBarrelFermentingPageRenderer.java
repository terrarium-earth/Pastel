package de.dafuqs.spectrum.compat.modonomicon.client.pages;

import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.mojang.blaze3d.systems.RenderSystem;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.recipe.FluidIngredient;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.compat.modonomicon.ModonomiconHelper;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookGatedRecipePage;
import de.dafuqs.spectrum.recipe.titration_barrel.TitrationBarrelRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class BookTitrationBarrelFermentingPageRenderer extends BookGatedRecipePageRenderer<TitrationBarrelRecipe, BookGatedRecipePage<TitrationBarrelRecipe>> {

    private static final ResourceLocation BACKGROUND_TEXTURE = SpectrumCommon.locate("textures/gui/modonomicon/titration_barrel.png");

    private final BookTextHolder durationText1;
    private final BookTextHolder durationText2;

    public BookTitrationBarrelFermentingPageRenderer(BookGatedRecipePage<TitrationBarrelRecipe> page) {
        super(page);
		
		RecipeHolder<TitrationBarrelRecipe> recipe1 = page.getRecipe1();
		RecipeHolder<TitrationBarrelRecipe> recipe2 = page.getRecipe2();
        
        ResourceLocation font = BookDataManager.Client.get().safeFont(this.page.getBook().getFont());
		durationText1 = recipe1 == null ? null : new BookTextHolder(TitrationBarrelRecipe.getDurationText(recipe1.value().getMinFermentationTimeHours(), recipe1.value().getFermentationData()).withStyle(s -> s.withFont(font)));
		durationText2 = recipe2 == null ? null : new BookTextHolder(TitrationBarrelRecipe.getDurationText(recipe2.value().getMinFermentationTimeHours(), recipe2.value().getFermentationData()).withStyle(s -> s.withFont(font)));
    }

    @Override
    protected int getRecipeHeight() {
        return 70;
    }

    @Override
    protected void drawRecipe(GuiGraphics drawContext, RecipeHolder<TitrationBarrelRecipe> recipeEntry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        TitrationBarrelRecipe recipe = recipeEntry.value();
        Level world = Minecraft.getInstance().level;
        if (world == null) return;

        RenderSystem.enableBlend();
        drawContext.blit(BACKGROUND_TEXTURE, recipeX - 2, recipeY - 2, 0, 0, 100, 32, 128, 256);

        renderTitle(drawContext, recipeY, second);

        FluidIngredient fluid = recipe.getFluidInput();
        boolean usesFluid = fluid != FluidIngredient.EMPTY;
        IngredientStack bucketStack = IngredientStack.EMPTY;
        if (usesFluid) {
            bucketStack = IngredientStack.of(recipe.getFluidInput().into());
        }

        // the ingredients
        List<IngredientStack> ingredients = recipe.getIngredientStacks();
        int ingredientSize = ingredients.size();
        int ingredientSizeWithFluid = usesFluid ? ingredientSize + 1 : ingredientSize;
        int startX = recipeX + Math.max(-5, 15 - ingredientSizeWithFluid * 10);
        int startY = recipeY + (ingredientSizeWithFluid > 3 ? 0 : 10);
        for (int i = 0; i < ingredientSizeWithFluid; i++) {
            IngredientStack currentIngredient = i == ingredientSize ? bucketStack : ingredients.get(i);
            int yOffset;
            int xOffset;
            if (i < 3) {
                xOffset = i * 18;
                yOffset = 0;
            } else {
                xOffset = (i - 3) * 18;
                yOffset = 18;
            }
            ModonomiconHelper.renderIngredientStack(drawContext, parentScreen, startX + xOffset, startY + yOffset, mouseX, mouseY, currentIngredient);
        }

        // the titration barrel / tapping ingredient
        if (recipe.getTappingItem() == Items.AIR) {
            parentScreen.renderItemStack(drawContext, recipeX + 54, recipeY + 20, mouseX, mouseY, recipe.getToastSymbol());
        } else {
            parentScreen.renderItemStack(drawContext, recipeX + 50, recipeY + 20, mouseX, mouseY, recipe.getToastSymbol());
            parentScreen.renderItemStack(drawContext, recipeX + 60, recipeY + 20, mouseX, mouseY, recipe.getTappingItem().getDefaultInstance());
        }

        // the output
        parentScreen.renderItemStack(drawContext, recipeX + 78, recipeY + 10, mouseX, mouseY, recipe.getResultItem(world.registryAccess()));

        // the duration
        if (!second && durationText1 != null) {
            this.renderBookTextHolder(drawContext, durationText1, 0, recipeY + 40, BookEntryScreen.PAGE_WIDTH);
        }
        if (second && durationText2 != null) {
            this.renderBookTextHolder(drawContext, durationText2, 0, recipeY + 40, BookEntryScreen.PAGE_WIDTH);
        }
    }

}

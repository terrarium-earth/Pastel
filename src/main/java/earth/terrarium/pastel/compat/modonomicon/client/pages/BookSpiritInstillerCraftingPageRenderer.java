package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconHelper;
import earth.terrarium.pastel.compat.modonomicon.pages.BookGatedRecipePage;
import earth.terrarium.pastel.recipe.spirit_instiller.SpiritInstillerRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class BookSpiritInstillerCraftingPageRenderer
    extends
    BookGatedRecipePageRenderer<SpiritInstillerRecipe, BookGatedRecipePage<SpiritInstillerRecipe>> {

    private static final ResourceLocation BACKGROUND_TEXTURE = PastelCommon
        .locate(
            "textures/gui/modonomicon/spirit_instiller.png"
        );

    private static final ItemStack ITEM_BOWL_STACK = PastelBlocks.ITEM_BOWL_CALCITE
        .get()
        .asItem()
        .getDefaultInstance();

    public BookSpiritInstillerCraftingPageRenderer(BookGatedRecipePage<SpiritInstillerRecipe> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 58;
    }

    @Override
    protected void drawRecipe(
        GuiGraphics drawContext,
        RecipeHolder<SpiritInstillerRecipe> recipeEntry,
        int recipeX,
        int recipeY,
        int mouseX,
        int mouseY,
        boolean second
    ) {
        SpiritInstillerRecipe recipe = recipeEntry.value();
        Level world = Minecraft.getInstance().level;
        if (world == null) return;

        RenderSystem.enableBlend();
        drawContext.blit(BACKGROUND_TEXTURE, recipeX - 2, recipeY - 2, 0, 0, 104, 97, 128, 256);

        renderTitle(drawContext, recipeY, second);

        // the ingredients
        List<IngredientStack> ingredients = recipe.getIngredientStacks();
        ModonomiconHelper
            .renderIngredientStack(
                drawContext,
                parentScreen,
                recipeX + 3,
                recipeY + 8,
                mouseX,
                mouseY,
                ingredients.get(1)
            ); // left
        ModonomiconHelper
            .renderIngredientStack(
                drawContext,
                parentScreen,
                recipeX + 23,
                recipeY + 11,
                mouseX,
                mouseY,
                ingredients.get(0)
            ); // center
        ModonomiconHelper
            .renderIngredientStack(
                drawContext,
                parentScreen,
                recipeX + 44,
                recipeY + 8,
                mouseX,
                mouseY,
                ingredients.get(2)
            ); // right

        // spirit instiller
        parentScreen.renderItemStack(drawContext, recipeX + 23, recipeY + 25, mouseX, mouseY, recipe.getToastSymbol());

        // item bowls
        parentScreen.renderItemStack(drawContext, recipeX + 3, recipeY + 25, mouseX, mouseY, ITEM_BOWL_STACK);
        parentScreen.renderItemStack(drawContext, recipeX + 44, recipeY + 25, mouseX, mouseY, ITEM_BOWL_STACK);

        // the output
        parentScreen
            .renderItemStack(
                drawContext,
                recipeX + 79,
                recipeY + 8,
                mouseX,
                mouseY,
                recipe.getResultItem(world.registryAccess())
            );
    }

}

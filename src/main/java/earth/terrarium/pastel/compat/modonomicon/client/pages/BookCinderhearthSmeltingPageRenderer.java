package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.client.gui.book.entry.BookEntryScreen;
import com.klikli_dev.modonomicon.data.BookDataManager;
import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.modonomicon.pages.BookGatedRecipePage;
import earth.terrarium.pastel.recipe.cinderhearth.CinderhearthRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class BookCinderhearthSmeltingPageRenderer
    extends
    BookGatedRecipePageRenderer<CinderhearthRecipe, BookGatedRecipePage<CinderhearthRecipe>> {

    private static final ResourceLocation BACKGROUND_TEXTURE = PastelCommon
        .locate(
            "textures/gui/modonomicon/cinderhearth.png"
        );

    private List<BookTextHolder> chanceTexts1 = null;

    private List<BookTextHolder> chanceTexts2 = null;

    public BookCinderhearthSmeltingPageRenderer(BookGatedRecipePage<CinderhearthRecipe> page) {
        super(page);
    }

    @Override
    public void onBeginDisplayPage(BookEntryScreen parentScreen, int left, int top) {
        super.onBeginDisplayPage(parentScreen, left, top);

        if (chanceTexts1 == null) {
            if (page.getRecipe1() != null)
                chanceTexts1 = createChanceTexts(
                    page
                        .getRecipe1()
                        .value()
                );
        }
        if (chanceTexts2 == null) {
            if (page.getRecipe2() != null)
                chanceTexts2 = createChanceTexts(
                    page
                        .getRecipe2()
                        .value()
                );
        }
    }

    private List<BookTextHolder> createChanceTexts(CinderhearthRecipe recipe) {
        if (recipe == null) return null;

        Level world = Minecraft.getInstance().level;
        if (world == null) return null;

        ResourceLocation font = BookDataManager.Client
            .get()
            .safeFont(
                this.page
                    .getBook()
                    .getFont()
            );

        List<BookTextHolder> chanceTexts = new ArrayList<>();
        List<Tuple<ItemStack, Float>> possibleOutputs = recipe.getResultsWithChance();

        int chanceTextIndex = 0;
        for (
            Tuple<ItemStack, Float> possibleOutput : possibleOutputs
        ) {
            if (possibleOutput.getB() < 1.0F) {
                if (chanceTexts.size() < chanceTextIndex + 1) {
                    chanceTexts
                        .add(
                            new BookTextHolder(
                                Component
                                    .literal(String.format("%f.2%%", possibleOutput.getB() * 100))
                                    .withStyle(s -> s.withFont(font))
                            )
                        );
                }
                chanceTextIndex++;
            }
        }

        return chanceTexts;
    }

    @Override
    protected int getRecipeHeight() {
        return 54;
    }

    @Override
    protected void drawRecipe(
        GuiGraphics drawContext,
        RecipeHolder<CinderhearthRecipe> recipeEntry,
        int recipeX,
        int recipeY,
        int mouseX,
        int mouseY,
        boolean second
    ) {
        CinderhearthRecipe recipe = recipeEntry.value();
        Level world = Minecraft.getInstance().level;
        if (world == null) return;

        RenderSystem.enableBlend();

        List<Tuple<ItemStack, Float>> possibleOutputs = recipe.getResultsWithChance();
        recipeX = Math.max(recipeX, recipeX + 26 - possibleOutputs.size() * 10);

        int backgroundTextureWidth = 34 + possibleOutputs.size() * 24;
        drawContext.blit(BACKGROUND_TEXTURE, recipeX - 1, recipeY - 2, 0, 0, backgroundTextureWidth, 45, 128, 128);

        renderTitle(drawContext, recipeY, second);

        // the ingredient
        var ingredientStack = recipe
            .getIngredientStacks()
            .getFirst();
        parentScreen
            .renderIngredient(
                drawContext,
                recipeX + 2,
                recipeY + 7,
                mouseX,
                mouseY,
                ingredientStack.getIngredient(),
                ingredientStack.getCount()
            );

        // cinderhearth
        parentScreen.renderItemStack(drawContext, recipeX + 21, recipeY + 26, mouseX, mouseY, recipe.getToastSymbol());

        // outputs
        int chanceTextIndex = 0;
        for (
            int i = 0;
            i < possibleOutputs.size();
            i++
        ) {
            Tuple<ItemStack, Float> possibleOutput = possibleOutputs.get(i);
            int x = recipeX + 37 + i * 23;
            parentScreen.renderItemStack(drawContext, x, recipeY + 6, mouseX, mouseY, possibleOutput.getA());

            if (possibleOutput.getB() < 1.0F) {
                var chance = second ? chanceTexts2 : chanceTexts1;

                if (chance == null)
                    continue;

                BookTextHolder chanceText = chance.get(chanceTextIndex);
                renderBookTextHolder(drawContext, chanceText, x, recipeY + 24, BookEntryScreen.PAGE_WIDTH);
                chanceTextIndex++;
            }
        }
    }

}

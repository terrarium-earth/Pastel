package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.modonomicon.pages.BookGatedRecipePage;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import earth.terrarium.pastel.recipe.enchanter.EnchanterCraftingRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class BookEnchanterCraftingPageRenderer extends BookGatedRecipePageRenderer<EnchanterCraftingRecipe, BookGatedRecipePage<EnchanterCraftingRecipe>> {

    private static final ResourceLocation BACKGROUND_TEXTURE = PastelCommon.locate("textures/gui/modonomicon/enchanter_crafting.png");

    public BookEnchanterCraftingPageRenderer(BookGatedRecipePage<EnchanterCraftingRecipe> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 94;
    }

    @Override
    protected void drawRecipe(GuiGraphics drawContext, RecipeHolder<EnchanterCraftingRecipe> recipeEntry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        EnchanterCraftingRecipe recipe = recipeEntry.value();
        Level world = Minecraft.getInstance().level;
        if (world == null) return;

        RenderSystem.enableBlend();
        drawContext.blit(BACKGROUND_TEXTURE, recipeX, recipeY, 0, 0, 100, 80, 256, 256);

        renderTitle(drawContext, recipeY, second);

        // the ingredients
        NonNullList<Ingredient> ingredients = recipe.getIngredients();

        int ingredientX = recipeX - 3;

        // surrounding input slots
        parentScreen.renderIngredient(drawContext, ingredientX + 16, recipeY, mouseX, mouseY, ingredients.get(1));
        parentScreen.renderIngredient(drawContext, ingredientX + 40, recipeY, mouseX, mouseY, ingredients.get(2));
        parentScreen.renderIngredient(drawContext, ingredientX + 56, recipeY + 16, mouseX, mouseY, ingredients.get(3));
        parentScreen.renderIngredient(drawContext, ingredientX + 56, recipeY + 40, mouseX, mouseY, ingredients.get(4));
        parentScreen.renderIngredient(drawContext, ingredientX + 40, recipeY + 56, mouseX, mouseY, ingredients.get(5));
        parentScreen.renderIngredient(drawContext, ingredientX + 16, recipeY + 56, mouseX, mouseY, ingredients.get(6));
        parentScreen.renderIngredient(drawContext, ingredientX, recipeY + 40, mouseX, mouseY, ingredients.get(7));
        parentScreen.renderIngredient(drawContext, ingredientX, recipeY + 16, mouseX, mouseY, ingredients.get(8));

        // center input slot
        parentScreen.renderIngredient(drawContext, ingredientX + 28, recipeY + 28, mouseX, mouseY, ingredients.get(0));

        // Knowledge Gem and Enchanter
        ItemStack knowledgeDropStackWithXP = KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getRequiredExperience(), true);
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 9, mouseX, mouseY, knowledgeDropStackWithXP);
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 46, mouseX, mouseY, PastelBlocks.ENCHANTER.get().asItem().getDefaultInstance());

        // the output
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 31, mouseX, mouseY, recipe.getResultItem(world.registryAccess()));
    }

}

package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.modonomicon.pages.BookGatedRecipePage;
import earth.terrarium.pastel.items.magic_items.KnowledgeGemItem;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class BookEnchanterUpgradingPageRenderer extends BookGatedRecipePageRenderer<EnchantmentUpgradeRecipe, BookGatedRecipePage<EnchantmentUpgradeRecipe>> {

    private static final ResourceLocation BACKGROUND_TEXTURE = PastelCommon.locate("textures/gui/modonomicon/enchanter_crafting.png");

    public BookEnchanterUpgradingPageRenderer(BookGatedRecipePage<EnchantmentUpgradeRecipe> page) {
        super(page);
    }

    @Override
    protected int getRecipeHeight() {
        return 94;
    }

    @Override
    protected void drawRecipe(GuiGraphics drawContext, RecipeHolder<EnchantmentUpgradeRecipe> recipeEntry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        EnchantmentUpgradeRecipe recipe = recipeEntry.value();
        Level world = Minecraft.getInstance().level;
        if (world == null) return;

        RenderSystem.enableBlend();
        drawContext.blit(BACKGROUND_TEXTURE, recipeX, recipeY, 0, 0, 100, 80, 256, 256);

        renderTitle(drawContext, recipeY, second);

        // the ingredients
        NonNullList<Ingredient> ingredients = recipe.getIngredients();

        int ingredientX = recipeX - 3;

        // surrounding input slots
        List<ItemStack> inputStacks = new ArrayList<>();
        int requiredItemCountSplit = recipe.getBaseItemCost() / 8;
        int requiredItemCountModulo = recipe.getBaseItemCost() % 8;
        for (int i = 0; i < 8; i++) {
            int addAmount = i < requiredItemCountModulo ? 1 : 0;
            inputStacks.add(new ItemStack(recipe.getBulkItem(), requiredItemCountSplit + addAmount));
        }

        parentScreen.renderItemStack(drawContext, ingredientX + 16, recipeY, mouseX, mouseY, inputStacks.get(0));
        parentScreen.renderItemStack(drawContext, ingredientX + 40, recipeY, mouseX, mouseY, inputStacks.get(1));
        parentScreen.renderItemStack(drawContext, ingredientX + 56, recipeY + 16, mouseX, mouseY, inputStacks.get(2));
        parentScreen.renderItemStack(drawContext, ingredientX + 56, recipeY + 40, mouseX, mouseY, inputStacks.get(3));
        parentScreen.renderItemStack(drawContext, ingredientX + 40, recipeY + 56, mouseX, mouseY, inputStacks.get(4));
        parentScreen.renderItemStack(drawContext, ingredientX + 16, recipeY + 56, mouseX, mouseY, inputStacks.get(5));
        parentScreen.renderItemStack(drawContext, ingredientX, recipeY + 40, mouseX, mouseY, inputStacks.get(6));
        parentScreen.renderItemStack(drawContext, ingredientX, recipeY + 16, mouseX, mouseY, inputStacks.get(7));

        // center input slot
        parentScreen.renderIngredient(drawContext, ingredientX + 28, recipeY + 28, mouseX, mouseY, ingredients.getFirst());

        // Knowledge Gem and Enchanter
        ItemStack knowledgeDropStackWithXP = KnowledgeGemItem.getKnowledgeDropStackWithXP(recipe.getBaseXPCost(), true);
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 9, mouseX, mouseY, knowledgeDropStackWithXP);
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 46, mouseX, mouseY, PastelBlocks.ENCHANTER.get().asItem().getDefaultInstance());

        // the output
        parentScreen.renderItemStack(drawContext, recipeX + 81, recipeY + 31, mouseX, mouseY, recipe.getResultItem(world.registryAccess()));
    }

}

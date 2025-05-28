package earth.terrarium.pastel.compat.modonomicon.client.pages;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconHelper;
import earth.terrarium.pastel.compat.modonomicon.pages.BookGatedRecipePage;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipeTier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookPedestalCraftingPageRenderer extends BookGatedRecipePageRenderer<PedestalRecipe, BookGatedRecipePage<PedestalRecipe>> {
	
	private static final ResourceLocation BACKGROUND_TEXTURE1 = SpectrumCommon.locate("textures/gui/modonomicon/pedestal_crafting1.png");
	private static final ResourceLocation BACKGROUND_TEXTURE2 = SpectrumCommon.locate("textures/gui/modonomicon/pedestal_crafting2.png");
	private static final ResourceLocation BACKGROUND_TEXTURE3 = SpectrumCommon.locate("textures/gui/modonomicon/pedestal_crafting3.png");
	private static final ResourceLocation BACKGROUND_TEXTURE4 = SpectrumCommon.locate("textures/gui/modonomicon/pedestal_crafting4.png");
	
	public BookPedestalCraftingPageRenderer(BookGatedRecipePage<PedestalRecipe> page) {
		super(page);
	}
	
	@Override
	protected int getRecipeHeight() {
		return 110;
	}

    @Override
    protected void drawRecipe(GuiGraphics drawContext, RecipeHolder<PedestalRecipe> recipeEntry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
        PedestalRecipe recipe = recipeEntry.value();
        Level world = Minecraft.getInstance().level;
        if (world == null) return;

        RenderSystem.enableBlend();
        drawContext.blit(getBackgroundTextureForTier(recipe.getTier()), recipeX - 2, recipeY - 2, 0, 0, 106, 97, 128, 256);

        renderTitle(drawContext, recipeY, second);

        // the output
        parentScreen.renderItemStack(drawContext, recipeX + 78, recipeY + 22, mouseX, mouseY, recipe.getResultItem(world.registryAccess()));

        // the powders
        switch (recipe.getTier()) {
            case COMPLEX -> drawGemstonePowderSlots(drawContext, recipe, recipe.getTier().getAvailableGemstoneColors(), 3, recipeX, recipeY, mouseX, mouseY);
            case ADVANCED -> drawGemstonePowderSlots(drawContext, recipe, recipe.getTier().getAvailableGemstoneColors(),12, recipeX, recipeY, mouseX, mouseY);
            default -> drawGemstonePowderSlots(drawContext, recipe, recipe.getTier().getAvailableGemstoneColors(),22, recipeX, recipeY, mouseX, mouseY);
        }

        // the ingredients
        List<IngredientStack> ingredients = recipe.getIngredientStacks();
        int wrap = recipe.getWidth();
        for (int i = 0; i < ingredients.size(); i++) {
            ModonomiconHelper.renderIngredientStack(drawContext, parentScreen, recipeX + (i % wrap) * 19 + 3, recipeY + (i / wrap) * 19 + 3, mouseX, mouseY, ingredients.get(i));
        }
    }

    @Contract(pure = true)
    private ResourceLocation getBackgroundTextureForTier(@NotNull PedestalRecipeTier pedestalRecipeTier) {
        return switch (pedestalRecipeTier) {
            case BASIC -> BACKGROUND_TEXTURE1;
            case SIMPLE -> BACKGROUND_TEXTURE2;
            case ADVANCED -> BACKGROUND_TEXTURE3;
            default -> BACKGROUND_TEXTURE4;
        };
    }

    private void drawGemstonePowderSlots(GuiGraphics drawContext, PedestalRecipe recipe, GemstoneColor @NotNull [] colors, int startX, int recipeX, int recipeY, int mouseX, int mouseY) {
        int h = 0;
        for (GemstoneColor color : colors) {
            int amount = recipe.getPowderInputs().getOrDefault(color, 0);
            if (amount > 0) {
                ItemStack stack = color.getGemstonePowderItem().getDefaultInstance();
                stack.setCount(amount);
                parentScreen.renderItemStack(drawContext, recipeX + startX + h * 19, recipeY + 72, mouseX, mouseY, stack);
            }
            h++;
        }
    }

}

package de.dafuqs.spectrum.compat.modonomicon.client.pages;

import com.mojang.blaze3d.systems.RenderSystem;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.compat.modonomicon.pages.BookGatedRecipePage;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class BookPrimordialFireBurningPageRenderer<R extends PrimordialFireBurningRecipe, T extends BookGatedRecipePage<R>> extends BookGatedRecipePageRenderer<R, T> {
	
	private static final ResourceLocation BACKGROUND_TEXTURE = SpectrumCommon.locate("textures/gui/modonomicon/primordial_fire.png");
	
	public BookPrimordialFireBurningPageRenderer(T page) {
		super(page);
	}
	
	@Override
	protected int getRecipeHeight() {
		return 58;
	}
	
	@Override
	protected void drawRecipe(GuiGraphics drawContext, RecipeHolder<R> recipeEntry, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
		R recipe = recipeEntry.value();
		Level world = Minecraft.getInstance().level;
		if (world == null) return;
		
		renderTitle(drawContext, recipeY, second);
		
		RenderSystem.enableBlend();
		drawContext.blit(BACKGROUND_TEXTURE, recipeX + 13, recipeY + 6, 0, 0, 57, 40, 64, 64);
		
		// the ingredient
		NonNullList<Ingredient> ingredients = recipe.getIngredients();
		parentScreen.renderIngredient(drawContext, recipeX + 16, recipeY + 8, mouseX, mouseY, ingredients.getFirst());
		
		// the output
		parentScreen.renderItemStack(drawContext, recipeX + 51, recipeY + 8, mouseX, mouseY, recipe.getResultItem(world.registryAccess()));
	}
	
}

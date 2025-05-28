package earth.terrarium.pastel.compat.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookAndCondition;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import com.klikli_dev.modonomicon.util.BookGsonHelper;
import earth.terrarium.pastel.api.block.GatedGuidebookPage;
import earth.terrarium.pastel.api.recipe.GatedRecipe;
import earth.terrarium.pastel.compat.modonomicon.unlock_conditions.RecipesLoadedAndUnlockedCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class BookGatedRecipePage<T extends GatedRecipe<?>> extends BookRecipePage<T> implements GatedGuidebookPage {
	
	private final ResourceLocation pageType;
	
	public BookGatedRecipePage(RecipeType<T> recipeType, ResourceLocation pageType, BookTextHolder title1, ResourceLocation recipeId1, BookTextHolder title2, ResourceLocation recipeId2, BookTextHolder text, String anchor, BookCondition condition) {
		super(recipeType, title1, recipeId1, title2, recipeId2, text, anchor, condition);
		this.pageType = pageType;
	}
	
	public static BookCondition getConditionWithRecipes(BookCondition condition, ResourceLocation recipeId1, ResourceLocation recipeId2) {
		List<ResourceLocation> list = new ArrayList<>();
		if (recipeId1 != null) {
			list.add(recipeId1);
		}
		if (recipeId2 != null) {
			list.add(recipeId2);
		}
		BookCondition[] conditions = {condition, new RecipesLoadedAndUnlockedCondition(null, list)};
		return new BookAndCondition(null, conditions);
	}
	
	public static <T extends GatedRecipe<?>> BookGatedRecipePage<T> fromJson(ResourceLocation entryId, ResourceLocation pageType, RecipeType<T> recipeType, JsonObject json, boolean supportsTwoRecipesOnOnePage, HolderLookup.Provider provider) {
		var anchor = GsonHelper.getAsString(json, "anchor", "");
		var condition = json.has("condition")
				? BookCondition.fromJson(entryId, json.getAsJsonObject("condition"), provider)
				: new BookNoneCondition();
		var text = BookGsonHelper.getAsBookTextHolder(json, "text", BookTextHolder.EMPTY, provider);
		var skipRecipeUnlockCheck = GsonHelper.getAsBoolean(json, "skip_recipe_unlock_check", false);
		
		if (supportsTwoRecipesOnOnePage) {
			var title1 = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
			var title2 = BookGsonHelper.getAsBookTextHolder(json, "title2", BookTextHolder.EMPTY, provider);
			ResourceLocation recipeId1 = json.has("recipe_id") ? ResourceLocation.tryParse(GsonHelper.getAsString(json, "recipe_id")) : null;
			ResourceLocation recipeId2 = json.has("recipe_id2") ? ResourceLocation.tryParse(GsonHelper.getAsString(json, "recipe_id2")) : null;
			condition = skipRecipeUnlockCheck ? condition : getConditionWithRecipes(condition, recipeId1, recipeId2);
			return new BookGatedRecipePage<>(recipeType, pageType, title1, recipeId1, title2, recipeId2, text, anchor, condition);
		} else {
			var title = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
			ResourceLocation recipeId = json.has("recipe_id") ? ResourceLocation.tryParse(GsonHelper.getAsString(json, "recipe_id")) : null;
			condition = skipRecipeUnlockCheck ? condition : getConditionWithRecipes(condition, recipeId, null);
			return new BookGatedRecipePage<>(recipeType, pageType, title, recipeId, BookTextHolder.EMPTY, null, text, anchor, condition);
		}
	}
	
	public static <T extends GatedRecipe<?>> BookGatedRecipePage<T> fromNetwork(ResourceLocation pageType, RecipeType<T> recipeType, RegistryFriendlyByteBuf buffer) {
		var common = BookRecipePage.commonFromNetwork(buffer);
		var anchor = buffer.readUtf();
		var condition = BookCondition.fromNetwork(buffer);
		return new BookGatedRecipePage<>(recipeType, pageType, common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor, condition);
	}
	
	@Override
	protected ItemStack getRecipeOutput(Level world, RecipeHolder<T> recipeEntry) {
		if (recipeEntry == null) {
			return ItemStack.EMPTY;
		}
		return recipeEntry.value().getResultItem(world.registryAccess());
	}
	
	@Override
	public ResourceLocation getType() {
		return this.pageType;
	}
	
}

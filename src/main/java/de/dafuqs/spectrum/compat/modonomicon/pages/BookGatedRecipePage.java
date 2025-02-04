package de.dafuqs.spectrum.compat.modonomicon.pages;

import com.google.gson.*;
import com.klikli_dev.modonomicon.book.*;
import com.klikli_dev.modonomicon.book.conditions.*;
import com.klikli_dev.modonomicon.book.page.*;
import com.klikli_dev.modonomicon.util.*;
import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.compat.modonomicon.unlock_conditions.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class BookGatedRecipePage<T extends GatedRecipe<?>> extends BookRecipePage<T> implements GatedGuidebookPage {
	
	private final Identifier pageType;
	
	public BookGatedRecipePage(RecipeType<T> recipeType, Identifier pageType, BookTextHolder title1, Identifier recipeId1, BookTextHolder title2, Identifier recipeId2, BookTextHolder text, String anchor, BookCondition condition) {
		super(recipeType, title1, recipeId1, title2, recipeId2, text, anchor, condition);
		this.pageType = pageType;
	}
	
	public static BookCondition getConditionWithRecipes(BookCondition condition, Identifier recipeId1, Identifier recipeId2) {
		List<Identifier> list = new ArrayList<>();
		if (recipeId1 != null) {
			list.add(recipeId1);
		}
		if (recipeId2 != null) {
			list.add(recipeId2);
		}
		BookCondition[] conditions = {condition, new RecipesLoadedAndUnlockedCondition(null, list)};
		return new BookAndCondition(null, conditions);
	}
	
	public static <T extends GatedRecipe<?>> BookGatedRecipePage<T> fromJson(Identifier entryId, Identifier pageType, RecipeType<T> recipeType, JsonObject json, boolean supportsTwoRecipesOnOnePage, RegistryWrapper.WrapperLookup provider) {
		var anchor = JsonHelper.getString(json, "anchor", "");
		var condition = json.has("condition")
				? BookCondition.fromJson(entryId, json.getAsJsonObject("condition"), provider)
				: new BookNoneCondition();
		var text = BookGsonHelper.getAsBookTextHolder(json, "text", BookTextHolder.EMPTY, provider);
		var skipRecipeUnlockCheck = JsonHelper.getBoolean(json, "skip_recipe_unlock_check", false);
		
		if (supportsTwoRecipesOnOnePage) {
			var title1 = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
			var title2 = BookGsonHelper.getAsBookTextHolder(json, "title2", BookTextHolder.EMPTY, provider);
			Identifier recipeId1 = json.has("recipe_id") ? Identifier.tryParse(JsonHelper.getString(json, "recipe_id")) : null;
			Identifier recipeId2 = json.has("recipe_id2") ? Identifier.tryParse(JsonHelper.getString(json, "recipe_id2")) : null;
			condition = skipRecipeUnlockCheck ? condition : getConditionWithRecipes(condition, recipeId1, recipeId2);
			return new BookGatedRecipePage<>(recipeType, pageType, title1, recipeId1, title2, recipeId2, text, anchor, condition);
		} else {
			var title = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
			Identifier recipeId = json.has("recipe_id") ? Identifier.tryParse(JsonHelper.getString(json, "recipe_id")) : null;
			condition = skipRecipeUnlockCheck ? condition : getConditionWithRecipes(condition, recipeId, null);
			return new BookGatedRecipePage<>(recipeType, pageType, title, recipeId, BookTextHolder.EMPTY, null, text, anchor, condition);
		}
	}
	
	public static <T extends GatedRecipe<?>> BookGatedRecipePage<T> fromNetwork(Identifier pageType, RecipeType<T> recipeType, RegistryByteBuf buffer) {
		var common = BookRecipePage.commonFromNetwork(buffer);
		var anchor = buffer.readString();
		var condition = BookCondition.fromNetwork(buffer);
		return new BookGatedRecipePage<>(recipeType, pageType, common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor, condition);
	}
	
	@Override
	protected ItemStack getRecipeOutput(World world, RecipeEntry<T> recipeEntry) {
		if (recipeEntry == null) {
			return ItemStack.EMPTY;
		}
		return recipeEntry.value().getResult(world.getRegistryManager());
	}
	
	@Override
	public Identifier getType() {
		return this.pageType;
	}
	
}

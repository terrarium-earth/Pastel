package de.dafuqs.spectrum.compat.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookTextPage;
import com.klikli_dev.modonomicon.util.BookGsonHelper;
import com.mojang.serialization.JsonOps;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.compat.modonomicon.ModonomiconCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class BookHintPage extends BookTextPage {

    private final ResourceLocation completionAdvancement;
	private final IngredientStack cost;
	
	public BookHintPage(BookTextHolder title, BookTextHolder text, boolean useMarkdownInTitle, boolean showTitleSeparator, String anchor, BookCondition condition, ResourceLocation completionAdvancement, IngredientStack cost) {
        super(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition);
        this.completionAdvancement = completionAdvancement;
        this.cost = cost;
    }

    public static BookHintPage fromJson(ResourceLocation entryId, JsonObject json, HolderLookup.Provider provider) {
        var title = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
        var useMarkdownInTitle = GsonHelper.getAsBoolean(json, "use_markdown_title", false);
        var showTitleSeparator = GsonHelper.getAsBoolean(json, "show_title_separator", true);
        var text = BookGsonHelper.getAsBookTextHolder(json, "text", BookTextHolder.EMPTY, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(entryId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();
        var completionAdvancement = ResourceLocation.tryParse(GsonHelper.getAsString(json, "completion_advancement"));
		IngredientStack cost = IngredientStack.EMPTY;
        if (json.has("cost")) {
            var ingredient = GsonHelper.getAsJsonObject(json, "cost");
			cost = IngredientStack.CODEC.parse(provider.createSerializationContext(JsonOps.INSTANCE), ingredient).result().orElse(cost);
        }
        return new BookHintPage(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition, completionAdvancement, cost);
    }

    public static BookHintPage fromNetwork(RegistryFriendlyByteBuf buffer) {
        var title = BookTextHolder.fromNetwork(buffer);
        var useMarkdownInTitle = buffer.readBoolean();
        var showTitleSeparator = buffer.readBoolean();
        var text = BookTextHolder.fromNetwork(buffer);
        var anchor = buffer.readUtf();
        var condition = BookCondition.fromNetwork(buffer);
        var completionAdvancement = buffer.readResourceLocation();
		var cost = IngredientStack.PACKET_CODEC.decode(buffer);
        return new BookHintPage(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition, completionAdvancement, cost);
    }

    public ResourceLocation getCompletionAdvancement() {
        return completionAdvancement;
    }
	
	public IngredientStack getCost() {
        return cost;
    }

    @Override
    public ResourceLocation getType() {
        return ModonomiconCompat.HINT_PAGE;
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeResourceLocation(completionAdvancement);
		IngredientStack.PACKET_CODEC.encode(buffer, cost);
    }

}

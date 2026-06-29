package earth.terrarium.pastel.compat.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookTextPage;
import com.klikli_dev.modonomicon.util.BookGsonHelper;
import com.mojang.serialization.JsonOps;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.Optional;

public class BookHintPage extends BookTextPage {

    private final ResourceLocation completionAdvancement;

    private final Optional<SizedIngredient> cost;

    public BookHintPage(
        BookTextHolder title,
        BookTextHolder text,
        boolean useMarkdownInTitle,
        boolean showTitleSeparator,
        String anchor,
        BookCondition condition,
        ResourceLocation completionAdvancement,
        Optional<SizedIngredient> cost
    ) {
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
        Optional<SizedIngredient> cost = Optional.empty();
        if (json.has("cost")) {
            var ingredient = GsonHelper.getAsJsonObject(json, "cost");
            // flat codec SHOULD:tm: be the same as our current ingredientstack codec
            // NOTE: I'm only ok with using FLAT_CODEC here because we're eventually nuking this class upon
            // upgrade to 2.0 anyway
            cost = SizedIngredient.FLAT_CODEC
                .parse(provider.createSerializationContext(JsonOps.INSTANCE), ingredient)
                .result();
        }
        return new BookHintPage(
            title,
            text,
            useMarkdownInTitle,
            showTitleSeparator,
            anchor,
            condition,
            completionAdvancement,
            cost
        );
    }

    public static BookHintPage fromNetwork(RegistryFriendlyByteBuf buffer) {
        var title = BookTextHolder.fromNetwork(buffer);
        var useMarkdownInTitle = buffer.readBoolean();
        var showTitleSeparator = buffer.readBoolean();
        var text = BookTextHolder.fromNetwork(buffer);
        var anchor = buffer.readUtf();
        var condition = BookCondition.fromNetwork(buffer);
        var completionAdvancement = buffer.readResourceLocation();
        var cost = ByteBufCodecs.optional(SizedIngredient.STREAM_CODEC).decode(buffer);
        return new BookHintPage(
            title,
            text,
            useMarkdownInTitle,
            showTitleSeparator,
            anchor,
            condition,
            completionAdvancement,
            cost
        );
    }

    public ResourceLocation getCompletionAdvancement() {
        return completionAdvancement;
    }

    public Optional<SizedIngredient> getCost() {
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
        ByteBufCodecs.optional(SizedIngredient.STREAM_CODEC).encode(buffer, cost);
    }

}

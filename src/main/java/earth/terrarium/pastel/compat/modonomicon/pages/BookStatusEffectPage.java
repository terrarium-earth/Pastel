package earth.terrarium.pastel.compat.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookTextPage;
import com.klikli_dev.modonomicon.client.gui.book.markdown.BookTextRenderer;
import com.klikli_dev.modonomicon.util.BookGsonHelper;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class BookStatusEffectPage extends BookTextPage {

    private final ResourceLocation statusEffectId;

    public BookStatusEffectPage(BookTextHolder title, BookTextHolder text, boolean useMarkdownInTitle, boolean showTitleSeparator, String anchor, BookCondition condition, ResourceLocation statusEffectId) {
        super(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition);
        this.statusEffectId = statusEffectId;
    }

    public static BookStatusEffectPage fromJson(ResourceLocation entryId, JsonObject json, HolderLookup.Provider provider) {
        var title = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
        var useMarkdownInTitle = GsonHelper.getAsBoolean(json, "use_markdown_title", false);
        var showTitleSeparator = GsonHelper.getAsBoolean(json, "show_title_separator", true);
        var text = BookGsonHelper.getAsBookTextHolder(json, "text", BookTextHolder.EMPTY, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(entryId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();
        var statusEffectId = json.has("status_effect_id") ? ResourceLocation.tryParse(GsonHelper.getAsString(json, "status_effect_id")) : null;
        return new BookStatusEffectPage(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition, statusEffectId);
    }

    public static BookStatusEffectPage fromNetwork(RegistryFriendlyByteBuf buffer) {
        var title = BookTextHolder.fromNetwork(buffer);
        var useMarkdownInTitle = buffer.readBoolean();
        var showTitleSeparator = buffer.readBoolean();
        var text = BookTextHolder.fromNetwork(buffer);
        var anchor = buffer.readUtf();
        var condition = BookCondition.fromNetwork(buffer);
        var statusEffectId = buffer.readResourceLocation();
        return new BookStatusEffectPage(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition, statusEffectId);
    }

    @Override
    public ResourceLocation getType() {
        return ModonomiconCompat.STATUS_EFFECT_PAGE;
    }

    public ResourceLocation getStatusEffectId() {
        return this.statusEffectId;
    }

    @Override
    public void prerenderMarkdown(BookTextRenderer textRenderer) {
        if (title.isEmpty()) {
            title = new BookTextHolder(statusEffectId.toLanguageKey("effect"));
        }

        super.prerenderMarkdown(textRenderer);
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeResourceLocation(this.statusEffectId);
    }

}

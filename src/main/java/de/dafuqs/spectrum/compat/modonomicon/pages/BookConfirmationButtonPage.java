package de.dafuqs.spectrum.compat.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookTextPage;
import com.klikli_dev.modonomicon.client.gui.book.markdown.BookTextRenderer;
import com.klikli_dev.modonomicon.util.BookGsonHelper;
import de.dafuqs.spectrum.compat.modonomicon.ModonomiconCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class BookConfirmationButtonPage extends BookTextPage {

    private final ResourceLocation checkedAdvancement;
    private BookTextHolder buttonText;
    private BookTextHolder confirmedButtonText;
    private final String confirmationString;

    public BookConfirmationButtonPage(BookTextHolder title, BookTextHolder text, boolean useMarkdownInTitle, boolean showTitleSeparator, String anchor, BookCondition condition, ResourceLocation checkedAdvancement, BookTextHolder buttonText, BookTextHolder confirmedButtonText, String confirmationString) {
        super(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition);
        this.checkedAdvancement = checkedAdvancement;
        this.buttonText = buttonText;
        this.confirmedButtonText = confirmedButtonText;
        this.confirmationString = confirmationString;
    }

    public static BookConfirmationButtonPage fromJson(ResourceLocation entryId, JsonObject json, HolderLookup.Provider provider) {
        var title = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
        var useMarkdownInTitle = GsonHelper.getAsBoolean(json, "use_markdown_title", false);
        var showTitleSeparator = GsonHelper.getAsBoolean(json, "show_title_separator", true);
        var text = BookGsonHelper.getAsBookTextHolder(json, "text", BookTextHolder.EMPTY, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
                ? BookCondition.fromJson(entryId, json.getAsJsonObject("condition"), provider)
                : new BookNoneCondition();
        var checkedAdvancement = ResourceLocation.tryParse(GsonHelper.getAsString(json, "checked_advancement"));
        var buttonText = BookGsonHelper.getAsBookTextHolder(json, "button_text", BookTextHolder.EMPTY, provider);
        var confirmedButtonText = BookGsonHelper.getAsBookTextHolder(json, "button_text_confirmed", BookTextHolder.EMPTY, provider);
        var confirmationString = GsonHelper.getAsString(json, "confirmation", "");
        return new BookConfirmationButtonPage(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition, checkedAdvancement, buttonText, confirmedButtonText, confirmationString);
    }

    public static BookConfirmationButtonPage fromNetwork(RegistryFriendlyByteBuf buffer) {
        var title = BookTextHolder.fromNetwork(buffer);
        var useMarkdownInTitle = buffer.readBoolean();
        var showTitleSeparator = buffer.readBoolean();
        var text = BookTextHolder.fromNetwork(buffer);
        var anchor = buffer.readUtf();
        var condition = BookCondition.fromNetwork(buffer);
        var checkedAdvancement = buffer.readResourceLocation();
        var buttonText = BookTextHolder.fromNetwork(buffer);
        var confirmedButtonText = BookTextHolder.fromNetwork(buffer);
        var confirmationString = buffer.readUtf();
        return new BookConfirmationButtonPage(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition, checkedAdvancement, buttonText, confirmedButtonText, confirmationString);
    }

    @Override
    public void prerenderMarkdown(BookTextRenderer textRenderer) {
        super.prerenderMarkdown(textRenderer);

        if (!buttonText.hasComponent()) {
            buttonText = new BookTextHolder(Component.translatable(buttonText.getKey()));
        }

        if (!confirmedButtonText.hasComponent()) {
            confirmedButtonText = new BookTextHolder(Component.translatable(confirmedButtonText.getKey()));
        }
    }

    public ResourceLocation getCheckedAdvancement() {
        return checkedAdvancement;
    }

    public BookTextHolder getButtonText() {
        return buttonText;
    }

    public BookTextHolder getConfirmedButtonText() {
        return confirmedButtonText;
    }

    public String getConfirmationString() {
        return confirmationString;
    }

    @Override
    public ResourceLocation getType() {
        return ModonomiconCompat.CONFIRMATION_BUTTON_PAGE;
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeResourceLocation(checkedAdvancement);
        buttonText.toNetwork(buffer);
        confirmedButtonText.toNetwork(buffer);
        buffer.writeUtf(confirmationString);
    }

}

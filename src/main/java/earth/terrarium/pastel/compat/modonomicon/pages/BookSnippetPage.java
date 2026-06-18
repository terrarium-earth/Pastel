package earth.terrarium.pastel.compat.modonomicon.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.conditions.BookCondition;
import com.klikli_dev.modonomicon.book.conditions.BookNoneCondition;
import com.klikli_dev.modonomicon.book.page.BookTextPage;
import com.klikli_dev.modonomicon.util.BookGsonHelper;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconCompat;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class BookSnippetPage extends BookTextPage {

    private final ResourceLocation resourcePath;

    private final int resourceWidth;

    private final int resourceHeight;

    private final int textureX;

    private final int textureY;

    private final int textureWidth;

    private final int textureHeight;

    public BookSnippetPage(
        BookTextHolder title,
        BookTextHolder text,
        boolean useMarkdownInTitle,
        boolean showTitleSeparator,
        String anchor,
        BookCondition condition,
        ResourceLocation resourcePath,
        int resourceWidth,
        int resourceHeight,
        int textureX,
        int textureY,
        int textureWidth,
        int textureHeight
    ) {
        super(title, text, useMarkdownInTitle, showTitleSeparator, anchor, condition);
        this.resourcePath = resourcePath;
        this.resourceWidth = resourceWidth;
        this.resourceHeight = resourceHeight;
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public static BookSnippetPage fromJson(ResourceLocation entryId, JsonObject json, HolderLookup.Provider provider) {
        var title = BookGsonHelper.getAsBookTextHolder(json, "title", BookTextHolder.EMPTY, provider);
        var useMarkdownInTitle = GsonHelper.getAsBoolean(json, "use_markdown_title", false);
        var showTitleSeparator = GsonHelper.getAsBoolean(json, "show_title_separator", false);
        var text = BookGsonHelper.getAsBookTextHolder(json, "text", BookTextHolder.EMPTY, provider);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        var condition = json.has("condition")
            ? BookCondition.fromJson(entryId, json.getAsJsonObject("condition"), provider)
            : new BookNoneCondition();
        var resourcePath = ResourceLocation.tryParse(GsonHelper.getAsString(json, "resource_path"));
        var resourceWidth = GsonHelper.getAsInt(json, "resource_width");
        var resourceHeight = GsonHelper.getAsInt(json, "resource_height");
        var textureX = GsonHelper.getAsInt(json, "texture_x");
        var textureY = GsonHelper.getAsInt(json, "texture_y");
        var textureWidth = GsonHelper.getAsInt(json, "texture_width");
        var textureHeight = GsonHelper.getAsInt(json, "texture_height");
        return new BookSnippetPage(
            title,
            text,
            useMarkdownInTitle,
            showTitleSeparator,
            anchor,
            condition,
            resourcePath,
            resourceWidth,
            resourceHeight,
            textureX,
            textureY,
            textureWidth,
            textureHeight
        );
    }

    public static BookSnippetPage fromNetwork(RegistryFriendlyByteBuf buffer) {
        var title = BookTextHolder.fromNetwork(buffer);
        var useMarkdownInTitle = buffer.readBoolean();
        var showTitleSeparator = buffer.readBoolean();
        var text = BookTextHolder.fromNetwork(buffer);
        var anchor = buffer.readUtf();
        var condition = BookCondition.fromNetwork(buffer);
        var resourcePath = buffer.readResourceLocation();
        var resourceWidth = buffer.readVarInt();
        var resourceHeight = buffer.readVarInt();
        var textureX = buffer.readVarInt();
        var textureY = buffer.readVarInt();
        var textureWidth = buffer.readVarInt();
        var textureHeight = buffer.readVarInt();
        return new BookSnippetPage(
            title,
            text,
            useMarkdownInTitle,
            showTitleSeparator,
            anchor,
            condition,
            resourcePath,
            resourceWidth,
            resourceHeight,
            textureX,
            textureY,
            textureWidth,
            textureHeight
        );
    }

    public ResourceLocation getResourcePath() {
        return resourcePath;
    }

    public int getResourceWidth() {
        return resourceWidth;
    }

    public int getResourceHeight() {
        return resourceHeight;
    }

    public int getTextureX() {
        return textureX;
    }

    public int getTextureY() {
        return textureY;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    @Override
    public ResourceLocation getType() {
        return ModonomiconCompat.SNIPPET_PAGE;
    }

    @Override
    public void toNetwork(RegistryFriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeResourceLocation(resourcePath);
        buffer.writeVarInt(resourceWidth);
        buffer.writeVarInt(resourceHeight);
        buffer.writeVarInt(textureX);
        buffer.writeVarInt(textureY);
        buffer.writeVarInt(textureWidth);
        buffer.writeVarInt(textureHeight);
    }

}

package de.dafuqs.spectrum.compat.modonomicon.page_types;

import com.google.gson.*;
import com.klikli_dev.modonomicon.book.entries.*;
import com.klikli_dev.modonomicon.client.gui.book.*;
import de.dafuqs.spectrum.compat.modonomicon.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.network.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

/**
 * A node that is a link to another existing entry
 * similar to com.klikli_dev.modonomicon.book.entries.CategoryLinkBookEntry,
 * but for entries, instead of a category
 */
public class WebLinkEntry extends BookEntry {
	
	private final String url;
	
	public WebLinkEntry(Identifier id, BookEntryData data, Identifier commandToRunOnFirstReadId, String url) {
		super(id, data, commandToRunOnFirstReadId);
		this.url = url;
	}
	
	@Override
	public Identifier getType() {
		return ModonomiconCompat.WEB_LINK_ENTRY_TYPE;
	}
	
	@Override
	public void openEntry(BookAddress bookAddress) {
		ConfirmLinkScreen.open(MinecraftClient.getInstance().currentScreen, url, false);
	}
	
	@Override
	public boolean matchesQuery(String query) {
		return false;
	}
	
	@Override
	public void toNetwork(RegistryByteBuf buffer) {
		buffer.writeIdentifier(this.id);
		this.data.toNetwork(buffer);
		buffer.writeNullable(this.commandToRunOnFirstReadId, PacketByteBuf::writeIdentifier);
		buffer.writeString(url);
	}
	
	public static WebLinkEntry fromNetwork(RegistryByteBuf buffer) {
		var id = buffer.readIdentifier();
		BookEntryData data = BookEntryData.fromNetwork(buffer);
		Identifier commandToRunOnFirstReadId = buffer.readNullable(PacketByteBuf::readIdentifier);
		var url = buffer.readString();
		
		return new WebLinkEntry(id, data, commandToRunOnFirstReadId, url);
	}
	
	public static WebLinkEntry fromJson(Identifier id, JsonObject json, boolean autoAddReadConditions, RegistryWrapper.WrapperLookup wrapperLookup) {
		BookEntry.BookEntryData data = BookEntryData.fromJson(id, json, autoAddReadConditions, wrapperLookup);
		Identifier commandToRunOnFirstReadId = null;
		if (json.has("command_to_run_on_first_read")) {
			commandToRunOnFirstReadId = Identifier.of(JsonHelper.getString(json, "command_to_run_on_first_read"));
		}
		String url = JsonHelper.getString(json, "url");
		return new WebLinkEntry(id, data, commandToRunOnFirstReadId, url);
	}
}
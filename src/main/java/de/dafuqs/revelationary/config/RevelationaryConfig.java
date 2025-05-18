package de.dafuqs.revelationary.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.dafuqs.revelationary.Revelationary;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RevelationaryConfig {
	private static final File CONFIG_FILE_PATH = new File(FMLPaths.CONFIGDIR.get().toFile(), "Revelationary.json");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private static Config CONFIG = null;

	public static Config get() {
		if(CONFIG == null) {
			CONFIG = Config.load();
		}
		return CONFIG;
	}

	public static class Config {
		public boolean PreventMiningOfUnrevealedBlocks = false;
		public boolean UseTargetBlockOrItemNameInsteadOfScatter = false;
		public String NameForUnrevealedBlocks = "";
		public String NameForUnrevealedItems = "";
		public boolean HideCloakedEntriesFromRecipeViewers = false;

		public Config() { }

		private static Config load() {
			if (!CONFIG_FILE_PATH.exists()) {
				try {
					CONFIG_FILE_PATH.createNewFile();
					Config config = new Config();
					config.save();
					return config;
				} catch (IOException e) {
					Revelationary.logError("Could not generate config file under " + CONFIG_FILE_PATH.getAbsolutePath() + ".\n" + e.getLocalizedMessage());
				}
			}

			try {
				return GSON.fromJson(new FileReader(CONFIG_FILE_PATH), Config.class);
			} catch (FileNotFoundException e) {
				Revelationary.logError("Could not load config file under " + CONFIG_FILE_PATH.getAbsolutePath() + ".\n" + e.getLocalizedMessage());
			}

			return new Config();
		}

		private void save() {
			try {
				FileWriter writer = new FileWriter(CONFIG_FILE_PATH);
				GSON.toJson(this, writer);
				writer.close();
			} catch (IOException e) {
				Revelationary.logError("Could not save config file under " + CONFIG_FILE_PATH.getAbsolutePath() + ".\n" + e.getLocalizedMessage());
			}
		}

	}
}
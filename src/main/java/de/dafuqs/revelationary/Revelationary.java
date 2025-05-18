package de.dafuqs.revelationary;

import de.dafuqs.revelationary.api.advancements.AdvancementCriteria;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Revelationary {
    public static final String MOD_ID = "revelationary";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void logInfo(String message) {
        LOGGER.info("[Revelationary] {}", message);
    }

    public static void logWarning(String message) {
        LOGGER.warn("[Revelationary] {}", message);
    }

    public static void logError(String message) {
        LOGGER.error("[Revelationary] {}", message);
    }
    public static void logException(Throwable t) {
        LOGGER.error("[Revelationary] ", t);
    }

    public static void onInitialize() {
        logInfo("Starting Common Startup");

        RevelationaryNetworking.register();

        AdvancementCriteria.register();
        CommandRegistrationCallback.EVENT.register(Commands::register);
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(RevelationDataLoader.INSTANCE);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            RevelationRegistry.addRevelationAwares();
            RevelationRegistry.deepTrim();
        });
        if (FabricLoader.getInstance().isModLoaded("sodium")) {
            logWarning("Sodium detected. Chunk rebuilding will be done in cursed mode.");
        }

        logInfo("Common startup completed!");
    }
}

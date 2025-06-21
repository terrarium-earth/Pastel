package de.dafuqs.revelationary;

import de.dafuqs.revelationary.api.advancements.AdvancementCriteria;
import earth.terrarium.pastel.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Revelationary {
    public static final String MOD_ID = PastelCommon.MOD_ID;
    private static final Logger LOGGER = LoggerFactory.getLogger("Revelationary");

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

    private static void onRegisterCommands(RegisterCommandsEvent event) {
        Commands.register(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }

    private static void onServerStarted(ServerStartedEvent event) {
        RevelationRegistry.addRevelationAwares();
        RevelationRegistry.deepTrim();
    }

    private static void onDataReload(AddReloadListenerEvent event) {
        event.addListener(RevelationDataLoader.INSTANCE);
    }

    public static void onInitialize(IEventBus modEventBus) {
        logInfo("Starting Common Startup");

        AdvancementCriteria.register(modEventBus);
        NeoForge.EVENT_BUS.addListener(Revelationary::onRegisterCommands);
        NeoForge.EVENT_BUS.addListener(Revelationary::onDataReload);
        NeoForge.EVENT_BUS.addListener(Revelationary::onServerStarted);

        if (ModList.get().isLoaded("sodium")) {
            logWarning("Sodium detected. Chunk rebuilding will be done in cursed mode.");
        }

        logInfo("Common startup completed!");
    }
}

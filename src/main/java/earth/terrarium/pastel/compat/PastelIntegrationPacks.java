package earth.terrarium.pastel.compat;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.ae2.AE2Compat;
import earth.terrarium.pastel.compat.botania.BotaniaCompat;
import earth.terrarium.pastel.compat.create.CreateCompat;
import earth.terrarium.pastel.compat.exclusions_lib.ExclusionsLibCompat;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconCompat;
import earth.terrarium.pastel.compat.travelersbackpack.TravelersBackpackCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PastelIntegrationPacks {

    protected static final Map<String, ModIntegrationPack> INTEGRATION_PACKS = new HashMap<>();


    public abstract static class ModIntegrationPack {
        public abstract void register();

        public abstract void registerClient();
    }

    protected static void registerIntegrationPack(String modId, Supplier<ModIntegrationPack> container) {
        if (!PastelCommon.CONFIG.IntegrationPacksToSkipLoading.contains(modId) && ModList.get()
                                                                                         .isLoaded(modId)) {
            var pack = container.get();

            INTEGRATION_PACKS.put(modId, pack);
            pack.register();
        }
    }

    public static final String AE2_ID = "ae2";
    public static final String TRAVELERS_BACKPACK_ID = "travelersbackpack";
    public static final String BOTANIA_ID = "botania";
    public static final String MODONOMICON_ID = "modonomicon";
    public static final String MALUM_ID = "malum";
    public static final String CREATE_ID = "create";
    public static final String FARMERSDELIGHT_ID = "farmersdelight";
    public static final String EXCLUSIONS_LIB_ID = "exclusions_lib";

    @SuppressWarnings("Convert2MethodRef")
    public static void register(IEventBus bus) {
        registerIntegrationPack(MODONOMICON_ID, () -> new ModonomiconCompat());

        if (!ModList.get()
                    .isLoaded(EXCLUSIONS_LIB_ID)) {
            ExclusionsLibCompat.registerNotPresent(bus);
        }

        registerIntegrationPack(AE2_ID, () -> new AE2Compat());
        //registerIntegrationPack(BOTANIA_ID, () -> new BotaniaCompat());
        //registerIntegrationPack(FARMERSDELIGHT_ID, () -> new FDCompat());
        registerIntegrationPack(CREATE_ID, () -> new CreateCompat());
        bus.addListener((FMLCommonSetupEvent event) -> {
            registerIntegrationPack(TRAVELERS_BACKPACK_ID, () -> new TravelersBackpackCompat());
        });
    }

    public static void registerClient() {
        for (ModIntegrationPack container : INTEGRATION_PACKS.values()) {
            container.registerClient();
        }
    }

    public static boolean isIntegrationPackActive(String modId) {
        return INTEGRATION_PACKS.containsKey(modId);
    }

}

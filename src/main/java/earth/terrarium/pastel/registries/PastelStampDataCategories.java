package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.item.StampDataCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings(
    "unused"
)
public class PastelStampDataCategories {

    private static final DeferredRegister<StampDataCategory> REGISTER = DeferredRegister
        .create(
            PastelRegistryKeys.STAMP_DATA_CATEGORY,
            PastelCommon.MOD_ID
        );

    public static StampDataCategory UNIQUE = StampDataCategory.UNIQUE;

    public static StampDataCategory PASTEL = register("pastel");

    public static void register(IEventBus bus) {
        REGISTER
            .register(
                StampDataCategory.UNIQUE
                    .getId()
                    .getPath(),
                () -> StampDataCategory.UNIQUE
            );
        REGISTER.register(bus);
    }

    private static StampDataCategory register(String name) {
        var cat = StampDataCategory.create(PastelCommon.locate(name));
        REGISTER.register(name, () -> cat);
        return cat;
    }
}

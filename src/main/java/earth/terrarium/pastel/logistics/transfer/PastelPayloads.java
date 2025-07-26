package earth.terrarium.pastel.logistics.transfer;


import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.logistics.api.Payload;
import earth.terrarium.pastel.registries.PastelRegistryKeys;
import net.minecraft.core.Holder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PastelPayloads {

    public static final DeferredRegister<Payload<?, ?, ?>> REGISTER = DeferredRegister.create(PastelRegistryKeys.LOGISTIC_PAYLOAD,
                                                                                              PastelCommon.MOD_ID
    );

    public static final DeferredHolder<Payload<?, ?, ?>, ItemPayload> ITEM_PAYLOAD =
        REGISTER.register("item_payload", ItemPayload::new);

    public static void register(IEventBus modBus) {
        REGISTER.register(modBus);
    }
}

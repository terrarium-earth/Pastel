package earth.terrarium.pastel.registries;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.data_loaders.resonance_processors.DropSelfResonanceProcessor;
import earth.terrarium.pastel.data_loaders.resonance_processors.ModifyDropsResonanceProcessor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PastelResonanceProcessorTypes {

    private static final DeferredRegister<MapCodec<? extends ResonanceProcessor>> REGISTER = DeferredRegister.create(
        PastelRegistries.RESONANCE_PROCESSOR_TYPE, PastelCommon.MOD_ID);

    public static void register(String id, MapCodec<? extends ResonanceProcessor> target) {
        REGISTER.register(id, () -> target);
    }

    public static void register(IEventBus bus) {
        register("drop_self", DropSelfResonanceProcessor.CODEC);
        register("modify_drops", ModifyDropsResonanceProcessor.CODEC);
        REGISTER.register(bus);
    }

}

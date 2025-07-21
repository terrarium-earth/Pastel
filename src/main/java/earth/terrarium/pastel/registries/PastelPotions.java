package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PastelPotions {

    public static Holder<Potion> PIGMENT_POTION;

    public static void register(IEventBus bus) {
        var registry = DeferredRegister.create(Registries.POTION, PastelCommon.MOD_ID);

        PIGMENT_POTION = registry.register("pigment_potion", () -> new Potion());

        registry.register(bus);
    }

}

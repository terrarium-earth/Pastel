package earth.terrarium.pastel.loot;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.loot.modifiers.NightArcheologyModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class PastelLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> REGISTER =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, PastelCommon.MOD_ID);

    public static final Supplier<MapCodec<NightArcheologyModifier>> NIGHT_ARCHEOLOGY = REGISTER.register("night_archeology", () -> NightArcheologyModifier.CODEC);

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }
}

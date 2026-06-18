package earth.terrarium.pastel.loot;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.loot.modifiers.NightArcheologyModifier;
import earth.terrarium.pastel.loot.modifiers.TreasureHunterModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PastelLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> REGISTER = DeferredRegister
        .create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, PastelCommon.MOD_ID);

    public static void register(IEventBus bus) {
        REGISTER.register("night_archeology", () -> NightArcheologyModifier.CODEC);
        REGISTER.register("treasure_hunter", () -> TreasureHunterModifier.CODEC);

        REGISTER.register(bus);
    }
}

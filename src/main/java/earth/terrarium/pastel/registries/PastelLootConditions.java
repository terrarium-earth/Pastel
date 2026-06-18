package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.loot.conditions.NearMoonstoneLootCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class PastelLootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister
        .create(Registries.LOOT_CONDITION_TYPE, PastelCommon.MOD_ID);

    public static final Supplier<LootItemConditionType> NEAR_MOONSTONE = LOOT_CONDITION_TYPES
        .register("near_moonstone", () -> new LootItemConditionType(NearMoonstoneLootCondition.CODEC));

    public static void register(IEventBus bus) {
        LOOT_CONDITION_TYPES.register(bus);
    }
}

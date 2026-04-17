package earth.terrarium.pastel.registries;

import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.loot.conditions.NearBlockLootCondition;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.function.Supplier;

public class PastelLootConditions {
    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES =
        DeferredRegister.create(PastelCommon.MOD_ID,Registries.LOOT_CONDITION_TYPE);

    public static final Supplier<LootItemConditionType> NEAR_BLOCK =
        LOOT_CONDITION_TYPES.register("near_block", () -> new LootItemConditionType(NearBlockLootCondition.CODEC));
}

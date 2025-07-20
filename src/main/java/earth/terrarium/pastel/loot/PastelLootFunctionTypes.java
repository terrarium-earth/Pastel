package earth.terrarium.pastel.loot;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.loot.functions.DyeRandomlyLootFunction;
import earth.terrarium.pastel.loot.functions.FermentRandomlyLootFunction;
import earth.terrarium.pastel.loot.functions.FillPotionFillableLootFunction;
import earth.terrarium.pastel.loot.functions.GrantAdvancementLootFunction;
import earth.terrarium.pastel.loot.functions.SetComponentsRandomlyLootFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PastelLootFunctionTypes {

    private static final DeferredRegister<LootItemFunctionType<?>> REGISTRAR = DeferredRegister.create(
        Registries.LOOT_FUNCTION_TYPE, PastelCommon.MOD_ID);

    public static final LootItemFunctionType<DyeRandomlyLootFunction> DYE_RANDOMLY = register(
        "dye_randomly", DyeRandomlyLootFunction.CODEC);
    public static final LootItemFunctionType<FermentRandomlyLootFunction> FERMENT_RANDOMLY = register(
        "ferment_randomly", FermentRandomlyLootFunction.CODEC);
    public static final LootItemFunctionType<SetComponentsRandomlyLootFunction> SET_COMPONENTS_RANDOMLY = register(
        "set_components_randomly", SetComponentsRandomlyLootFunction.CODEC);
    public static final LootItemFunctionType<FillPotionFillableLootFunction> FILL_POTION_FILLABLE = register(
        "fill_potion_fillable", FillPotionFillableLootFunction.CODEC);
    public static final LootItemFunctionType<GrantAdvancementLootFunction> GRANT_ADVANCEMENT = register(
        "grant_advancement", GrantAdvancementLootFunction.CODEC);

    private static <T extends LootItemFunction> LootItemFunctionType<T> register(String id, MapCodec<T> codec) {
        var tLootItemFunctionType = new LootItemFunctionType<>(codec);
        REGISTRAR.register(id, () -> tLootItemFunctionType);
        return tLootItemFunctionType;
    }

    public static void register(IEventBus bus) {
        REGISTRAR.register(bus);
    }

}

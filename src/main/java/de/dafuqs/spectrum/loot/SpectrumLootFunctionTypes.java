package de.dafuqs.spectrum.loot;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.loot.functions.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.loot.function.*;
import net.minecraft.registry.*;

public class SpectrumLootFunctionTypes {
	
	private static final DeferredRegistrar REGISTRAR = new DeferredRegistrar();
	
	public static final LootFunctionType<DyeRandomlyLootFunction> DYE_RANDOMLY = register("dye_randomly", DyeRandomlyLootFunction.CODEC);
	public static final LootFunctionType<FermentRandomlyLootFunction> FERMENT_RANDOMLY = register("ferment_randomly", FermentRandomlyLootFunction.CODEC);
	public static final LootFunctionType<SetComponentsRandomlyLootFunction> SET_COMPONENTS_RANDOMLY = register("set_components_randomly", SetComponentsRandomlyLootFunction.CODEC);
	public static final LootFunctionType<FillPotionFillableLootFunction> FILL_POTION_FILLABLE = register("fill_potion_fillable", FillPotionFillableLootFunction.CODEC);
	public static final LootFunctionType<GrantAdvancementLootFunction> GRANT_ADVANCEMENT = register("grant_advancement", GrantAdvancementLootFunction.CODEC);
	
	private static <T extends LootFunction> LootFunctionType<T> register(String id, MapCodec<T> codec) {
		return REGISTRAR.defer(new LootFunctionType<>(codec), type -> Registry.register(Registries.LOOT_FUNCTION_TYPE, SpectrumCommon.locate(id), type));
	}
	
	public static void register() {
		REGISTRAR.flush();
	}
	
}

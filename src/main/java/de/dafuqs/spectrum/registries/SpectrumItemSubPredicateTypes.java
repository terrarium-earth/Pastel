package de.dafuqs.spectrum.registries;

import com.mojang.serialization.Codec;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.predicate.item.BottomlessStackPredicate;
import de.dafuqs.spectrum.api.predicate.item.InfusedBeveragePredicate;
import de.dafuqs.spectrum.api.predicate.item.SweetenedPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

@SuppressWarnings("unused")
public class SpectrumItemSubPredicateTypes {
	
	private static final DeferredRegistrar REGISTRAR = new DeferredRegistrar();
	
	public static ItemSubPredicate.Type<SweetenedPredicate> SWEETENED = register("sweetened", SweetenedPredicate.CODEC);
	public static ItemSubPredicate.Type<InfusedBeveragePredicate> INFUSED_BEVERAGE = register("infused_beverage", InfusedBeveragePredicate.CODEC);
	public static ItemSubPredicate.Type<BottomlessStackPredicate> BOTTOMLESS_STACK = register("bottomless_stack", BottomlessStackPredicate.CODEC);
	
	private static <T extends ItemSubPredicate> ItemSubPredicate.Type<T> register(String id, Codec<T> codec) {
		return REGISTRAR.defer(new ItemSubPredicate.Type<>(codec), type -> Registry.register(BuiltInRegistries.ITEM_SUB_PREDICATE_TYPE, SpectrumCommon.locate(id), type));
	}
	
	public static void register() {
		REGISTRAR.flush();
	}
	
}

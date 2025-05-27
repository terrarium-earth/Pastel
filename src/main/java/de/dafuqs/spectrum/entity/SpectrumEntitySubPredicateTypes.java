package de.dafuqs.spectrum.entity;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.predicates.EggLayingWoolyPigPredicate;
import de.dafuqs.spectrum.entity.predicates.KindlingPredicate;
import de.dafuqs.spectrum.entity.predicates.LizardPredicate;
import de.dafuqs.spectrum.entity.predicates.ShulkerPredicate;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumEntitySubPredicateTypes {

	private static final DeferredRegister<MapCodec<? extends EntitySubPredicate>> REGISTER = DeferredRegister.create(Registries.ENTITY_SUB_PREDICATE_TYPE, SpectrumCommon.MOD_ID);

	public static final MapCodec<EggLayingWoolyPigPredicate> EGG_LAYING_WOOLY_PIG = EggLayingWoolyPigPredicate.CODEC;
	public static final MapCodec<ShulkerPredicate> SHULKER = ShulkerPredicate.CODEC;
	public static final MapCodec<KindlingPredicate> KINDLING = KindlingPredicate.CODEC;
	public static final MapCodec<LizardPredicate> LIZARD = LizardPredicate.CODEC;

	public static void register(IEventBus bus) {
		// EntitySubPredicateTypes are not handled via identifiers, but we'll add our mod id anyway,
		// in case of collisions with future vanilla updates or other mods
		REGISTER.register("egg_laying_wooly_pig", () -> EGG_LAYING_WOOLY_PIG);
		REGISTER.register("shulker", () -> SHULKER);
		REGISTER.register("kindling", () -> KINDLING);
		REGISTER.register("lizard", () -> LIZARD);
		REGISTER.register(bus);
	}
	
}

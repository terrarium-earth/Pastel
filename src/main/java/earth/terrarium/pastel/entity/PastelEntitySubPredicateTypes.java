package earth.terrarium.pastel.entity;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.predicates.EggLayingWoolyPigPredicate;
import earth.terrarium.pastel.entity.predicates.KindlingPredicate;
import earth.terrarium.pastel.entity.predicates.LizardPredicate;
import earth.terrarium.pastel.entity.predicates.ShulkerPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PastelEntitySubPredicateTypes {

    private static final DeferredRegister<MapCodec<? extends EntitySubPredicate>> REGISTER = DeferredRegister.create(
        Registries.ENTITY_SUB_PREDICATE_TYPE, PastelCommon.MOD_ID);

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

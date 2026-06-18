package earth.terrarium.pastel.events.game;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class PastelPositionSources {

    private static final DeferredRegister<PositionSourceType<?>> REGISTER = DeferredRegister
        .create(
            Registries.POSITION_SOURCE_TYPE,
            PastelCommon.MOD_ID
        );

    public static Holder<PositionSourceType<ExactPositionSource>> EXACT = register(
        "exact",
        ExactPositionSource.Type::new
    );

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }

    @SuppressWarnings(
        "unchecked"
    )
    static <S extends PositionSourceType<T>, T extends PositionSource> Holder<S> register(
        String id,
        Supplier<S> positionSourceType
    ) {
        return (Holder<S>) REGISTER.register(id, positionSourceType);
    }

}

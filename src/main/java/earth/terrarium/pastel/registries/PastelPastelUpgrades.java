package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.pastel.PastelUpgradeSignature;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class PastelPastelUpgrades {

    private static final DeferredRegister<PastelUpgradeSignature> REGISTER = DeferredRegister
        .create(
            PastelRegistryKeys.PASTEL_UPGRADE,
            PastelCommon.MOD_ID
        );

    private static final String NAMESPACE = PastelCommon.MOD_ID;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> WEAK_STACK;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> STRONG_STACK;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> WEAK_SPEED;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> STRONG_SPEED;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> WEAK_FILTER;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> STRONG_FILTER;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> RATE;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> LIGHT;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> ALWAYS_ON;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> ALWAYS_OFF;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> INVERTED;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> SENSOR;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> TRIGGER;

    public static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> LAMP;

    public static final PastelUpgradeSignature.Category NON_COMPOUNDING = PastelUpgradeSignature.Category
        .nonCompounding();

    public static final PastelUpgradeSignature.Category STACK = PastelUpgradeSignature.Category.simple();

    public static final PastelUpgradeSignature.Category SPEED = PastelUpgradeSignature.Category.simple();

    public static final PastelUpgradeSignature.Category FILTER = PastelUpgradeSignature.Category.simple();

    public static final PastelUpgradeSignature.Category REDSTONE = PastelUpgradeSignature.Category.redstone();

    public static void register(IEventBus bus) {
        WEAK_STACK = register(
            "weak_stack",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.RAW_BLOODSTONE.get(),
                    STACK,
                    NAMESPACE
                )
                .named("weak_stack")
                .stackMod(3)
                .stackMult(2)
                .build()
        );
        STRONG_STACK = register(
            "strong_stack",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.PURE_BLOODSTONE.get(),
                    STACK,
                    NAMESPACE
                )
                .named("strong_stack")
                .stackMod(15)
                .stackMult(4)
                .build()
        );

        WEAK_SPEED = register(
            "weak_speed",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.RAW_MALACHITE.get(),
                    SPEED,
                    NAMESPACE
                )
                .named("weak_speed")
                .speedMod(-5)
                .speedMult(0.8F)
                .build()
        );
        STRONG_SPEED = register(
            "strong_speed",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.PURE_MALACHITE.get(),
                    SPEED,
                    NAMESPACE
                )
                .named("strong_speed")
                .speedMod(-10)
                .speedMult(0.5F)
                .build()
        );

        WEAK_FILTER = register(
            "weak_filter",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.RAW_AZURITE.get(),
                    FILTER,
                    NAMESPACE
                )
                .named("weak_filter")
                .slotRowMod(1)
                .build()
        );
        STRONG_FILTER = register(
            "strong_filter",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.PURE_AZURITE.get(),
                    FILTER,
                    NAMESPACE
                )
                .named("strong_filter")
                .slotRowMod(2)
                .build()
        );

        RATE = register(
            "rate",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.RESONANCE_SHARD.get(),
                    NON_COMPOUNDING,
                    NAMESPACE
                )
                .named("rate")
                .priority(true)
                .build()
        );
        LIGHT = register(
            "light",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.SHIMMERSTONE_GEM.get(),
                    NON_COMPOUNDING,
                    NAMESPACE
                )
                .named("light")
                .light(true)
                .build()
        );

        ALWAYS_ON = register(
            "always_active",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.PURE_REDSTONE.get(),
                    REDSTONE,
                    NAMESPACE
                )
                .redstone("always_active")
                .redstonePreProcess(context -> InteractionResult.SUCCESS)
                .buildRedstone()
        );
        ALWAYS_OFF = register(
            "always_inactive",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.PURE_LAPIS.get(),
                    REDSTONE,
                    NAMESPACE
                )
                .redstone("always_inactive")
                .redstonePreProcess(context -> InteractionResult.FAIL)
                .buildRedstone()
        );

        INVERTED = register(
            "inverted",
            () -> PastelUpgradeSignature
                .builder(PastelItems.PURE_COAL.get(), REDSTONE, NAMESPACE)
                .redstone("inverted")
                .redstonePostProcess(context -> {
                    if (context.active())
                        return InteractionResult.FAIL;
                    return InteractionResult.SUCCESS;
                })
                .buildRedstone()
        );

        LAMP = register(
            "lamp",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.PURE_GLOWSTONE.get(),
                    REDSTONE,
                    NAMESPACE
                )
                .redstone("lamp")
                .lamp(true)
                .buildRedstone()
        );
        TRIGGER = register(
            "trigger",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.PURE_QUARTZ.get(),
                    REDSTONE,
                    NAMESPACE
                )
                .redstone("trigger")
                .triggerTransfer(true)
                .buildRedstone()
        );
        SENSOR = register(
            "sensor",
            () -> PastelUpgradeSignature
                .builder(
                    PastelItems.PURE_ECHO.get(),
                    REDSTONE,
                    NAMESPACE
                )
                .redstone("sensor")
                .sensor(true)
                .buildRedstone()
        );
        REGISTER.register(bus);
    }

    private static DeferredHolder<PastelUpgradeSignature, PastelUpgradeSignature> register(
        String name,
        Supplier<PastelUpgradeSignature> upgrade
    ) {
        return REGISTER.register(name, upgrade);
    }

    public static PastelUpgradeSignature of(Item item) {
        return PastelRegistries.PASTEL_UPGRADE
            .stream()
            .filter(upgrade -> upgrade.upgradeItem == item)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Attempted to fetch an upgrade that does not exist"));
    }

    public static String toString(PastelUpgradeSignature upgrade) {
        return PastelRegistries.PASTEL_UPGRADE
            .getKey(upgrade)
            .toString();
    }

    public static PastelUpgradeSignature of(ItemStack stack) {
        return of(stack.getItem());
    }
}

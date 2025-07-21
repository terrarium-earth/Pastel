package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.worldgen.tree_decorators.FrondsDecorator;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class PastelTreeDecoratorTypes {

    private static final DeferredRegister<TreeDecoratorType<?>> REGISTER = DeferredRegister.create(
        Registries.TREE_DECORATOR_TYPE, PastelCommon.MOD_ID);

    public static final Holder<TreeDecoratorType<?>> FRONDS = register("fronds", () -> new TreeDecoratorType<>(
        FrondsDecorator.CODEC)
    );

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }

    private static Holder<TreeDecoratorType<?>> register(String id, Supplier<TreeDecoratorType<?>> treeDecoratorType) {
        return REGISTER.register(id, treeDecoratorType);
    }

}

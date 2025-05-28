package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BundleContents.class)
public interface BundleContentsComponentAccessor {

    @Invoker
    static Fraction invokeGetWeight(ItemStack stack) {
        throw new AssertionError();
    }

}

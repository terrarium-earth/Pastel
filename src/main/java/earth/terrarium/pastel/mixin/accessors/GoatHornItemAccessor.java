package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(InstrumentItem.class)
public interface GoatHornItemAccessor {

    @Invoker
    Optional<Holder<Instrument>> invokeGetInstrument(ItemStack stack);

}

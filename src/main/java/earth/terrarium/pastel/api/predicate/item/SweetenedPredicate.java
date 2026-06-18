package earth.terrarium.pastel.api.predicate.item;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.components.JadeWineComponent;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public record SweetenedPredicate(boolean sweetened) implements SingleComponentItemPredicate<JadeWineComponent> {

    public static final Codec<SweetenedPredicate> CODEC = Codec.BOOL
        .xmap(
            SweetenedPredicate::new,
            SweetenedPredicate::sweetened
        );

    @Override
    public DataComponentType<JadeWineComponent> componentType() {
        return PastelDataComponentTypes.JADE_WINE;
    }

    @Override
    public boolean matches(ItemStack stack, JadeWineComponent component) {
        return component.sweetened() == sweetened;
    }

}

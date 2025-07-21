package earth.terrarium.pastel.api.render;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface SlotBackgroundEffect {

    SlotEffect backgroundType(@Nullable Player player, ItemStack stack);

    int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta);

    default float getEffectOpacity(@Nullable Player player, ItemStack stack, float tickDelta) {
        return 1F;
    }

    enum SlotEffect {
        PULSE,
        BORDER,
        BORDER_FADE,
        FULL_PACKAGE,
        NONE
    }
}

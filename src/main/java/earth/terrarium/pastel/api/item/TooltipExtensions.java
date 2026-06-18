package earth.terrarium.pastel.api.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface TooltipExtensions {

    default void expandTooltipPostStats(
        ItemStack stack,
        @Nullable Player player,
        List<Component> tooltip,
        Item.TooltipContext context
    ) {
    }

    default void appendTooltipWithPlayer(
        ItemStack stack,
        @Nullable Player player,
        List<Component> tooltip,
        Item.TooltipContext context
    ) {
    }

}

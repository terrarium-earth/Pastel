package earth.terrarium.pastel.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

// The neofeds ain't got shit on me
public interface AreaMiningHandler {

    /**
     * Z is depth
     */
    Vec3i getMiningArea(Player player, ItemStack stack, BlockPos pos);
}
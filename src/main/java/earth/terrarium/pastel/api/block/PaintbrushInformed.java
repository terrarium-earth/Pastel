package earth.terrarium.pastel.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface PaintbrushInformed {
    @Nullable Component getStatusBarInfo(Level level, BlockPos pos, Player player);
}

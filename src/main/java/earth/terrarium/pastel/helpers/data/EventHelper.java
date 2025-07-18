package earth.terrarium.pastel.helpers.data;

import earth.terrarium.pastel.blocks.redstone.RedstoneTransceiverBlock;
import earth.terrarium.pastel.blocks.redstone.RedstoneTransceiverBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class EventHelper {

    public static DyeColor getRedstoneEventDyeColor(GameEvent.ListenerInfo message) {
        return message.context()
                      .affectedState()
                      .getOptionalValue(RedstoneTransceiverBlock.CHANNEL)
                      .orElse(null);
    }

    public static int getRedstoneEventPower(Level world, GameEvent.ListenerInfo message) {
        var pos = message.source();
        var blockEntity = world.getBlockEntity(BlockPos.containing(pos.x, pos.y, pos.z));
        if (blockEntity instanceof RedstoneTransceiverBlockEntity transceiver) {
            return transceiver.getCurrentSignalStrength();
        }
        return 0;
    }

}

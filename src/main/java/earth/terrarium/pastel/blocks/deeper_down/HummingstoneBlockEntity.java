package earth.terrarium.pastel.blocks.deeper_down;

import earth.terrarium.pastel.events.game.PastelGameEvents;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class HummingstoneBlockEntity extends BlockEntity
    implements HummingstoneEventQueue.Callback<HummingstoneEventQueue.EventEntry> {

    private static final int RANGE = 8;
    protected final HummingstoneEventQueue listener;

    public HummingstoneBlockEntity(BlockPos pos, BlockState state) {
        super(PastelBlockEntities.HUMMINGSTONE.get(), pos, state);
        this.listener = new HummingstoneEventQueue(new BlockPositionSource(this.worldPosition), RANGE, this);
    }

    @SuppressWarnings("unused")
    public static void serverTick(
        @NotNull Level world, BlockPos pos, BlockState state, @NotNull HummingstoneBlockEntity blockEntity) {
        blockEntity.listener.tick(world);
    }

    @Override
    public boolean canAcceptEvent(
        Level world, GameEventListener listener, GameEvent.ListenerInfo message, Vec3 sourcePos) {
        return !this.isRemoved() && (message.gameEvent() == PastelGameEvents.HUMMINGSTONE_HYMN ||
                                     message.gameEvent() == PastelGameEvents.HUMMINGSTONE_HUMMING);
    }

    @Override
    public void triggerEvent(Level world, GameEventListener listener, HummingstoneEventQueue.EventEntry entry) {
        GameEvent.ListenerInfo message = entry.message();

        if (message.gameEvent() == PastelGameEvents.HUMMINGSTONE_HUMMING) {
            HummingstoneBlock.startHumming(
                world, this.worldPosition, world.getBlockState(this.worldPosition), message.context()
                                                                                           .sourceEntity(), true
            );
        } else if (message.gameEvent() == PastelGameEvents.HUMMINGSTONE_HYMN) {
            HummingstoneBlock.onHymn(
                world, this.worldPosition, message.context()
                                                  .sourceEntity()
            );
        }
    }

}

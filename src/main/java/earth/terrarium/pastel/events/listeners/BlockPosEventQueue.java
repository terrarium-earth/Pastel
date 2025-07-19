package earth.terrarium.pastel.events.listeners;

import earth.terrarium.pastel.networking.s2c_payloads.TypedTransmissionPayload;
import earth.terrarium.pastel.particle.effect.TypedTransmission;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class BlockPosEventQueue extends EventQueue<BlockPosEventQueue.EventEntry> {
	
	public BlockPosEventQueue(PositionSource positionSource, int range, Callback<EventEntry> listener) {
		super(positionSource, range, listener);
	}
	
	@Override
	public void acceptEvent(Level world, GameEvent.ListenerInfo event, Vec3 sourcePos) {
		if (world instanceof ServerLevel) {
			Vec3 emitterPos = event.source();
			EventEntry eventEntry = new EventEntry(event.gameEvent(), BlockPos.containing(emitterPos.x, emitterPos.y, emitterPos.z), Mth.floor(event.source().distanceTo(sourcePos)));
			int delay = eventEntry.distance * 2;
			this.schedule(eventEntry, delay);
			TypedTransmissionPayload.playTransmissionParticle((ServerLevel) world, new TypedTransmission(emitterPos, this.positionSource, delay, TypedTransmission.Variant.BLOCK_POS));
		}
	}
	
	public static class EventEntry {
		public final Holder<GameEvent> gameEvent;
		public final BlockPos eventSourceBlockPos;
		public final int distance;
		
		public EventEntry(Holder<GameEvent> gameEvent, BlockPos eventSourceBlockPos, int distance) {
			this.gameEvent = gameEvent;
			this.eventSourceBlockPos = eventSourceBlockPos;
			this.distance = distance;
		}
	}
	
}
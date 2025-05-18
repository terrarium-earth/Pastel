package de.dafuqs.spectrum.blocks.deeper_down;

import de.dafuqs.spectrum.events.SpectrumGameEvents;
import de.dafuqs.spectrum.events.listeners.EventQueue;
import de.dafuqs.spectrum.networking.s2c_payloads.TypedTransmissionPayload;
import de.dafuqs.spectrum.particle.effect.TypedTransmission;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class HummingstoneEventQueue extends EventQueue<HummingstoneEventQueue.EventEntry> {
    
    public HummingstoneEventQueue(PositionSource positionSource, int range, Callback<EventEntry> listener) {
        super(positionSource, range, listener);
    }
    
    @Override
    public void acceptEvent(Level world, GameEvent.ListenerInfo message, Vec3 sourcePos) {
        Vec3 pos = message.source();
        EventEntry eventEntry = new EventEntry(message, Mth.floor(pos.distanceTo(sourcePos)));
        int delay = eventEntry.distance * 2;
		this.schedule(eventEntry, delay);
	
		if (message.gameEvent() == SpectrumGameEvents.HUMMINGSTONE_HUMMING) {
			TypedTransmissionPayload.playTransmissionParticle((ServerLevel) world, new TypedTransmission(pos, this.positionSource, delay, TypedTransmission.Variant.HUMMINGSTONE));
			if (getQueuedEventCount() > 20) {
				world.gameEvent(message.context().sourceEntity(), SpectrumGameEvents.HUMMINGSTONE_HYMN, pos);
			}
		}
	}

	public record EventEntry(GameEvent.ListenerInfo message, int distance) { }

}

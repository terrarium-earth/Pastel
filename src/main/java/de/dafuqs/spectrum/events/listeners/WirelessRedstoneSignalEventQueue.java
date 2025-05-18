package de.dafuqs.spectrum.events.listeners;

import de.dafuqs.spectrum.events.SpectrumGameEvents;
import de.dafuqs.spectrum.networking.s2c_payloads.TypedTransmissionPayload;
import de.dafuqs.spectrum.particle.effect.TypedTransmission;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class WirelessRedstoneSignalEventQueue extends EventQueue<WirelessRedstoneSignalEventQueue.EventEntry> {
	
	public WirelessRedstoneSignalEventQueue(PositionSource positionSource, int range, EventQueue.Callback<EventEntry> listener) {
		super(positionSource, range, listener);
	}
	
	@Override
	public void acceptEvent(Level world, GameEvent.ListenerInfo event, Vec3 sourcePos) {
		if (world instanceof ServerLevel && event.gameEvent() == SpectrumGameEvents.WIRELESS_REDSTONE_SIGNAL) {
			Vec3 pos = event.source();
			var eventEntry = new WirelessRedstoneSignalEventQueue.EventEntry(event, Mth.floor(pos.distanceTo(sourcePos)));
			int delay = eventEntry.distance * 2;
			this.schedule(eventEntry, delay);
			TypedTransmissionPayload.playTransmissionParticle((ServerLevel) world, new TypedTransmission(pos, this.positionSource, delay, TypedTransmission.Variant.REDSTONE));
		}
	}

	public record EventEntry(GameEvent.ListenerInfo gameEvent, int distance) {
	}
	
}
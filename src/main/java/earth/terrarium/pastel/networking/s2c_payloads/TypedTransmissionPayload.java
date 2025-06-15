package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import earth.terrarium.pastel.particle.SpectrumParticleTypes;
import earth.terrarium.pastel.particle.effect.TransmissionParticleEffect;
import earth.terrarium.pastel.particle.effect.TypedTransmission;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record TypedTransmissionPayload(TypedTransmission transmission) implements CustomPacketPayload {
	
	public static final Type<TypedTransmissionPayload> ID = SpectrumC2SPackets.makeId("typed_transmission");
	public static final StreamCodec<RegistryFriendlyByteBuf, TypedTransmissionPayload> CODEC = StreamCodec.composite(
			TypedTransmission.STREAM_CODEC, TypedTransmissionPayload::transmission,
			TypedTransmissionPayload::new
	);
	
	public static void playTransmissionParticle(ServerLevel world, @NotNull TypedTransmission transmission) {
		PacketDistributor.sendToPlayersTrackingChunk(world, new ChunkPos(BlockPos.containing(transmission.getOrigin())), new TypedTransmissionPayload(transmission));
	}
	
	@SuppressWarnings("resource")
	public static void execute(TypedTransmissionPayload payload, IPayloadContext context) {
		var level = context.player().level();
        TypedTransmission transmission = payload.transmission();
		switch (transmission.getVariant()) {
			case BLOCK_POS -> level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.BLOCK_POS_EVENT_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
			case ITEM -> level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.ITEM_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
			case EXPERIENCE -> level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.EXPERIENCE_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
			case HUMMINGSTONE -> level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.HUMMINGSTONE_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
			case REDSTONE -> level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.WIRELESS_REDSTONE_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

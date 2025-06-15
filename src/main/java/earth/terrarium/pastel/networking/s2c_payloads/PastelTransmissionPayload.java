package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.pastel_network.network.PastelTransmission;
import earth.terrarium.pastel.blocks.pastel_network.network.ServerPastelNetwork;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import earth.terrarium.pastel.particle.effect.PastelTransmissionParticleEffect;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.common.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public record PastelTransmissionPayload(int networkColor, int travelTime, PastelTransmission transmission) implements CustomPacketPayload {
	
	public static final Type<PastelTransmissionPayload> ID = SpectrumC2SPackets.makeId("pastel_transmission");
	public static final StreamCodec<RegistryFriendlyByteBuf, PastelTransmissionPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, PastelTransmissionPayload::networkColor,
			ByteBufCodecs.INT, PastelTransmissionPayload::travelTime,
			PastelTransmission.STREAM_CODEC, PastelTransmissionPayload::transmission,
			PastelTransmissionPayload::new
	);
	
	public static void sendPastelTransmissionParticle(ServerPastelNetwork network, int travelTime, @NotNull PastelTransmission transmission) {
		Packet<?> packet = new ClientboundCustomPayloadPacket(new PastelTransmissionPayload(network.getColor(), travelTime, transmission));
		var targetPlayers = new HashSet<ServerPlayer>();
		targetPlayers.addAll(network.getLevel().getChunkSource().chunkMap.getPlayers(new ChunkPos(transmission.getNodePositions().getFirst()), false));
		targetPlayers.addAll(network.getLevel().getChunkSource().chunkMap.getPlayers(new ChunkPos(transmission.getNodePositions().getLast()), false));
		
		for (ServerPlayer player : targetPlayers) {
			player.connection.send(packet);
		}
	}
	
	@SuppressWarnings("resource")
	public static void execute(PastelTransmissionPayload payload, IPayloadContext context) {
		int color = payload.networkColor();
		int travelTime = payload.travelTime();
		PastelTransmission transmission = payload.transmission;
		BlockPos spawnPos = transmission.getStartPos();
		context.player().level().addParticle(new PastelTransmissionParticleEffect(transmission.getNodePositions(), transmission.getStack(), travelTime, color), spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5, 0, 0, 0);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

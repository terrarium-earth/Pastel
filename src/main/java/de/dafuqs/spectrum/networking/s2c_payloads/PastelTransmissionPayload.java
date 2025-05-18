package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pastel_network.network.PastelTransmission;
import de.dafuqs.spectrum.blocks.pastel_network.network.ServerPastelNetwork;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.particle.effect.PastelTransmissionParticleEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record PastelTransmissionPayload(int networkColor, int travelTime, PastelTransmission transmission) implements CustomPacketPayload {
	
	public static final Type<PastelTransmissionPayload> ID = SpectrumC2SPackets.makeId("pastel_transmission");
	public static final StreamCodec<RegistryFriendlyByteBuf, PastelTransmissionPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, PastelTransmissionPayload::networkColor,
			ByteBufCodecs.INT, PastelTransmissionPayload::travelTime,
			PastelTransmission.PACKET_CODEC, PastelTransmissionPayload::transmission,
			PastelTransmissionPayload::new
	);
	
	// TODO: we should probably also send the transmission to players that track the destination pos
	public static void sendPastelTransmissionParticle(ServerPastelNetwork network, int travelTime, @NotNull PastelTransmission transmission) {
		for (ServerPlayer player : PlayerLookup.tracking(network.getWorld(), transmission.getStartPos())) {
			ServerPlayNetworking.send(player, new PastelTransmissionPayload(network.getColor(), travelTime, transmission));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(PastelTransmissionPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		
		int color = payload.networkColor();
		int travelTime = payload.travelTime();
		PastelTransmission transmission = payload.transmission;
		BlockPos spawnPos = transmission.getStartPos();
		client.level.addParticle(new PastelTransmissionParticleEffect(transmission.getNodePositions(), transmission.getVariant().toStack(), travelTime, color), spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5, 0, 0, 0);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

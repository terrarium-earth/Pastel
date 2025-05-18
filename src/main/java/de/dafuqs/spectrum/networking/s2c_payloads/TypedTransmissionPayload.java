package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.particle.effect.TransmissionParticleEffect;
import de.dafuqs.spectrum.particle.effect.TypedTransmission;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
			TypedTransmission.PACKET_CODEC, TypedTransmissionPayload::transmission,
			TypedTransmissionPayload::new
	);
	
	public static void playTransmissionParticle(ServerLevel world, @NotNull TypedTransmission transmission) {
		for (ServerPlayer player : PlayerLookup.tracking(world, BlockPos.containing(transmission.getOrigin()))) {
			ServerPlayNetworking.send(player, new TypedTransmissionPayload(transmission));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(TypedTransmissionPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		if (client.level == null) return;
		TypedTransmission transmission = payload.transmission();
		switch (transmission.getVariant()) {
			case BLOCK_POS -> client.level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.BLOCK_POS_EVENT_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
			case ITEM -> client.level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.ITEM_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
			case EXPERIENCE -> client.level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.EXPERIENCE_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
			case HUMMINGSTONE -> client.level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.HUMMINGSTONE_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
			case REDSTONE -> client.level.addAlwaysVisibleParticle(new TransmissionParticleEffect(SpectrumParticleTypes.WIRELESS_REDSTONE_TRANSMISSION, transmission.getDestination(), transmission.getArrivalInTicks()), true, transmission.getOrigin().x(), transmission.getOrigin().y(), transmission.getOrigin().z(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

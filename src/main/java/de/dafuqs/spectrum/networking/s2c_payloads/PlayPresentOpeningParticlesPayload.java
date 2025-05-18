package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.present.PresentBlock;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;

public record PlayPresentOpeningParticlesPayload(BlockPos presentPos, Map<Integer, Integer> colors) implements CustomPacketPayload {
	
	public static final Type<PlayPresentOpeningParticlesPayload> ID = SpectrumC2SPackets.makeId("play_present_opening_particles");
	public static final StreamCodec<FriendlyByteBuf, PlayPresentOpeningParticlesPayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, PlayPresentOpeningParticlesPayload::presentPos,
			ByteBufCodecs.map(Object2IntArrayMap::new, ByteBufCodecs.INT, ByteBufCodecs.INT), PlayPresentOpeningParticlesPayload::colors,
			PlayPresentOpeningParticlesPayload::new
	);
	
	public static void playPresentOpeningParticles(ServerLevel serverWorld, BlockPos presentPos, Map<Integer, Integer> colors) {
		for (ServerPlayer player : PlayerLookup.tracking(serverWorld, presentPos)) {
			ServerPlayNetworking.send(player, new PlayPresentOpeningParticlesPayload(presentPos, colors));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayPresentOpeningParticlesPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		PresentBlock.spawnParticles(client.level, payload.presentPos, payload.colors);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

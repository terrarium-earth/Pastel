package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.present.PresentBlock;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
		PacketDistributor.sendToPlayersTrackingChunk(serverWorld, new ChunkPos(presentPos), new PlayPresentOpeningParticlesPayload(presentPos, colors));
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayPresentOpeningParticlesPayload payload, IPayloadContext context) {
		var level = context.player().level();
		PresentBlock.spawnParticles(level, payload.presentPos, payload.colors);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

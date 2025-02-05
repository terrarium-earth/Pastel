package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.present.*;
import de.dafuqs.spectrum.networking.*;
import it.unimi.dsi.fastutil.objects.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;

public record PlayPresentOpeningParticlesPayload(BlockPos presentPos, Map<DyeColor, Integer> colors) implements CustomPayload {
	
	public static final Id<PlayPresentOpeningParticlesPayload> ID = SpectrumC2SPackets.makeId("play_present_opening_particles");
	public static final PacketCodec<PacketByteBuf, PlayPresentOpeningParticlesPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, PlayPresentOpeningParticlesPayload::presentPos,
			PacketCodecs.map(Object2IntArrayMap::new, DyeColor.PACKET_CODEC, PacketCodecs.INTEGER), PlayPresentOpeningParticlesPayload::colors,
			PlayPresentOpeningParticlesPayload::new
	);
	
	public static void playPresentOpeningParticles(ServerWorld serverWorld, BlockPos presentPos, Map<DyeColor, Integer> colors) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, presentPos)) {
			ServerPlayNetworking.send(player, new PlayPresentOpeningParticlesPayload(presentPos, colors));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayPresentOpeningParticlesPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		PresentBlock.spawnParticles(client.world, payload.presentPos, payload.colors);
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

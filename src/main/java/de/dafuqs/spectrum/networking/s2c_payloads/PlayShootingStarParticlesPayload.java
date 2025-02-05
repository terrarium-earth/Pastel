package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.shooting_star.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayShootingStarParticlesPayload(Vec3d shootingStarPos, ShootingStar.Type type) implements CustomPayload {
	
	public static final Id<PlayShootingStarParticlesPayload> ID = SpectrumC2SPackets.makeId("play_shooting_star_particles");
	public static final PacketCodec<PacketByteBuf, PlayShootingStarParticlesPayload> CODEC = PacketCodec.tuple(
			PacketCodecHelper.VEC3D, PlayShootingStarParticlesPayload::shootingStarPos,
			ShootingStar.Type.PACKET_CODEC, PlayShootingStarParticlesPayload::type,
			PlayShootingStarParticlesPayload::new
	);
	
	public static void sendPlayShootingStarParticles(@NotNull ShootingStarEntity shootingStarEntity) {
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) shootingStarEntity.getWorld(), shootingStarEntity.getBlockPos())) {
			ServerPlayNetworking.send(player, new PlayShootingStarParticlesPayload(shootingStarEntity.getPos(), shootingStarEntity.getShootingStarType()));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayShootingStarParticlesPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		
		ShootingStarEntity.playHitParticles(client.world, payload.shootingStarPos.x, payload.shootingStarPos.y, payload.shootingStarPos.z, payload.type, 25);
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

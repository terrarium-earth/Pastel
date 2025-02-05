package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.world.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.particle.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayParticleWithExactVelocityPayload(Vec3d pos, ParticleEffect particle, int amount, Vec3d velocity) implements CustomPayload {
	
	public static final Id<PlayParticleWithExactVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_exact_velocity");
	public static final PacketCodec<RegistryByteBuf, PlayParticleWithExactVelocityPayload> CODEC = PacketCodec.tuple(
			PacketCodecHelper.VEC3D, PlayParticleWithExactVelocityPayload::pos,
			ParticleTypes.PACKET_CODEC, PlayParticleWithExactVelocityPayload::particle,
			PacketCodecs.INTEGER, PlayParticleWithExactVelocityPayload::amount,
			PacketCodecHelper.VEC3D, PlayParticleWithExactVelocityPayload::velocity,
			PlayParticleWithExactVelocityPayload::new
	);
	
	/**
	 * Play particle effect
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticles(ServerWorld world, BlockPos position, ParticleEffect particleEffect, int amount) {
		playParticleWithExactVelocity(world, Vec3d.ofCenter(position), particleEffect, amount, Vec3d.ZERO);
	}
	
	/**
	 * Play particle effect
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticleWithExactVelocity(ServerWorld world, @NotNull Vec3d position, @NotNull ParticleEffect particleEffect, int amount, @NotNull Vec3d velocity) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, BlockPos.ofFloored(position))) {
			ServerPlayNetworking.send(player, new PlayParticleWithExactVelocityPayload(position, particleEffect, amount, velocity));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayParticleWithExactVelocityPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		ClientWorld world = client.world;
		
		for (int i = 0; i < payload.amount; i++) {
			world.addParticle(payload.particle, payload.pos.getX(), payload.pos.getY(), payload.pos.getZ(), payload.velocity.getX(), payload.velocity.getY(), payload.velocity.getZ());
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}

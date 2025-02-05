package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.particle.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public record PlayParticleWithPatternAndVelocityPayload(Vec3d pos, ParticleEffect effect, VectorPattern pattern, double velocity) implements CustomPayload {
	
	public static final Id<PlayParticleWithPatternAndVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_pattern_and_velocity");
	public static final PacketCodec<RegistryByteBuf, PlayParticleWithPatternAndVelocityPayload> CODEC = PacketCodec.tuple(
			PacketCodecHelper.VEC3D, PlayParticleWithPatternAndVelocityPayload::pos,
			ParticleTypes.PACKET_CODEC, PlayParticleWithPatternAndVelocityPayload::effect,
			VectorPattern.PACKET_CODEC, PlayParticleWithPatternAndVelocityPayload::pattern,
			PacketCodecs.DOUBLE, PlayParticleWithPatternAndVelocityPayload::velocity,
			PlayParticleWithPatternAndVelocityPayload::new
	);
	
	/**
	 * Play particles matching a spawn pattern
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticleWithPatternAndVelocity(@Nullable PlayerEntity notThisPlayerEntity, ServerWorld world, @NotNull Vec3d position, @NotNull ParticleEffect particleEffect, @NotNull VectorPattern pattern, double velocity) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, BlockPos.ofFloored(position))) {
			if (!player.equals(notThisPlayerEntity)) {
				ServerPlayNetworking.send(player, new PlayParticleWithPatternAndVelocityPayload(position, particleEffect, pattern, velocity));
			}
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayParticleWithPatternAndVelocityPayload payload, ClientPlayNetworking.Context context) {
		ParticleHelper.playParticleWithPatternAndVelocityClient(context.client().world, payload.pos, payload.effect, payload.pattern, payload.velocity);
	}
}

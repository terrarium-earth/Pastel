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
import net.minecraft.util.math.random.*;
import org.jetbrains.annotations.*;

public record PlayParticleWithRandomOffsetAndVelocityPayload(Vec3d pos, ParticleEffect effect, int amount, Vec3d randomOffset, Vec3d randomVelocity) implements CustomPayload {
	
	public static final Id<PlayParticleWithRandomOffsetAndVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_random_offset_and_velocity");
	public static final PacketCodec<RegistryByteBuf, PlayParticleWithRandomOffsetAndVelocityPayload> CODEC = PacketCodec.tuple(
			PacketCodecHelper.VEC3D, PlayParticleWithRandomOffsetAndVelocityPayload::pos,
			ParticleTypes.PACKET_CODEC, PlayParticleWithRandomOffsetAndVelocityPayload::effect,
			PacketCodecs.INTEGER, PlayParticleWithRandomOffsetAndVelocityPayload::amount,
			PacketCodecHelper.VEC3D, PlayParticleWithRandomOffsetAndVelocityPayload::randomOffset,
			PacketCodecHelper.VEC3D, PlayParticleWithRandomOffsetAndVelocityPayload::randomVelocity,
			PlayParticleWithRandomOffsetAndVelocityPayload::new
	);
	
	/**
	 * Play particle effect
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticleWithRandomOffsetAndVelocity(ServerWorld world, Vec3d position, @NotNull ParticleEffect particleEffect, int amount, Vec3d randomOffset, Vec3d randomVelocity) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, BlockPos.ofFloored(position))) {
			ServerPlayNetworking.send(player, new PlayParticleWithRandomOffsetAndVelocityPayload(position, particleEffect, amount, randomOffset, randomVelocity));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayParticleWithRandomOffsetAndVelocityPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		ClientWorld world = client.world;
		Random random = world.getRandom();
		
		Vec3d pos = payload.pos;
		Vec3d randomOffset = payload.randomOffset;
		Vec3d randomVelocity = payload.randomVelocity;
		
		for (int i = 0; i < payload.amount; i++) {
			double randomOffsetX = randomOffset.x - random.nextDouble() * randomOffset.x * 2;
			double randomOffsetY = randomOffset.y - random.nextDouble() * randomOffset.y * 2;
			double randomOffsetZ = randomOffset.z - random.nextDouble() * randomOffset.z * 2;
			double randomVelocityX = randomVelocity.x - random.nextDouble() * randomVelocity.x * 2;
			double randomVelocityY = randomVelocity.y - random.nextDouble() * randomVelocity.y * 2;
			double randomVelocityZ = randomVelocity.z - random.nextDouble() * randomVelocity.z * 2;
			
			world.addParticle(payload.effect,
					pos.getX() + randomOffsetX, pos.getY() + randomOffsetY, pos.getZ() + randomOffsetZ,
					randomVelocityX, randomVelocityY, randomVelocityZ);
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}

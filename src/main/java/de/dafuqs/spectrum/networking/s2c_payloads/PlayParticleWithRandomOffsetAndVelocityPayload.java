package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record PlayParticleWithRandomOffsetAndVelocityPayload(Vec3 pos, ParticleOptions effect, int amount, Vec3 randomOffset, Vec3 randomVelocity) implements CustomPacketPayload {
	
	public static final Type<PlayParticleWithRandomOffsetAndVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_random_offset_and_velocity");
	public static final StreamCodec<RegistryFriendlyByteBuf, PlayParticleWithRandomOffsetAndVelocityPayload> CODEC = StreamCodec.composite(
			PacketCodecHelper.VEC3D, PlayParticleWithRandomOffsetAndVelocityPayload::pos,
			ParticleTypes.STREAM_CODEC, PlayParticleWithRandomOffsetAndVelocityPayload::effect,
			ByteBufCodecs.INT, PlayParticleWithRandomOffsetAndVelocityPayload::amount,
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
	public static void playParticleWithRandomOffsetAndVelocity(ServerLevel world, Vec3 position, @NotNull ParticleOptions particleEffect, int amount, Vec3 randomOffset, Vec3 randomVelocity) {
		for (ServerPlayer player : PlayerLookup.tracking(world, BlockPos.containing(position))) {
			ServerPlayNetworking.send(player, new PlayParticleWithRandomOffsetAndVelocityPayload(position, particleEffect, amount, randomOffset, randomVelocity));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayParticleWithRandomOffsetAndVelocityPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		ClientLevel world = client.level;
		RandomSource random = world.getRandom();
		
		Vec3 pos = payload.pos;
		Vec3 randomOffset = payload.randomOffset;
		Vec3 randomVelocity = payload.randomVelocity;
		
		for (int i = 0; i < payload.amount; i++) {
			double randomOffsetX = randomOffset.x - random.nextDouble() * randomOffset.x * 2;
			double randomOffsetY = randomOffset.y - random.nextDouble() * randomOffset.y * 2;
			double randomOffsetZ = randomOffset.z - random.nextDouble() * randomOffset.z * 2;
			double randomVelocityX = randomVelocity.x - random.nextDouble() * randomVelocity.x * 2;
			double randomVelocityY = randomVelocity.y - random.nextDouble() * randomVelocity.y * 2;
			double randomVelocityZ = randomVelocity.z - random.nextDouble() * randomVelocity.z * 2;
			
			world.addParticle(payload.effect,
					pos.x() + randomOffsetX, pos.y() + randomOffsetY, pos.z() + randomOffsetZ,
					randomVelocityX, randomVelocityY, randomVelocityZ);
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
}

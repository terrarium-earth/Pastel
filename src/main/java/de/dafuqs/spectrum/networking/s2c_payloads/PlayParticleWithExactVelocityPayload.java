package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record PlayParticleWithExactVelocityPayload(Vec3 pos, ParticleOptions particle, int amount, Vec3 velocity) implements CustomPacketPayload {
	
	public static final Type<PlayParticleWithExactVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_exact_velocity");
	public static final StreamCodec<RegistryFriendlyByteBuf, PlayParticleWithExactVelocityPayload> CODEC = StreamCodec.composite(
			PacketCodecHelper.VEC3D, PlayParticleWithExactVelocityPayload::pos,
			ParticleTypes.STREAM_CODEC, PlayParticleWithExactVelocityPayload::particle,
			ByteBufCodecs.INT, PlayParticleWithExactVelocityPayload::amount,
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
	public static void playParticles(ServerLevel world, BlockPos position, ParticleOptions particleEffect, int amount) {
		playParticleWithExactVelocity(world, Vec3.atCenterOf(position), particleEffect, amount, Vec3.ZERO);
	}
	
	/**
	 * Play particle effect
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticleWithExactVelocity(ServerLevel world, @NotNull Vec3 position, @NotNull ParticleOptions particleEffect, int amount, @NotNull Vec3 velocity) {
		for (ServerPlayer player : PlayerLookup.tracking(world, BlockPos.containing(position))) {
			ServerPlayNetworking.send(player, new PlayParticleWithExactVelocityPayload(position, particleEffect, amount, velocity));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayParticleWithExactVelocityPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		ClientLevel world = client.level;
		
		for (int i = 0; i < payload.amount; i++) {
			world.addParticle(payload.particle, payload.pos.x(), payload.pos.y(), payload.pos.z(), payload.velocity.x(), payload.velocity.y(), payload.velocity.z());
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
}

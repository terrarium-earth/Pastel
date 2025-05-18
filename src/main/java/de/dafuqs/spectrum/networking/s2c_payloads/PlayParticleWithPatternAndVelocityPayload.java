package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.helpers.ParticleHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.particle.VectorPattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record PlayParticleWithPatternAndVelocityPayload(Vec3 pos, ParticleOptions effect, VectorPattern pattern, double velocity) implements CustomPacketPayload {
	
	public static final Type<PlayParticleWithPatternAndVelocityPayload> ID = SpectrumC2SPackets.makeId("play_particle_with_pattern_and_velocity");
	public static final StreamCodec<RegistryFriendlyByteBuf, PlayParticleWithPatternAndVelocityPayload> CODEC = StreamCodec.composite(
			PacketCodecHelper.VEC3D, PlayParticleWithPatternAndVelocityPayload::pos,
			ParticleTypes.STREAM_CODEC, PlayParticleWithPatternAndVelocityPayload::effect,
			VectorPattern.PACKET_CODEC, PlayParticleWithPatternAndVelocityPayload::pattern,
			ByteBufCodecs.DOUBLE, PlayParticleWithPatternAndVelocityPayload::velocity,
			PlayParticleWithPatternAndVelocityPayload::new
	);
	
	/**
	 * Play particles matching a spawn pattern
	 *
	 * @param world          the world
	 * @param position       the pos of the particles
	 * @param particleEffect The particle effect to play
	 */
	public static void playParticleWithPatternAndVelocity(@Nullable Player notThisPlayerEntity, ServerLevel world, @NotNull Vec3 position, @NotNull ParticleOptions particleEffect, @NotNull VectorPattern pattern, double velocity) {
		for (ServerPlayer player : PlayerLookup.tracking(world, BlockPos.containing(position))) {
			if (!player.equals(notThisPlayerEntity)) {
				ServerPlayNetworking.send(player, new PlayParticleWithPatternAndVelocityPayload(position, particleEffect, pattern, velocity));
			}
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayParticleWithPatternAndVelocityPayload payload, ClientPlayNetworking.Context context) {
		ParticleHelper.playParticleWithPatternAndVelocityClient(context.client().level, payload.pos, payload.effect, payload.pattern, payload.velocity);
	}
}

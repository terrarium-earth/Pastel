package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.helpers.ParticleHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public record PlayParticleAroundBlockSidesPayload(BlockPos pos, int quantity, Vec3 velocity, ParticleOptions particle, Direction[] sides) implements CustomPacketPayload {
	
	public static final Type<PlayParticleAroundBlockSidesPayload> ID = SpectrumC2SPackets.makeId("play_particle_around_block_sides");
	public static final StreamCodec<RegistryFriendlyByteBuf, PlayParticleAroundBlockSidesPayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, PlayParticleAroundBlockSidesPayload::pos,
			ByteBufCodecs.VAR_INT, PlayParticleAroundBlockSidesPayload::quantity,
			PacketCodecHelper.VEC3D, PlayParticleAroundBlockSidesPayload::velocity,
			ParticleTypes.STREAM_CODEC, PlayParticleAroundBlockSidesPayload::particle,
			PacketCodecHelper.array(Direction.class, Direction.STREAM_CODEC), PlayParticleAroundBlockSidesPayload::sides,
			PlayParticleAroundBlockSidesPayload::new
	);
	
	public static void playParticleAroundBlockSides(ServerLevel world, int quantity, BlockPos pos, Vec3 velocity, ParticleOptions particleEffect, Predicate<ServerPlayer> sendCheck, Direction... sides) {
		for (ServerPlayer player : PlayerLookup.tracking(world, pos)) {
			if (!sendCheck.test(player))
				continue;
			ServerPlayNetworking.send(player, new PlayParticleAroundBlockSidesPayload(pos, quantity, velocity, particleEffect, sides));
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayParticleAroundBlockSidesPayload payload, ClientPlayNetworking.Context context) {
		ParticleHelper.playParticleAroundBlockSides(context.client().level, payload.particle, payload.pos, payload.sides, payload.quantity, payload.velocity);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

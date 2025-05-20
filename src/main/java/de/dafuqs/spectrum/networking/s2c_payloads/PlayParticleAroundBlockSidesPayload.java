package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.helpers.ParticleHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.common.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
	
	public static void playParticleAroundBlockSides(ServerLevel level, int quantity, BlockPos pos, Vec3 velocity, ParticleOptions particleEffect, Predicate<ServerPlayer> sendCheck, Direction... sides) {
		Packet<?> packet = new ClientboundCustomPayloadPacket(new PlayParticleAroundBlockSidesPayload(pos, quantity, velocity, particleEffect, sides));
		
		for (ServerPlayer player : level.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)) {
			if (sendCheck.test(player))
				continue;
			
			player.connection.send(packet);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayParticleAroundBlockSidesPayload payload, IPayloadContext context) {
		ParticleHelper.playParticleAroundBlockSides(context.player().level(), payload.particle, payload.pos, payload.sides, payload.quantity, payload.velocity);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

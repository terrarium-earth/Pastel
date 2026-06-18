package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.helpers.render.ParticleHelper;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.particle.VectorPattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record PlayParticleWithPatternAndVelocityPayload(
    Vec3 pos,
    ParticleOptions effect,
    VectorPattern pattern,
    double velocity
) implements CustomPacketPayload {

    public static final Type<PlayParticleWithPatternAndVelocityPayload> ID = PastelC2SPackets
        .makeId(
            "play_particle_with_pattern_and_velocity"
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayParticleWithPatternAndVelocityPayload> CODEC = StreamCodec
        .composite(
            PacketCodecHelper.VEC3D,
            PlayParticleWithPatternAndVelocityPayload::pos,
            ParticleTypes.STREAM_CODEC,
            PlayParticleWithPatternAndVelocityPayload::effect,
            VectorPattern.STREAM_CODEC,
            PlayParticleWithPatternAndVelocityPayload::pattern,
            ByteBufCodecs.DOUBLE,
            PlayParticleWithPatternAndVelocityPayload::velocity,
            PlayParticleWithPatternAndVelocityPayload::new
        );

    /**
     * Play particles matching a spawn pattern
     *
     * @param level          the world
     * @param position       the pos of the particles
     * @param particleEffect The particle effect to play
     */
    public static void playParticleWithPatternAndVelocity(
        @Nullable Player notThisPlayerEntity,
        ServerLevel level,
        @NotNull Vec3 position,
        @NotNull ParticleOptions particleEffect,
        @NotNull VectorPattern pattern,
        double velocity
    ) {
        Packet<?> packet = new ClientboundCustomPayloadPacket(
            new PlayParticleWithPatternAndVelocityPayload(position, particleEffect, pattern, velocity)
        );

        for (
            ServerPlayer player : level.getChunkSource().chunkMap
                .getPlayers(
                    new ChunkPos(BlockPos.containing(position)),
                    false
                )
        ) {
            if (notThisPlayerEntity != null && notThisPlayerEntity.equals(player))
                continue;

            player.connection.send(packet);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static void execute(PlayParticleWithPatternAndVelocityPayload payload, IPayloadContext context) {
        ParticleHelper
            .playParticleWithPatternAndVelocityClient(
                context
                    .player()
                    .level(),
                payload.pos,
                payload.effect,
                payload.pattern,
                payload.velocity
            );
    }
}

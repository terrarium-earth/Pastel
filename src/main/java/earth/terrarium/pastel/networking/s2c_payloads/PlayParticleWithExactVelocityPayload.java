package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record PlayParticleWithExactVelocityPayload(Vec3 pos, ParticleOptions particle, int amount, Vec3 velocity)
    implements CustomPacketPayload {

    public static final Type<PlayParticleWithExactVelocityPayload> ID = PastelC2SPackets.makeId(
        "play_particle_with_exact_velocity");
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayParticleWithExactVelocityPayload> CODEC
        = StreamCodec.composite(
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
    public static void playParticleWithExactVelocity(
        ServerLevel world, @NotNull Vec3 position, @NotNull ParticleOptions particleEffect, int amount,
        @NotNull Vec3 velocity
    ) {
        PacketDistributor.sendToPlayersTrackingChunk(
            world, new ChunkPos(BlockPos.containing(position)),
            new PlayParticleWithExactVelocityPayload(position, particleEffect, amount, velocity)
        );
    }

    @SuppressWarnings("resource")
    public static void execute(PlayParticleWithExactVelocityPayload payload, IPayloadContext context) {
        var level = context.player()
                           .level();

        for (int i = 0; i < payload.amount; i++) {
            level.addParticle(
                payload.particle, payload.pos.x(), payload.pos.y(), payload.pos.z(), payload.velocity.x(),
                payload.velocity.y(), payload.velocity.z()
            );
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

}

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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record PlayParticleWithRandomOffsetAndVelocityPayload(
    Vec3 pos,
    ParticleOptions effect,
    int amount,
    Vec3 randomOffset,
    Vec3 randomVelocity
) implements CustomPacketPayload {

    public static final Type<PlayParticleWithRandomOffsetAndVelocityPayload> ID = PastelC2SPackets
        .makeId(
            "play_particle_with_random_offset_and_velocity"
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayParticleWithRandomOffsetAndVelocityPayload> CODEC = StreamCodec
        .composite(
            PacketCodecHelper.VEC3D,
            PlayParticleWithRandomOffsetAndVelocityPayload::pos,
            ParticleTypes.STREAM_CODEC,
            PlayParticleWithRandomOffsetAndVelocityPayload::effect,
            ByteBufCodecs.INT,
            PlayParticleWithRandomOffsetAndVelocityPayload::amount,
            PacketCodecHelper.VEC3D,
            PlayParticleWithRandomOffsetAndVelocityPayload::randomOffset,
            PacketCodecHelper.VEC3D,
            PlayParticleWithRandomOffsetAndVelocityPayload::randomVelocity,
            PlayParticleWithRandomOffsetAndVelocityPayload::new
        );

    /**
     * Play particle effect
     *
     * @param world          the world
     * @param position       the pos of the particles
     * @param particleEffect The particle effect to play
     */
    public static void playParticleWithRandomOffsetAndVelocity(
        ServerLevel world,
        Vec3 position,
        @NotNull ParticleOptions particleEffect,
        int amount,
        Vec3 randomOffset,
        Vec3 randomVelocity
    ) {
        PacketDistributor
            .sendToPlayersTrackingChunk(
                world,
                new ChunkPos(BlockPos.containing(position)),
                new PlayParticleWithRandomOffsetAndVelocityPayload(
                    position,
                    particleEffect,
                    amount,
                    randomOffset,
                    randomVelocity
                )
            );
    }

    @SuppressWarnings(
        "resource"
    )
    public static void execute(PlayParticleWithRandomOffsetAndVelocityPayload payload, IPayloadContext context) {
        var level = context
            .player()
            .level();
        RandomSource random = level.getRandom();

        Vec3 pos = payload.pos;
        Vec3 randomOffset = payload.randomOffset;
        Vec3 randomVelocity = payload.randomVelocity;

        for (
            int i = 0;
            i < payload.amount;
            i++
        ) {
            double randomOffsetX = randomOffset.x - random.nextDouble() * randomOffset.x * 2;
            double randomOffsetY = randomOffset.y - random.nextDouble() * randomOffset.y * 2;
            double randomOffsetZ = randomOffset.z - random.nextDouble() * randomOffset.z * 2;
            double randomVelocityX = randomVelocity.x - random.nextDouble() * randomVelocity.x * 2;
            double randomVelocityY = randomVelocity.y - random.nextDouble() * randomVelocity.y * 2;
            double randomVelocityZ = randomVelocity.z - random.nextDouble() * randomVelocity.z * 2;

            level
                .addParticle(
                    payload.effect,
                    pos.x() + randomOffsetX,
                    pos.y() + randomOffsetY,
                    pos.z() + randomOffsetZ,
                    randomVelocityX,
                    randomVelocityY,
                    randomVelocityZ
                );
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

}

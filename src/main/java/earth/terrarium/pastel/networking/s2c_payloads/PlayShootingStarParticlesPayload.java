package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.shooting_star.ShootingStar;
import earth.terrarium.pastel.entity.entity.ShootingStarEntity;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record PlayShootingStarParticlesPayload(Vec3 shootingStarPos, ShootingStar.Variant variant)
    implements CustomPacketPayload {

    public static final Type<PlayShootingStarParticlesPayload> ID = PastelC2SPackets.makeId(
        "play_shooting_star_particles");
    public static final StreamCodec<FriendlyByteBuf, PlayShootingStarParticlesPayload> CODEC = StreamCodec.composite(
        PacketCodecHelper.VEC3D, PlayShootingStarParticlesPayload::shootingStarPos,
        ShootingStar.Variant.STREAM_CODEC, PlayShootingStarParticlesPayload::variant,
        PlayShootingStarParticlesPayload::new
    );

    public static void sendPlayShootingStarParticles(@NotNull ShootingStarEntity shootingStarEntity) {
        PacketDistributor.sendToPlayersTrackingChunk(
            (ServerLevel) shootingStarEntity.level(), new ChunkPos(shootingStarEntity.blockPosition()),
            new PlayShootingStarParticlesPayload(
                shootingStarEntity.position(),
                shootingStarEntity.getShootingStarType()
            )
        );
    }

    public static void execute(PlayShootingStarParticlesPayload payload, IPayloadContext context) {
        var level = context.player()
                           .level();

        ShootingStarEntity.playHitParticles(
            level, payload.shootingStarPos.x, payload.shootingStarPos.y, payload.shootingStarPos.z, payload.variant,
            25
        );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

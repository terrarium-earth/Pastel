package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.spells.MoonstoneStrike;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MoonstoneBlastPayload(double x, double y, double z, Vec3 data, Vec3 playerVelocity)
    implements
    CustomPacketPayload {

    public static final Type<MoonstoneBlastPayload> ID = PastelC2SPackets.makeId("moonstone_blast");

    public static final StreamCodec<FriendlyByteBuf, MoonstoneBlastPayload> CODEC = StreamCodec
        .composite(
            ByteBufCodecs.DOUBLE,
            MoonstoneBlastPayload::x,
            ByteBufCodecs.DOUBLE,
            MoonstoneBlastPayload::y,
            ByteBufCodecs.DOUBLE,
            MoonstoneBlastPayload::z,
            PacketCodecHelper.VEC3D,
            MoonstoneBlastPayload::data,
            PacketCodecHelper.VEC3D,
            MoonstoneBlastPayload::playerVelocity,
            MoonstoneBlastPayload::new
        );

    public static void sendMoonstoneBlast(ServerLevel serverWorld, MoonstoneStrike moonstoneStrike, float pitch) {
        for (
            ServerPlayer player : serverWorld.getChunkSource().chunkMap
                .getPlayers(
                    new ChunkPos(
                        BlockPos.containing(moonstoneStrike.getX(), moonstoneStrike.getY(), moonstoneStrike.getZ())
                    ),
                    false
                )
        ) {
            PacketDistributor
                .sendToPlayer(
                    player,
                    new MoonstoneBlastPayload(
                        moonstoneStrike.getX(),
                        moonstoneStrike.getY(),
                        moonstoneStrike.getZ(),
                        new Vec3(
                            moonstoneStrike.getPower(),
                            moonstoneStrike.getKnockbackMod(),
                            pitch
                        ),
                        moonstoneStrike
                            .getAffectedPlayers()
                            .getOrDefault(player, Vec3.ZERO)
                    )
                );
        }
    }

    public static void execute(MoonstoneBlastPayload payload, IPayloadContext context) {
        Player player = context.player();
        Vec3 playerVelocity = payload.playerVelocity();

        var data = payload.data;
        MoonstoneStrike
            .create(
                player.level(),
                null,
                null,
                payload.x,
                payload.y,
                payload.z,
                (float) data.x,
                (float) data.y,
                (float) data.z
            );
        player
            .setDeltaMovement(
                player
                    .getDeltaMovement()
                    .add(playerVelocity.x, playerVelocity.y, playerVelocity.z)
            );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

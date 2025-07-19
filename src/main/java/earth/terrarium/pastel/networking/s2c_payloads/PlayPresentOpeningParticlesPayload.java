package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.present.PresentBlock;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;

import java.util.Map;

public record PlayPresentOpeningParticlesPayload(BlockPos presentPos, Map<Integer, Integer> colors)
    implements CustomPacketPayload {

    public static final Type<PlayPresentOpeningParticlesPayload> ID = PastelC2SPackets.makeId(
        "play_present_opening_particles");
    public static final StreamCodec<FriendlyByteBuf, PlayPresentOpeningParticlesPayload> CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, PlayPresentOpeningParticlesPayload::presentPos,
        ByteBufCodecs.map(Object2IntArrayMap::new, ByteBufCodecs.INT, ByteBufCodecs.INT),
        PlayPresentOpeningParticlesPayload::colors,
        PlayPresentOpeningParticlesPayload::new
    );

    public static void playPresentOpeningParticles(
        ServerLevel serverWorld, BlockPos presentPos, Map<Integer, Integer> colors) {
        PacketDistributor.sendToPlayersTrackingChunk(
            serverWorld, new ChunkPos(presentPos), new PlayPresentOpeningParticlesPayload(presentPos, colors));
    }

    public static void execute(PlayPresentOpeningParticlesPayload payload, IPayloadContext context) {
        var level = context.player()
                           .level();
        PresentBlock.spawnParticles(level, payload.presentPos, payload.colors);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

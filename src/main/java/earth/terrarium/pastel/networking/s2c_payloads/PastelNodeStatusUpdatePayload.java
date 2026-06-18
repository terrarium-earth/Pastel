package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;
import java.util.Map;

public record PastelNodeStatusUpdatePayload(boolean longSpin, Map<BlockPos, Integer> spinTimes)
    implements
    CustomPacketPayload {

    public static final Type<PastelNodeStatusUpdatePayload> ID = PastelC2SPackets.makeId("pastel_node_status_update");

    public static final StreamCodec<FriendlyByteBuf, PastelNodeStatusUpdatePayload> CODEC = StreamCodec
        .composite(
            ByteBufCodecs.BOOL,
            PastelNodeStatusUpdatePayload::longSpin,
            ByteBufCodecs.map(Object2IntArrayMap::new, BlockPos.STREAM_CODEC, ByteBufCodecs.INT),
            PastelNodeStatusUpdatePayload::spinTimes,
            PastelNodeStatusUpdatePayload::new
        );

    public static void sendPastelNodeStatusUpdate(List<PastelNodeBlockEntity> nodes, boolean longSpin) {
        Map<BlockPos, Integer> spinTimes = new Object2IntArrayMap<>();
        for (
            PastelNodeBlockEntity node : nodes
        ) {
            Level world = node.getLevel();
            if (world == null)
                continue;

            int time = longSpin
                ? 24 + world
                    .getRandom()
                    .nextInt(11)
                : 10 + world
                    .getRandom()
                    .nextInt(11);
            spinTimes.put(node.getBlockPos(), time);
        }

        PacketDistributor
            .sendToPlayersTrackingChunk(
                (ServerLevel) nodes
                    .getFirst()
                    .getLevel(),
                new ChunkPos(
                    nodes
                        .getFirst()
                        .getBlockPos()
                ),
                new PastelNodeStatusUpdatePayload(longSpin, spinTimes)
            );
    }

    public static void execute(PastelNodeStatusUpdatePayload payload, IPayloadContext context) {
        var level = context
            .player()
            .level();
        for (
            Map.Entry<BlockPos, Integer> e : payload.spinTimes.entrySet()
        ) {
            var entity = level.getBlockEntity(e.getKey());
            if (!(entity instanceof PastelNodeBlockEntity node))
                continue;

            node.setSpinTicks(e.getValue());

            if (payload.longSpin && node.isTriggerTransfer()) {
                node.markTriggered();
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

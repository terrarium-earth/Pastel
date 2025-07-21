package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PlayPedestalStartCraftingParticlePayload(BlockPos pedestalPos) implements CustomPacketPayload {

    public static final Type<PlayPedestalStartCraftingParticlePayload> ID = PastelC2SPackets.makeId(
        "play_pedestal_start_crafting_particle");
    public static final StreamCodec<FriendlyByteBuf, PlayPedestalStartCraftingParticlePayload> CODEC
        = StreamCodec.composite(
        BlockPos.STREAM_CODEC, PlayPedestalStartCraftingParticlePayload::pedestalPos,
        PlayPedestalStartCraftingParticlePayload::new
    );

    public static void spawnPedestalStartCraftingParticles(PedestalBlockEntity pedestalBlockEntity) {
        PacketDistributor.sendToPlayersTrackingChunk(
            (ServerLevel) pedestalBlockEntity.getLevel(), new ChunkPos(pedestalBlockEntity.getBlockPos()),
            new PlayPedestalStartCraftingParticlePayload(pedestalBlockEntity.getBlockPos())
        );
    }

    public static void execute(PlayPedestalStartCraftingParticlePayload payload, IPayloadContext context) {
        //PedestalBlockEntity.spawnCraftingStartParticles(
        //    context.player()
        //           .level(), payload.pedestalPos
        //);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

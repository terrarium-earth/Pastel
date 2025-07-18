package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.fusion_shrine.FusionShrineBlockEntity;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

public record PlayFusionCraftingInProgressParticlePayload(BlockPos pos) implements CustomPacketPayload {

    public static final Type<PlayFusionCraftingInProgressParticlePayload> ID = PastelC2SPackets.makeId(
        "play_fusion_crafting_in_progress_particle");
    public static final StreamCodec<FriendlyByteBuf, PlayFusionCraftingInProgressParticlePayload> CODEC
        = StreamCodec.composite(
        BlockPos.STREAM_CODEC, PlayFusionCraftingInProgressParticlePayload::pos,
        PlayFusionCraftingInProgressParticlePayload::new
    );

    public static void sendPlayFusionCraftingInProgressParticles(ServerLevel world, BlockPos pos) {
        PacketDistributor.sendToPlayersTrackingChunk(
            world, new ChunkPos(pos), new PlayFusionCraftingInProgressParticlePayload(pos));
    }

    @SuppressWarnings("resource")
    public static void execute(PlayFusionCraftingInProgressParticlePayload payload, IPayloadContext context) {
        BlockEntity blockEntity = context.player()
                                         .level()
                                         .getBlockEntity(payload.pos);
        if (blockEntity instanceof FusionShrineBlockEntity fusionShrineBlockEntity) {
            fusionShrineBlockEntity.spawnCraftingParticles();
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public record PlayPedestalCraftingFinishedParticlePayload(BlockPos pedestalPos, ItemStack craftedStack)
    implements CustomPacketPayload {

    public static final Type<PlayPedestalCraftingFinishedParticlePayload> ID = PastelC2SPackets.makeId(
        "play_pedestal_crafting_finished_particle");
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayPedestalCraftingFinishedParticlePayload> CODEC
        = StreamCodec.composite(
        BlockPos.STREAM_CODEC, PlayPedestalCraftingFinishedParticlePayload::pedestalPos,
        ItemStack.STREAM_CODEC, PlayPedestalCraftingFinishedParticlePayload::craftedStack,
        PlayPedestalCraftingFinishedParticlePayload::new
    );

    public static void sendPlayPedestalCraftingFinishedParticle(
        ServerLevel world, BlockPos pedestalPos, ItemStack craftedStack) {
        PacketDistributor.sendToPlayersTrackingChunk(
            world, new ChunkPos(pedestalPos), new PlayPedestalCraftingFinishedParticlePayload(
                pedestalPos,
                craftedStack
            )
        );
    }

    @SuppressWarnings("resource")
    public static void execute(PlayPedestalCraftingFinishedParticlePayload payload, IPayloadContext context) {
        var level = context.player()
                           .level();

        RandomSource random = level.random;

        for (int i = 0; i < 10; i++) {
            level.addParticle(
                new ItemParticleOption(ParticleTypes.ITEM, payload.craftedStack), payload.pedestalPos.getX() + 0.5,
                payload.pedestalPos.getY() + 1, payload.pedestalPos.getZ() + 0.5, 0.15 - random.nextFloat() * 0.3,
                random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3
            );
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

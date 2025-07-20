package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.chests.FabricationChestBlockEntity;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record FabricationChestStatusUpdatePayload(
    BlockPos pos, boolean isFull, boolean hasValidRecipes, List<ItemStack> stacks
) implements CustomPacketPayload {

    public static final Type<FabricationChestStatusUpdatePayload> ID = PastelC2SPackets.makeId(
        "fabrication_chest_status_update");
    public static final StreamCodec<RegistryFriendlyByteBuf, FabricationChestStatusUpdatePayload> CODEC
        = StreamCodec.composite(
        BlockPos.STREAM_CODEC, FabricationChestStatusUpdatePayload::pos,
        ByteBufCodecs.BOOL, FabricationChestStatusUpdatePayload::isFull,
        ByteBufCodecs.BOOL, FabricationChestStatusUpdatePayload::hasValidRecipes,
        ItemStack.LIST_STREAM_CODEC, FabricationChestStatusUpdatePayload::stacks,
        FabricationChestStatusUpdatePayload::new
    );

    public static void sendFabricationChestStatusUpdate(FabricationChestBlockEntity chest) {
        BlockPos pos = chest.getBlockPos();
        boolean isFull = chest.isFullServer();
        boolean hasValidRecipes = chest.hasValidRecipes();
        List<ItemStack> stacks = new ArrayList<>(chest.getRecipeOutputs());

        PacketDistributor.sendToPlayersTrackingChunk(
            (ServerLevel) chest.getLevel(), new ChunkPos(pos), new FabricationChestStatusUpdatePayload(
                pos, isFull,
                hasValidRecipes,
                stacks
            )
        );
    }

    @SuppressWarnings("resource")
    public static void execute(FabricationChestStatusUpdatePayload payload, IPayloadContext context) {
        var level = context.player()
                           .level();
        var pos = payload.pos;
        var isFull = payload.isFull;
        var hasValidRecipes = payload.hasValidRecipes;
        List<ItemStack> outputs = payload.stacks;
        Optional<FabricationChestBlockEntity> entity = level.getBlockEntity(
            pos, PastelBlockEntities.FABRICATION_CHEST.get());
        if (entity.isPresent()) {
            entity.get()
                  .updateState(isFull, hasValidRecipes, outputs);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

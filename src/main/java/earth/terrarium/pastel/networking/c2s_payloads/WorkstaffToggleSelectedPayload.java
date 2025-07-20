package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.inventories.WorkstaffScreenHandler;
import earth.terrarium.pastel.items.tools.WorkstaffItem;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record WorkstaffToggleSelectedPayload(int index) implements CustomPacketPayload {

    public static final Type<WorkstaffToggleSelectedPayload> ID = PastelC2SPackets.makeId("workstaff_toggle_selected");
    public static final StreamCodec<RegistryFriendlyByteBuf, WorkstaffToggleSelectedPayload> CODEC
        = StreamCodec.composite(
        ByteBufCodecs.INT, WorkstaffToggleSelectedPayload::index,
        WorkstaffToggleSelectedPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static IPayloadHandler<WorkstaffToggleSelectedPayload> getPayloadHandler() {
        return (payload, context) -> {
            ServerPlayer player = (ServerPlayer) context.player();
            AbstractContainerMenu screenHandler = player.containerMenu;
            if (screenHandler instanceof WorkstaffScreenHandler workstaffScreenHandler) {
                WorkstaffItem.GUIToggle toggle = WorkstaffItem.GUIToggle.values()[payload.index];
                workstaffScreenHandler.onWorkstaffToggleSelectionPacket(toggle);
            }
        };
    }

}

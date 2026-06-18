package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.api.block.InkColorSelectedPacketReceiver;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.networking.s2c_payloads.InkColorSelectedS2CPayload;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import java.util.Optional;

public record InkColorSelectedC2SPayload(Optional<Holder<InkColor>> inkColor) implements CustomPacketPayload {

    public static final Type<InkColorSelectedC2SPayload> ID = PastelC2SPackets.makeId("ink_color_select");

    public static final StreamCodec<RegistryFriendlyByteBuf, InkColorSelectedC2SPayload> CODEC = StreamCodec
        .composite(
            ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(PastelRegistries.INK_COLOR.key())),
            InkColorSelectedC2SPayload::inkColor,
            InkColorSelectedC2SPayload::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    @SuppressWarnings(
        "resource"
    )
    public static IPayloadHandler<InkColorSelectedC2SPayload> getPayloadHandler() {
        return (payload, context) -> {
            ServerPlayer player = (ServerPlayer) context.player();
            AbstractContainerMenu screenHandler = player.containerMenu;
            if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {

                Optional<Holder<InkColor>> inkColor = payload.inkColor();

                // send the newly selected color to all players that have the same gui open
                // this is minus the player that selected that entry (since they have that info already)
                inkColorSelectedPacketReceiver.onInkColorSelectedPacket(inkColor);
                for (
                    ServerPlayer serverPlayer : player
                        .level()
                        .getServer()
                        .getPlayerList()
                        .getPlayers()
                ) {
                    if (serverPlayer.containerMenu instanceof InkColorSelectedPacketReceiver receiver && receiver
                        .getBlockEntity() != null && receiver.getBlockEntity() == inkColorSelectedPacketReceiver
                            .getBlockEntity()) {
                        InkColorSelectedS2CPayload.sendInkColorSelected(inkColor, serverPlayer);
                    }
                }
            }
        };
    }

}

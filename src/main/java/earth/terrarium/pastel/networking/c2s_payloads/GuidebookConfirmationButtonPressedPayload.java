package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.progression.PastelCriteria;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record GuidebookConfirmationButtonPressedPayload(String confirmationString) implements CustomPacketPayload {

    public static final Type<GuidebookConfirmationButtonPressedPayload> ID = PastelC2SPackets.makeId(
        "confirmation_button_pressed");
    public static final StreamCodec<FriendlyByteBuf, GuidebookConfirmationButtonPressedPayload> CODEC
        = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, GuidebookConfirmationButtonPressedPayload::confirmationString,
        GuidebookConfirmationButtonPressedPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static IPayloadHandler<GuidebookConfirmationButtonPressedPayload> getPayloadHandler() {
        return (payload, context) -> {
            ServerPlayer player = (ServerPlayer) context.player();
            PastelCriteria.CONFIRMATION_BUTTON_PRESSED.trigger(player, payload.confirmationString);
            player.level()
                  .playSound(
                      player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK.value(),
                      SoundSource.PLAYERS, 1.0F, 1.0F
                  );
        };

    }

}

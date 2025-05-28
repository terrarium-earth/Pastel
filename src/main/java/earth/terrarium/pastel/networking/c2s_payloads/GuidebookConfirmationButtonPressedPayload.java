package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import earth.terrarium.pastel.progression.SpectrumAdvancementCriteria;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public record GuidebookConfirmationButtonPressedPayload(String confirmationString) implements CustomPacketPayload {
	
	public static final Type<GuidebookConfirmationButtonPressedPayload> ID = SpectrumC2SPackets.makeId("confirmation_button_pressed");
	public static final StreamCodec<FriendlyByteBuf, GuidebookConfirmationButtonPressedPayload> CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, GuidebookConfirmationButtonPressedPayload::confirmationString, GuidebookConfirmationButtonPressedPayload::new);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	public static IPayloadHandler<GuidebookConfirmationButtonPressedPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayer player = (ServerPlayer) context.player();
			SpectrumAdvancementCriteria.CONFIRMATION_BUTTON_PRESSED.trigger(player, payload.confirmationString);
			player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
		};
		
	}
	
}

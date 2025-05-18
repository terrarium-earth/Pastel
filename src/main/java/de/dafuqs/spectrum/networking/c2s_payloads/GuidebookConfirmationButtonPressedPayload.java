package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
	
	public static ServerPlayNetworking.PlayPayloadHandler<GuidebookConfirmationButtonPressedPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayer player = context.player();
			SpectrumAdvancementCriteria.CONFIRMATION_BUTTON_PRESSED.trigger(player, payload.confirmationString);
			player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
		};
		
	}
	
}

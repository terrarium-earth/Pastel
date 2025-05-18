package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.api.block.InkColorSelectedPacketReceiver;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.networking.s2c_payloads.InkColorSelectedS2CPayload;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.Optional;

public record InkColorSelectedC2SPayload(Optional<Holder<InkColor>> inkColor) implements CustomPacketPayload {
	
	public static final Type<InkColorSelectedC2SPayload> ID = SpectrumC2SPackets.makeId("ink_color_select");
	public static final StreamCodec<RegistryFriendlyByteBuf, InkColorSelectedC2SPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(SpectrumRegistries.INK_COLOR.key())), InkColorSelectedC2SPayload::inkColor,
			InkColorSelectedC2SPayload::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	@SuppressWarnings("resource")
	public static ServerPlayNetworking.PlayPayloadHandler<InkColorSelectedC2SPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayer player = context.player();
			AbstractContainerMenu screenHandler = player.containerMenu;
			if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {
				
				Optional<Holder<InkColor>> inkColor = payload.inkColor();
				
				// send the newly selected color to all players that have the same gui open
				// this is minus the player that selected that entry (since they have that info already)
				inkColorSelectedPacketReceiver.onInkColorSelectedPacket(inkColor);
				for (ServerPlayer serverPlayer : context.server().getPlayerList().getPlayers()) {
					if (serverPlayer.containerMenu instanceof InkColorSelectedPacketReceiver receiver && receiver.getBlockEntity() != null && receiver.getBlockEntity() == inkColorSelectedPacketReceiver.getBlockEntity()) {
						InkColorSelectedS2CPayload.sendInkColorSelected(inkColor, serverPlayer);
					}
				}
			}
		};
	}
	
}

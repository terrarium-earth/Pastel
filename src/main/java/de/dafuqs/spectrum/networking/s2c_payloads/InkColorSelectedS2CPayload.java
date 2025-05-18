package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.block.InkColorSelectedPacketReceiver;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.Optional;

public record InkColorSelectedS2CPayload(Optional<Holder<InkColor>> inkColor) implements CustomPacketPayload {
	
	public static final Type<InkColorSelectedS2CPayload> ID = SpectrumC2SPackets.makeId("ink_color_selected");
	public static final StreamCodec<RegistryFriendlyByteBuf, InkColorSelectedS2CPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(SpectrumRegistries.INK_COLOR.key())), InkColorSelectedS2CPayload::inkColor,
			InkColorSelectedS2CPayload::new
	);
	
	public static void sendInkColorSelected(Optional<Holder<InkColor>> inkColor, ServerPlayer player) {
		ServerPlayNetworking.send(player, new InkColorSelectedS2CPayload(inkColor));
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(InkColorSelectedS2CPayload payload, ClientPlayNetworking.Context context) {
		AbstractContainerMenu screenHandler = context.player().containerMenu;
		if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {
			inkColorSelectedPacketReceiver.onInkColorSelectedPacket(payload.inkColor());
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
}

package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.api.block.InkColorSelectedPacketReceiver;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import earth.terrarium.pastel.registries.SpectrumRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;
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
		PacketDistributor.sendToPlayer(player, new InkColorSelectedS2CPayload(inkColor));
	}
	
	public static void execute(InkColorSelectedS2CPayload payload, IPayloadContext context) {
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

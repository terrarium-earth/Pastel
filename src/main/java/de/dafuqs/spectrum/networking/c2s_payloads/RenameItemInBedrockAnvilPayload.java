package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.inventories.BedrockAnvilScreenHandler;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.StringUtil;
import org.jetbrains.annotations.NotNull;

public record RenameItemInBedrockAnvilPayload(String name) implements CustomPacketPayload {
	
	public static final Type<RenameItemInBedrockAnvilPayload> ID = SpectrumC2SPackets.makeId("rename_item_in_bedrock_anvil");
	public static final StreamCodec<FriendlyByteBuf, RenameItemInBedrockAnvilPayload> CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, RenameItemInBedrockAnvilPayload::name, RenameItemInBedrockAnvilPayload::new);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	public static ServerPlayNetworking.@NotNull PlayPayloadHandler<RenameItemInBedrockAnvilPayload> getPayloadHandler() {
		return (payload, context) -> {
			if (context.player().containerMenu instanceof BedrockAnvilScreenHandler bedrockAnvilScreenHandler) {
				String string = StringUtil.filterText(payload.name);
				if (string.length() <= 50) {
					bedrockAnvilScreenHandler.setNewItemName(string);
				}
			}
		};
	}
	
}

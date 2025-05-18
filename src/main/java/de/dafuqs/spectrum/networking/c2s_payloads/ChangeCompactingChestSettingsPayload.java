package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.blocks.chests.CompactingChestBlockEntity;
import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.inventories.AutoCraftingMode;
import de.dafuqs.spectrum.inventories.CompactingChestScreenHandler;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;

public record ChangeCompactingChestSettingsPayload(AutoCraftingMode mode) implements CustomPacketPayload {
	
	public static final CustomPacketPayload.Type<ChangeCompactingChestSettingsPayload> ID = SpectrumC2SPackets.makeId("change_compacting_chest_settings");
	public static final StreamCodec<FriendlyByteBuf, ChangeCompactingChestSettingsPayload> CODEC = StreamCodec.composite(
			PacketCodecHelper.enumOf(AutoCraftingMode::values),
			ChangeCompactingChestSettingsPayload::mode,
			ChangeCompactingChestSettingsPayload::new
	);
	
	public static ServerPlayNetworking.PlayPayloadHandler<ChangeCompactingChestSettingsPayload> getPayloadHandler() {
		return (payload, context) -> {
			// receive the client packet...
			if (context.player().containerMenu instanceof CompactingChestScreenHandler compactingChestScreenHandler) {
				BlockEntity blockEntity = compactingChestScreenHandler.getBlockEntity();
				if (blockEntity instanceof CompactingChestBlockEntity compactingChestBlockEntity) {
					// apply the new settings
					compactingChestBlockEntity.applySettings(payload);
				}
			}
		};
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
}

package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.block.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;

public record ChangeCompactingChestSettingsPayload(AutoCraftingMode mode) implements CustomPayload {
	
	public static final CustomPayload.Id<ChangeCompactingChestSettingsPayload> ID = SpectrumC2SPackets.makeId("change_compacting_chest_settings");
	public static final PacketCodec<PacketByteBuf, ChangeCompactingChestSettingsPayload> CODEC = PacketCodec.tuple(
			PacketCodecHelper.enumOf(AutoCraftingMode::values),
			ChangeCompactingChestSettingsPayload::mode,
			ChangeCompactingChestSettingsPayload::new
	);
	
	public static ServerPlayNetworking.PlayPayloadHandler<ChangeCompactingChestSettingsPayload> getPayloadHandler() {
		return (payload, context) -> {
			// receive the client packet...
			if (context.player().currentScreenHandler instanceof CompactingChestScreenHandler compactingChestScreenHandler) {
				BlockEntity blockEntity = compactingChestScreenHandler.getBlockEntity();
				if (blockEntity instanceof CompactingChestBlockEntity compactingChestBlockEntity) {
					// apply the new settings
					compactingChestBlockEntity.applySettings(payload);
				}
			}
		};
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}

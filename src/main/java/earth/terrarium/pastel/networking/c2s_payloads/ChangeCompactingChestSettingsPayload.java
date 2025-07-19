package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.blocks.chests.CompactingChestBlockEntity;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.inventories.AutoCraftingMode;
import earth.terrarium.pastel.inventories.CompactingChestScreenHandler;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.BlockEntity;

public record ChangeCompactingChestSettingsPayload(AutoCraftingMode mode) implements CustomPacketPayload {
	
	public static final CustomPacketPayload.Type<ChangeCompactingChestSettingsPayload> ID = PastelC2SPackets.makeId("change_compacting_chest_settings");
	public static final StreamCodec<FriendlyByteBuf, ChangeCompactingChestSettingsPayload> CODEC = StreamCodec.composite(
			PacketCodecHelper.enumOf(AutoCraftingMode::values),
			ChangeCompactingChestSettingsPayload::mode,
			ChangeCompactingChestSettingsPayload::new
	);
	
	public static IPayloadHandler<ChangeCompactingChestSettingsPayload> getPayloadHandler() {
		return (payload, context) -> {
			// receive the client packet...
			if (context.player().containerMenu instanceof CompactingChestScreenHandler compactingChestScreenHandler) {
				BlockEntity blockEntity = compactingChestScreenHandler.getBlockEntity();
				if (blockEntity instanceof CompactingChestBlockEntity compactingChestBlockEntity) {
					// apply the new settings
					compactingChestBlockEntity.applySettings(payload.mode);
				}
			}
		};
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
}

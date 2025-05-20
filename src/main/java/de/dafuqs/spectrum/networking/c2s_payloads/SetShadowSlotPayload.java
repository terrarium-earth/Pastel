package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.api.block.FilterConfigurable;
import de.dafuqs.spectrum.inventories.slots.ShadowSlot;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public record SetShadowSlotPayload(int screenHandlerSyncId, int slotId,
								   ItemStack shadowStack) implements CustomPacketPayload {
	
	public static final Type<SetShadowSlotPayload> ID = SpectrumC2SPackets.makeId("set_shadow_slot");
	public static final StreamCodec<RegistryFriendlyByteBuf, SetShadowSlotPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, SetShadowSlotPayload::screenHandlerSyncId,
			ByteBufCodecs.INT, SetShadowSlotPayload::slotId,
			ItemStack.STREAM_CODEC, SetShadowSlotPayload::shadowStack,
			SetShadowSlotPayload::new
	);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	public static IPayloadHandler<SetShadowSlotPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayer player = (ServerPlayer) context.player();
			AbstractContainerMenu screenHandler = player.containerMenu;
			
			if (screenHandler == null || screenHandler.containerId != payload.screenHandlerSyncId) {
				return;
			}
			
			Slot slot = screenHandler.getSlot(payload.slotId);
			if (slot == null || !(slot instanceof ShadowSlot) || !(slot.container instanceof FilterConfigurable.FilterInventory filterInventory)) {
				return;
			}
			
			filterInventory.getClicker().clickShadowSlot(screenHandler.containerId, slot, payload.shadowStack());
		};
	}
	
}

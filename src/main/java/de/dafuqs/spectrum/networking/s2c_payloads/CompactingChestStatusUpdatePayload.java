package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;

public record CompactingChestStatusUpdatePayload(BlockPos pos, boolean hasToCraft) implements CustomPayload {
	
	public static final Id<CompactingChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("compacting_chest_status_update");
	public static final PacketCodec<PacketByteBuf, CompactingChestStatusUpdatePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, CompactingChestStatusUpdatePayload::pos,
			PacketCodecs.BOOL, CompactingChestStatusUpdatePayload::hasToCraft,
			CompactingChestStatusUpdatePayload::new
	);
	
	public static void sendCompactingChestStatusUpdate(CompactingChestBlockEntity chest) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, new CompactingChestStatusUpdatePayload(chest.getPos(), chest.hasToCraft()));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(CompactingChestStatusUpdatePayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		var entity = client.world.getBlockEntity(payload.pos, SpectrumBlockEntities.COMPACTING_CHEST);
		entity.ifPresent(compactingChestBlockEntity -> compactingChestBlockEntity.shouldCraft(payload.hasToCraft));
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.item.*;
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

import java.util.*;

public record BlackHoleChestStatusUpdatePayload(BlockPos pos, boolean isFull, boolean canStoreExperience, long storedExperience, long maxStoredExperience) implements CustomPayload {
	
	public static final Id<BlackHoleChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("black_hole_chest_status_update");
	public static final PacketCodec<PacketByteBuf, BlackHoleChestStatusUpdatePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, BlackHoleChestStatusUpdatePayload::pos,
			PacketCodecs.BOOL, BlackHoleChestStatusUpdatePayload::isFull,
			PacketCodecs.BOOL, BlackHoleChestStatusUpdatePayload::canStoreExperience,
			PacketCodecs.VAR_LONG, BlackHoleChestStatusUpdatePayload::storedExperience,
			PacketCodecs.VAR_LONG, BlackHoleChestStatusUpdatePayload::maxStoredExperience,
			BlackHoleChestStatusUpdatePayload::new
	);
	
	public static void sendBlackHoleChestUpdate(BlackHoleChestBlockEntity chest) {
		var xpStack = chest.getStack(BlackHoleChestBlockEntity.EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT);
		
		long storedXP = 0;
		long maxStoredXP = 0;
		
		if (xpStack.getItem() instanceof ExperienceStorageItem experienceStorageItem && chest.getWorld() != null) {
			storedXP = ExperienceStorageItem.getStoredExperience(xpStack);
			maxStoredXP = experienceStorageItem.getMaxStoredExperience(chest.getWorld().getRegistryManager(), xpStack);
		}
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, new BlackHoleChestStatusUpdatePayload(chest.getPos(), chest.isFullServer(), chest.canStoreExperience(), storedXP, maxStoredXP));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(BlackHoleChestStatusUpdatePayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		if (client.world != null) {
			Optional<BlackHoleChestBlockEntity> entity = client.world.getBlockEntity(payload.pos, SpectrumBlockEntities.BLACK_HOLE_CHEST);
			entity.ifPresent(chest -> {
				chest.setFull(payload.isFull);
				chest.setHasXPStorage(payload.canStoreExperience);
				chest.setXPData(payload.storedExperience, payload.maxStoredExperience);
			});
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.item.ExperienceStorageItem;
import de.dafuqs.spectrum.blocks.chests.BlackHoleChestBlockEntity;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public record BlackHoleChestStatusUpdatePayload(BlockPos pos, boolean isFull, boolean canStoreExperience, long storedExperience, long maxStoredExperience) implements CustomPacketPayload {
	
	public static final Type<BlackHoleChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("black_hole_chest_status_update");
	public static final StreamCodec<FriendlyByteBuf, BlackHoleChestStatusUpdatePayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, BlackHoleChestStatusUpdatePayload::pos,
			ByteBufCodecs.BOOL, BlackHoleChestStatusUpdatePayload::isFull,
			ByteBufCodecs.BOOL, BlackHoleChestStatusUpdatePayload::canStoreExperience,
			ByteBufCodecs.VAR_LONG, BlackHoleChestStatusUpdatePayload::storedExperience,
			ByteBufCodecs.VAR_LONG, BlackHoleChestStatusUpdatePayload::maxStoredExperience,
			BlackHoleChestStatusUpdatePayload::new
	);
	
	public static void sendBlackHoleChestUpdate(BlackHoleChestBlockEntity chest) {
		var xpStack = chest.getItem(BlackHoleChestBlockEntity.EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT);
		
		long storedXP = 0;
		long maxStoredXP = 0;
		
		if (xpStack.getItem() instanceof ExperienceStorageItem experienceStorageItem && chest.getLevel() != null) {
			storedXP = ExperienceStorageItem.getStoredExperience(xpStack);
			maxStoredXP = experienceStorageItem.getMaxStoredExperience(chest.getLevel().registryAccess(), xpStack);
		}
		
		for (ServerPlayer player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, new BlackHoleChestStatusUpdatePayload(chest.getBlockPos(), chest.isFullServer(), chest.canStoreExperience(), storedXP, maxStoredXP));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(BlackHoleChestStatusUpdatePayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		if (client.level != null) {
			Optional<BlackHoleChestBlockEntity> entity = client.level.getBlockEntity(payload.pos, SpectrumBlockEntities.BLACK_HOLE_CHEST);
			entity.ifPresent(chest -> {
				chest.setFull(payload.isFull);
				chest.setHasXPStorage(payload.canStoreExperience);
				chest.setXPData(payload.storedExperience, payload.maxStoredExperience);
			});
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

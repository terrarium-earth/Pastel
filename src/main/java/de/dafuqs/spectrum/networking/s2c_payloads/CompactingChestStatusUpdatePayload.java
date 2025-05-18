package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.chests.CompactingChestBlockEntity;
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

public record CompactingChestStatusUpdatePayload(BlockPos pos, boolean hasToCraft) implements CustomPacketPayload {
	
	public static final Type<CompactingChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("compacting_chest_status_update");
	public static final StreamCodec<FriendlyByteBuf, CompactingChestStatusUpdatePayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, CompactingChestStatusUpdatePayload::pos,
			ByteBufCodecs.BOOL, CompactingChestStatusUpdatePayload::hasToCraft,
			CompactingChestStatusUpdatePayload::new
	);
	
	public static void sendCompactingChestStatusUpdate(CompactingChestBlockEntity chest) {
		for (ServerPlayer player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, new CompactingChestStatusUpdatePayload(chest.getBlockPos(), chest.hasToCraft()));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(CompactingChestStatusUpdatePayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		var entity = client.level.getBlockEntity(payload.pos, SpectrumBlockEntities.COMPACTING_CHEST);
		entity.ifPresent(compactingChestBlockEntity -> compactingChestBlockEntity.shouldCraft(payload.hasToCraft));
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

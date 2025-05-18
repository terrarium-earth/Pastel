package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pedestal.PedestalBlockEntity;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public record PlayPedestalStartCraftingParticlePayload(BlockPos pedestalPos) implements CustomPacketPayload {
	
	public static final Type<PlayPedestalStartCraftingParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_start_crafting_particle");
	public static final StreamCodec<FriendlyByteBuf, PlayPedestalStartCraftingParticlePayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, PlayPedestalStartCraftingParticlePayload::pedestalPos,
			PlayPedestalStartCraftingParticlePayload::new
	);
	
	public static void spawnPedestalStartCraftingParticles(PedestalBlockEntity pedestalBlockEntity) {
		for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) pedestalBlockEntity.getLevel(), pedestalBlockEntity.getBlockPos())) {
			ServerPlayNetworking.send(player, new PlayPedestalStartCraftingParticlePayload(pedestalBlockEntity.getBlockPos()));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayPedestalStartCraftingParticlePayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		PedestalBlockEntity.spawnCraftingStartParticles(client.level, payload.pedestalPos);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

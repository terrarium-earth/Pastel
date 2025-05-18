package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.fusion_shrine.FusionShrineBlockEntity;
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
import net.minecraft.world.level.block.entity.BlockEntity;

public record PlayFusionCraftingInProgressParticlePayload(BlockPos pos) implements CustomPacketPayload {
	
	public static final Type<PlayFusionCraftingInProgressParticlePayload> ID = SpectrumC2SPackets.makeId("play_fusion_crafting_in_progress_particle");
	public static final StreamCodec<FriendlyByteBuf, PlayFusionCraftingInProgressParticlePayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, PlayFusionCraftingInProgressParticlePayload::pos,
			PlayFusionCraftingInProgressParticlePayload::new
	);
	
	public static void sendPlayFusionCraftingInProgressParticles(ServerLevel world, BlockPos pos) {
		for (ServerPlayer player : PlayerLookup.tracking(world, pos)) {
			ServerPlayNetworking.send(player, new PlayFusionCraftingInProgressParticlePayload(pos));
		}
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayFusionCraftingInProgressParticlePayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		BlockEntity blockEntity = client.level.getBlockEntity(payload.pos);
		if (blockEntity instanceof FusionShrineBlockEntity fusionShrineBlockEntity) {
			fusionShrineBlockEntity.spawnCraftingParticles();
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

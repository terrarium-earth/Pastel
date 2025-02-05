package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.fusion_shrine.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;

public record PlayFusionCraftingInProgressParticlePayload(BlockPos pos) implements CustomPayload {
	
	public static final Id<PlayFusionCraftingInProgressParticlePayload> ID = SpectrumC2SPackets.makeId("play_fusion_crafting_in_progress_particle");
	public static final PacketCodec<PacketByteBuf, PlayFusionCraftingInProgressParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, PlayFusionCraftingInProgressParticlePayload::pos,
			PlayFusionCraftingInProgressParticlePayload::new
	);
	
	public static void sendPlayFusionCraftingInProgressParticles(ServerWorld world, BlockPos pos) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
			ServerPlayNetworking.send(player, new PlayFusionCraftingInProgressParticlePayload(pos));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayFusionCraftingInProgressParticlePayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		BlockEntity blockEntity = client.world.getBlockEntity(payload.pos);
		if (blockEntity instanceof FusionShrineBlockEntity fusionShrineBlockEntity) {
			fusionShrineBlockEntity.spawnCraftingParticles();
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.world.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.particle.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;

public record PlayPedestalCraftingFinishedParticlePayload(BlockPos pedestalPos, ItemStack craftedStack) implements CustomPayload {
	
	public static final Id<PlayPedestalCraftingFinishedParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_crafting_finished_particle");
	public static final PacketCodec<RegistryByteBuf, PlayPedestalCraftingFinishedParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, PlayPedestalCraftingFinishedParticlePayload::pedestalPos,
			ItemStack.PACKET_CODEC, PlayPedestalCraftingFinishedParticlePayload::craftedStack,
			PlayPedestalCraftingFinishedParticlePayload::new
	);
	
	public static void sendPlayPedestalCraftingFinishedParticle(ServerWorld world, BlockPos pedestalPos, ItemStack craftedStack) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, pedestalPos)) {
			ServerPlayNetworking.send(player, new PlayPedestalCraftingFinishedParticlePayload(pedestalPos, craftedStack));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayPedestalCraftingFinishedParticlePayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		ClientWorld world = client.world;
		Random random = world.random;
		
		for (int i = 0; i < 10; i++) {
			world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, payload.craftedStack), payload.pedestalPos.getX() + 0.5, payload.pedestalPos.getY() + 1, payload.pedestalPos.getZ() + 0.5, 0.15 - random.nextFloat() * 0.3, random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3);
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

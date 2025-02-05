package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;

import java.util.*;

public record FabricationChestStatusUpdatePayload(BlockPos pos, boolean isFull, boolean hasValidRecipes, List<ItemStack> stacks) implements CustomPayload {
	
	public static final Id<FabricationChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("fabrication_chest_status_update");
	public static final PacketCodec<RegistryByteBuf, FabricationChestStatusUpdatePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, FabricationChestStatusUpdatePayload::pos,
			PacketCodecs.BOOL, FabricationChestStatusUpdatePayload::isFull,
			PacketCodecs.BOOL, FabricationChestStatusUpdatePayload::hasValidRecipes,
			ItemStack.LIST_PACKET_CODEC, FabricationChestStatusUpdatePayload::stacks,
			FabricationChestStatusUpdatePayload::new
	);
	
	public static void sendFabricationChestStatusUpdate(FabricationChestBlockEntity chest) {
		BlockPos pos = chest.getPos();
		boolean isFull = chest.isFullServer();
		boolean hasValidRecipes = chest.hasValidRecipes();
		List<ItemStack> stacks = new ArrayList<>(chest.getRecipeOutputs());
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, new FabricationChestStatusUpdatePayload(pos, isFull, hasValidRecipes, stacks));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(FabricationChestStatusUpdatePayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		var pos = payload.pos;
		var isFull = payload.isFull;
		var hasValidRecipes = payload.hasValidRecipes;
		List<ItemStack> outputs = payload.stacks;
		Optional<FabricationChestBlockEntity> entity = client.world.getBlockEntity(pos, SpectrumBlockEntities.FABRICATION_CHEST);
		if (entity.isPresent()) {
			entity.get().updateState(isFull, hasValidRecipes, outputs);
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.chests.FabricationChestBlockEntity;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record FabricationChestStatusUpdatePayload(BlockPos pos, boolean isFull, boolean hasValidRecipes, List<ItemStack> stacks) implements CustomPacketPayload {
	
	public static final Type<FabricationChestStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("fabrication_chest_status_update");
	public static final StreamCodec<RegistryFriendlyByteBuf, FabricationChestStatusUpdatePayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, FabricationChestStatusUpdatePayload::pos,
			ByteBufCodecs.BOOL, FabricationChestStatusUpdatePayload::isFull,
			ByteBufCodecs.BOOL, FabricationChestStatusUpdatePayload::hasValidRecipes,
			ItemStack.LIST_STREAM_CODEC, FabricationChestStatusUpdatePayload::stacks,
			FabricationChestStatusUpdatePayload::new
	);
	
	public static void sendFabricationChestStatusUpdate(FabricationChestBlockEntity chest) {
		BlockPos pos = chest.getBlockPos();
		boolean isFull = chest.isFullServer();
		boolean hasValidRecipes = chest.hasValidRecipes();
		List<ItemStack> stacks = new ArrayList<>(chest.getRecipeOutputs());
		
		for (ServerPlayer player : PlayerLookup.tracking(chest)) {
			ServerPlayNetworking.send(player, new FabricationChestStatusUpdatePayload(pos, isFull, hasValidRecipes, stacks));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(FabricationChestStatusUpdatePayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		var pos = payload.pos;
		var isFull = payload.isFull;
		var hasValidRecipes = payload.hasValidRecipes;
		List<ItemStack> outputs = payload.stacks;
		Optional<FabricationChestBlockEntity> entity = client.level.getBlockEntity(pos, SpectrumBlockEntities.FABRICATION_CHEST);
		if (entity.isPresent()) {
			entity.get().updateState(isFull, hasValidRecipes, outputs);
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

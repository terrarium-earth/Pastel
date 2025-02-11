package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.recipe.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.recipe.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

import java.util.*;

public record GuidebookHintBoughtPayload(Identifier completionAdvancement, IngredientStack payment) implements CustomPayload {
	
	public static final Id<GuidebookHintBoughtPayload> ID = SpectrumC2SPackets.makeId("guidebook_hint_bought");
	public static final PacketCodec<RegistryByteBuf, GuidebookHintBoughtPayload> CODEC = PacketCodec.tuple(
			Identifier.PACKET_CODEC, GuidebookHintBoughtPayload::completionAdvancement,
			IngredientStack.Serializer.PACKET_CODEC, GuidebookHintBoughtPayload::payment,
			GuidebookHintBoughtPayload::new);
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
	public static ServerPlayNetworking.PlayPayloadHandler<GuidebookHintBoughtPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayerEntity player = context.player();
			for (ItemStack remainder : InventoryHelper.removeIngredientStacksFromInventoryWithRemainders(List.of(payload.payment()), player.getInventory())) {
				InventoryHelper.smartAddToInventory(remainder, player.getInventory(), null);
			}
			
			// give the player the hidden "used_tip" advancement and play a sound
			Support.grantAdvancementCriterion(player, "hidden/used_tip", "used_tip");
			Support.grantAdvancementCriterion(player, payload.completionAdvancement(), "hint_purchased");
			player.getWorld().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
		};
	}
	
}

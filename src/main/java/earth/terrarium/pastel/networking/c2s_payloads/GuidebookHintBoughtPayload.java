package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.helpers.InventoryHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import net.neoforged.neoforge.items.*;
import net.neoforged.neoforge.items.wrapper.*;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record GuidebookHintBoughtPayload(ResourceLocation completionAdvancement, IngredientStack payment) implements CustomPacketPayload {
	
	public static final Type<GuidebookHintBoughtPayload> ID = SpectrumC2SPackets.makeId("guidebook_hint_bought");
	public static final StreamCodec<RegistryFriendlyByteBuf, GuidebookHintBoughtPayload> CODEC = StreamCodec.composite(
			ResourceLocation.STREAM_CODEC, GuidebookHintBoughtPayload::completionAdvancement,
			IngredientStack.STREAM_CODEC, GuidebookHintBoughtPayload::payment,
			GuidebookHintBoughtPayload::new);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	public static IPayloadHandler<GuidebookHintBoughtPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayer player = (ServerPlayer) context.player();
			for (ItemStack remainder : InventoryHelper.removeIngredientStacksFromInventoryWithRemainders(List.of(payload.payment()), new PlayerInvWrapper(player.getInventory()))) {
				ItemHandlerHelper.insertItemStacked(new PlayerInvWrapper(player.getInventory()), remainder, false);
			}
			
			// give the player the hidden "used_tip" advancement and play a sound
			Support.grantAdvancementCriterion(player, "hidden/used_tip", "used_tip");
			Support.grantAdvancementCriterion(player, payload.completionAdvancement(), "hint_purchased");
			player.level().playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
		};
	}
	
}

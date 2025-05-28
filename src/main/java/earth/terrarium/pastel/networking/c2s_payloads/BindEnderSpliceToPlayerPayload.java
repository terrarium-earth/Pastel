package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.items.magic_items.EnderSpliceItem;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public record BindEnderSpliceToPlayerPayload(int entityId) implements CustomPacketPayload {
	
	public static final Type<BindEnderSpliceToPlayerPayload> ID = SpectrumC2SPackets.makeId("bind_ender_splice_to_player");
	public static final StreamCodec<FriendlyByteBuf, BindEnderSpliceToPlayerPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, BindEnderSpliceToPlayerPayload::entityId, BindEnderSpliceToPlayerPayload::new);
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
	public static IPayloadHandler<BindEnderSpliceToPlayerPayload> getPayloadHandler() {
		return (payload, context) -> {
			ServerPlayer player = (ServerPlayer) context.player();
			Entity entity = player.level().getEntity(payload.entityId());
			if (entity instanceof ServerPlayer targetPlayerEntity
					&& player.distanceTo(targetPlayerEntity) < 8
					&& player.getMainHandItem().is(SpectrumItems.ENDER_SPLICE.get())) {
				
				EnderSpliceItem.setTeleportTargetPlayer(player.getMainHandItem(), targetPlayerEntity);
				
				player.playSound(SpectrumSoundEvents.ENDER_SPLICE_BOUND, 1.0F, 1.0F);
				targetPlayerEntity.playSound(SpectrumSoundEvents.ENDER_SPLICE_BOUND, 1.0F, 1.0F);
			}
		};
	}
	
}

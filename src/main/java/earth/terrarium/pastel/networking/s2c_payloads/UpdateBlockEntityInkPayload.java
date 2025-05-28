package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.api.energy.InkStorage;
import earth.terrarium.pastel.api.energy.InkStorageBlockEntity;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;

public record UpdateBlockEntityInkPayload(BlockPos pos, Map<InkColor, Long> storage, long currentTotal) implements CustomPacketPayload {
	
	public static final Type<UpdateBlockEntityInkPayload> ID = SpectrumC2SPackets.makeId("update_block_entity_ink");
	public static final StreamCodec<FriendlyByteBuf, UpdateBlockEntityInkPayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, UpdateBlockEntityInkPayload::pos,
			ByteBufCodecs.map(HashMap::new, InkColor.STREAM_CODEC, ByteBufCodecs.VAR_LONG), UpdateBlockEntityInkPayload::storage,
			ByteBufCodecs.VAR_LONG, UpdateBlockEntityInkPayload::currentTotal,
			UpdateBlockEntityInkPayload::new
	);
	
	@SuppressWarnings("deprecation")
	public static void updateBlockEntityInk(BlockPos pos, InkStorage inkStorage, ServerPlayer player) {
		PacketDistributor.sendToPlayer(player, new UpdateBlockEntityInkPayload(pos, inkStorage.getEnergy(), inkStorage.getCurrentTotal()));
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(UpdateBlockEntityInkPayload payload, IPayloadContext context) {
		var level = context.player().level();
		BlockEntity blockEntity = level.getBlockEntity(payload.pos);
		if (blockEntity instanceof InkStorageBlockEntity<?> inkStorageBlockEntity) {
			inkStorageBlockEntity.getEnergyStorage().setEnergy(payload.storage, payload.currentTotal);
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public record PastelNodeStatusUpdatePayload(boolean longSpin, Map<BlockPos, Integer> spinTimes) implements CustomPacketPayload {
	
	public static final Type<PastelNodeStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("pastel_node_status_update");
	public static final StreamCodec<FriendlyByteBuf, PastelNodeStatusUpdatePayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL, PastelNodeStatusUpdatePayload::longSpin,
			ByteBufCodecs.map(Object2IntArrayMap::new, BlockPos.STREAM_CODEC, ByteBufCodecs.INT), PastelNodeStatusUpdatePayload::spinTimes,
			PastelNodeStatusUpdatePayload::new
	);
	
	public static void sendPastelNodeStatusUpdate(List<PastelNodeBlockEntity> nodes, boolean longSpin) {
		Map<BlockPos, Integer> spinTimes = new Object2IntArrayMap<>();
		for (PastelNodeBlockEntity node : nodes) {
			Level world = node.getLevel();
			if (world == null)
				continue;
			
			int time = longSpin ? 24 + world.getRandom().nextInt(11) : 10 + world.getRandom().nextInt(11);
			spinTimes.put(node.getBlockPos(), time);
		}
		
		for (ServerPlayer player : PlayerLookup.tracking(nodes.getFirst())) {
			ServerPlayNetworking.send(player, new PastelNodeStatusUpdatePayload(longSpin, spinTimes));
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(PastelNodeStatusUpdatePayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		for (Map.Entry<BlockPos, Integer> e : payload.spinTimes.entrySet()) {
			var entity = client.level.getBlockEntity(e.getKey());
			if (!(entity instanceof PastelNodeBlockEntity node))
				continue;
			
			node.setSpinTicks(e.getValue());
			
			if (payload.longSpin && node.isTriggerTransfer()) {
				node.markTriggered();
			}
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

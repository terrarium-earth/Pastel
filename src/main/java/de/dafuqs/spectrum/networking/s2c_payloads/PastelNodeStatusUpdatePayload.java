package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pastel_network.nodes.*;
import de.dafuqs.spectrum.networking.*;
import it.unimi.dsi.fastutil.objects.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public record PastelNodeStatusUpdatePayload(boolean longSpin, Map<BlockPos, Integer> spinTimes) implements CustomPayload {
	
	public static final Id<PastelNodeStatusUpdatePayload> ID = SpectrumC2SPackets.makeId("pastel_node_status_update");
	public static final PacketCodec<PacketByteBuf, PastelNodeStatusUpdatePayload> CODEC = PacketCodec.tuple(
			PacketCodecs.BOOL, PastelNodeStatusUpdatePayload::longSpin,
			PacketCodecs.map(Object2IntArrayMap::new, BlockPos.PACKET_CODEC, PacketCodecs.INTEGER), PastelNodeStatusUpdatePayload::spinTimes,
			PastelNodeStatusUpdatePayload::new
	);
	
	public static void sendPastelNodeStatusUpdate(List<PastelNodeBlockEntity> nodes, boolean longSpin) {
		Map<BlockPos, Integer> spinTimes = new Object2IntArrayMap<>();
		for (PastelNodeBlockEntity node : nodes) {
			World world = node.getWorld();
			if (world == null)
				continue;
			
			int time = longSpin ? 24 + world.getRandom().nextInt(11) : 10 + world.getRandom().nextInt(11);
			spinTimes.put(node.getPos(), time);
		}
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(nodes.getFirst())) {
			ServerPlayNetworking.send(player, new PastelNodeStatusUpdatePayload(longSpin, spinTimes));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(PastelNodeStatusUpdatePayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		for (Map.Entry<BlockPos, Integer> e : payload.spinTimes.entrySet()) {
			var entity = client.world.getBlockEntity(e.getKey());
			if (!(entity instanceof PastelNodeBlockEntity node))
				continue;
			
			node.setSpinTicks(e.getValue());
			
			if (payload.longSpin && node.isTriggerTransfer()) {
				node.markTriggered();
			}
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

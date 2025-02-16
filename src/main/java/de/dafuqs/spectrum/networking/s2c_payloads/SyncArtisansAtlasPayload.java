package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.items.map.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.util.*;

import java.util.*;

public record SyncArtisansAtlasPayload(Optional<Identifier> targetId, MapUpdateS2CPacket packet) implements CustomPayload {
	
	public static final Id<SyncArtisansAtlasPayload> ID = SpectrumC2SPackets.makeId("sync_artisans_atlas");
	public static final PacketCodec<RegistryByteBuf, SyncArtisansAtlasPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.optional(Identifier.PACKET_CODEC), SyncArtisansAtlasPayload::targetId,
			MapUpdateS2CPacket.CODEC, SyncArtisansAtlasPayload::packet,
			SyncArtisansAtlasPayload::new
	);
	
	@Environment(EnvType.CLIENT)
	public static void execute(SyncArtisansAtlasPayload payload, ClientPlayNetworking.Context context) {
		var client = context.client();
		if (client.world == null) return;
		NetworkThreadUtils.forceMainThread(payload.packet, client.getNetworkHandler(), client);
		var mapRenderer = client.gameRenderer.getMapRenderer();
		var mapIdComponent = payload.packet.mapId();
		var mapState = client.world.getMapState(mapIdComponent);
		if (mapState == null) {
			mapState = new ArtisansAtlasState(payload.packet.scale(), payload.packet.locked(), client.world.getRegistryKey());
			client.world.putClientsideMapState(mapIdComponent, mapState);
		}
		if (payload.packet.decorations().isPresent())
			mapState.replaceDecorations(payload.packet.decorations().get());
		if (payload.packet.updateData().isPresent())
			payload.packet.updateData().get().setColorsTo(mapState);
		if (mapState instanceof ArtisansAtlasState artisansAtlasState)
			artisansAtlasState.setTargetId(payload.targetId.orElse(null));
		mapRenderer.updateTexture(mapIdComponent, mapState);
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}

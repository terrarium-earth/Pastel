package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.items.map.ArtisansAtlasState;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import net.minecraft.client.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record SyncArtisansAtlasPayload(Optional<ResourceLocation> targetId, ClientboundMapItemDataPacket packet) implements CustomPacketPayload {
	
	public static final Type<SyncArtisansAtlasPayload> ID = SpectrumC2SPackets.makeId("sync_artisans_atlas");
	public static final StreamCodec<RegistryFriendlyByteBuf, SyncArtisansAtlasPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), SyncArtisansAtlasPayload::targetId,
			ClientboundMapItemDataPacket.STREAM_CODEC, SyncArtisansAtlasPayload::packet,
			SyncArtisansAtlasPayload::new
	);
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(SyncArtisansAtlasPayload payload, IPayloadContext context) {
		var client = Minecraft.getInstance();
		var level = client.level;
		if (level == null) return;
		PacketUtils.ensureRunningOnSameThread(payload.packet, client.getConnection(), client);
		var mapRenderer = client.gameRenderer.getMapRenderer();
		var mapIdComponent = payload.packet.mapId();
		var mapState = level.getMapData(mapIdComponent);
		if (mapState == null) {
			mapState = new ArtisansAtlasState(payload.packet.scale(), payload.packet.locked(), level.dimension());
			level.overrideMapData(mapIdComponent, mapState);
		}
		if (payload.packet.decorations().isPresent())
			mapState.addClientSideDecorations(payload.packet.decorations().get());
		if (payload.packet.colorPatch().isPresent())
			payload.packet.colorPatch().get().applyToMap(mapState);
		if (mapState instanceof ArtisansAtlasState artisansAtlasState)
			artisansAtlasState.setTargetId(payload.targetId.orElse(null));
		mapRenderer.update(mapIdComponent, mapState);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
	
}

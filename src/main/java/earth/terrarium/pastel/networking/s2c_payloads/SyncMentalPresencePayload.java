package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.deeper_down.DimensionRenderEffects;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record SyncMentalPresencePayload(double value) implements CustomPacketPayload {
	
	public static final Type<SyncMentalPresencePayload> ID = SpectrumC2SPackets.makeId("sync_mental_presence");
	public static final StreamCodec<FriendlyByteBuf, SyncMentalPresencePayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.DOUBLE, SyncMentalPresencePayload::value,
			SyncMentalPresencePayload::new
	);
	
	public static void sendMentalPresenceSync(ServerPlayer player, double value) {
		PacketDistributor.sendToPlayer(player, new SyncMentalPresencePayload(value));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(SyncMentalPresencePayload payload, IPayloadContext context) {
		var player = context.player();
		MiscPlayerData.get(player).setLastSyncedSleepPotency(payload.value);
		DimensionRenderEffects.markForEffectUpdate();
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

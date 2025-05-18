package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.cca.MiscPlayerDataComponent;
import de.dafuqs.spectrum.deeper_down.DimensionRenderEffects;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

// TODO: Why tf is that a packet and not handled in the synced component instead?
public record SyncMentalPresencePayload(double value) implements CustomPacketPayload {
	
	public static final Type<SyncMentalPresencePayload> ID = SpectrumC2SPackets.makeId("sync_mental_presence");
	public static final StreamCodec<FriendlyByteBuf, SyncMentalPresencePayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.DOUBLE, SyncMentalPresencePayload::value,
			SyncMentalPresencePayload::new
	);
	
	public static void sendMentalPresenceSync(ServerPlayer player, double value) {
		FriendlyByteBuf buf = PacketByteBufs.create();
		buf.writeDouble(value);
		ServerPlayNetworking.send(player, new SyncMentalPresencePayload(value));
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(SyncMentalPresencePayload payload, ClientPlayNetworking.Context context) {
		LocalPlayer player = context.player();
		MiscPlayerDataComponent.get(player).setLastSyncedSleepPotency(payload.value);
		DimensionRenderEffects.markForEffectUpdate();
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.SpectrumClient;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

public record StartSkyLerpingPayload(long startTime, long endTime) implements CustomPacketPayload {
	
	public static final Type<StartSkyLerpingPayload> ID = SpectrumC2SPackets.makeId("start_sky_lerping");
	public static final StreamCodec<FriendlyByteBuf, StartSkyLerpingPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_LONG, StartSkyLerpingPayload::startTime,
			ByteBufCodecs.VAR_LONG, StartSkyLerpingPayload::endTime,
			StartSkyLerpingPayload::new
	);
	
	public static void startSkyLerping(@NotNull ServerLevel serverWorld, int additionalTime) {
		long timeOfDay = serverWorld.getDayTime();
		for (ServerPlayer player : serverWorld.players()) {
			ServerPlayNetworking.send(player, new StartSkyLerpingPayload(timeOfDay, timeOfDay + additionalTime));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(StartSkyLerpingPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		DimensionType dimensionType = client.level.dimensionType();
		
		SpectrumClient.skyLerper.trigger(dimensionType, payload.startTime, client.getTimer().getGameTimeDeltaPartialTick(false), payload.endTime);
		if (client.level.canSeeSky(client.player.blockPosition())) {
			client.level.playSound(null, client.player.blockPosition(), SpectrumSoundEvents.CELESTIAL_POCKET_WATCH_FLY_BY, SoundSource.NEUTRAL, 0.15F, 1.0F);
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.world.dimension.*;
import org.jetbrains.annotations.*;

public record StartSkyLerpingPayload(long startTime, long endTime) implements CustomPayload {
	
	public static final Id<StartSkyLerpingPayload> ID = SpectrumC2SPackets.makeId("start_sky_lerping");
	public static final PacketCodec<PacketByteBuf, StartSkyLerpingPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.VAR_LONG, StartSkyLerpingPayload::startTime,
			PacketCodecs.VAR_LONG, StartSkyLerpingPayload::endTime,
			StartSkyLerpingPayload::new
	);
	
	public static void startSkyLerping(@NotNull ServerWorld serverWorld, int additionalTime) {
		long timeOfDay = serverWorld.getTimeOfDay();
		for (ServerPlayerEntity player : serverWorld.getPlayers()) {
			ServerPlayNetworking.send(player, new StartSkyLerpingPayload(timeOfDay, timeOfDay + additionalTime));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(StartSkyLerpingPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		DimensionType dimensionType = client.world.getDimension();
		
		SpectrumClient.skyLerper.trigger(dimensionType, payload.startTime, client.getRenderTickCounter().getTickDelta(false), payload.endTime);
		if (client.world.isSkyVisible(client.player.getBlockPos())) {
			client.world.playSound(null, client.player.getBlockPos(), SpectrumSoundEvents.CELESTIAL_POCKET_WATCH_FLY_BY, SoundCategory.NEUTRAL, 0.15F, 1.0F);
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

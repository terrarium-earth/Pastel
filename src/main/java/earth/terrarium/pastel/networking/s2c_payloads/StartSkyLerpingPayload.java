package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.PastelClient;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record StartSkyLerpingPayload(long startTime, long endTime) implements CustomPacketPayload {

    public static final Type<StartSkyLerpingPayload> ID = PastelC2SPackets.makeId("start_sky_lerping");
    public static final StreamCodec<FriendlyByteBuf, StartSkyLerpingPayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_LONG, StartSkyLerpingPayload::startTime,
        ByteBufCodecs.VAR_LONG, StartSkyLerpingPayload::endTime,
        StartSkyLerpingPayload::new
    );

    public static void startSkyLerping(@NotNull ServerLevel serverWorld, int additionalTime) {
        long timeOfDay = serverWorld.getDayTime();
        PacketDistributor.sendToPlayersInDimension(
            serverWorld, new StartSkyLerpingPayload(timeOfDay, timeOfDay + additionalTime));
    }

    @SuppressWarnings("resource")
    public static void execute(StartSkyLerpingPayload payload, IPayloadContext context) {
        var client = Minecraft.getInstance();
        Level level = context.player()
                             .level();
        DimensionType dimensionType = level.dimensionType();

        PastelClient.skyLerper.trigger(
            dimensionType, payload.startTime, client.getTimer()
                                                    .getGameTimeDeltaPartialTick(false), payload.endTime
        );
        if (level.canSeeSky(client.player.blockPosition())) {
            level.playSound(
                null, client.player.blockPosition(), PastelSoundEvents.CELESTIAL_POCKET_WATCH_FLY_BY,
                SoundSource.NEUTRAL, 0.15F, 1.0F
            );
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

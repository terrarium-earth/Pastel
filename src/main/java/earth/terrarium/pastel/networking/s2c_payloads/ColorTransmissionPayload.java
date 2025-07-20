package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.particle.effect.ColoredTransmission;
import earth.terrarium.pastel.particle.effect.ColoredTransmissionParticleEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ColorTransmissionPayload(BlockPos pos, ColoredTransmission transmission) implements CustomPacketPayload {

    public static final Type<ColorTransmissionPayload> ID = PastelC2SPackets.makeId("color_transmission");
    public static final StreamCodec<RegistryFriendlyByteBuf, ColorTransmissionPayload> CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, ColorTransmissionPayload::pos,
        ColoredTransmission.STREAM_CODEC, ColorTransmissionPayload::transmission,
        ColorTransmissionPayload::new
    );

    public static void playColorTransmissionParticle(ServerLevel world, @NotNull ColoredTransmission transmission) {
        var pos = BlockPos.containing(transmission.getOrigin());
        PacketDistributor.sendToPlayersTrackingChunk(
            world, new ChunkPos(pos), new ColorTransmissionPayload(pos, transmission));
    }

    @SuppressWarnings("resource")
    public static void execute(ColorTransmissionPayload payload, IPayloadContext context) {
        var level = context.player()
                           .level();
        ColoredTransmission transmission = payload.transmission;
        level.addAlwaysVisibleParticle(
            new ColoredTransmissionParticleEffect(
                transmission.getDestination(), transmission.getArrivalInTicks(),
                transmission.getDyeColor()
            ), true, transmission.getOrigin()
                                 .x(), transmission.getOrigin()
                                                   .y(), transmission.getOrigin()
                                                                     .z(), 0.0D, 0.0D, 0.0D
        );
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

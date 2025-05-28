package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerConfiguration;
import earth.terrarium.pastel.inventories.ParticleSpawnerScreenHandler;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import earth.terrarium.pastel.networking.s2c_payloads.ParticleSpawnerConfigurationS2CPayload;

import net.minecraft.core.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.*;

public record ParticleSpawnerConfigurationC2SPayload(
        ParticleSpawnerConfiguration configuration) implements CustomPacketPayload {
    
    public static final CustomPacketPayload.Type<ParticleSpawnerConfigurationC2SPayload> ID = SpectrumC2SPackets.makeId("change_particle_spawner_settings");
    public static final StreamCodec<FriendlyByteBuf, ParticleSpawnerConfigurationC2SPayload> CODEC = StreamCodec.composite(
            ParticleSpawnerConfiguration.STREAM_CODEC,
            ParticleSpawnerConfigurationC2SPayload::configuration,
            ParticleSpawnerConfigurationC2SPayload::new
    );
    
    public static IPayloadHandler<ParticleSpawnerConfigurationC2SPayload> getPayloadHandler() {
        return (packet, context) -> {
            // receive the client packet...
            if (context.player().containerMenu instanceof ParticleSpawnerScreenHandler particleSpawnerScreenHandler) {
                ParticleSpawnerBlockEntity blockEntity = particleSpawnerScreenHandler.getBlockEntity();
                if (blockEntity != null) {
                    // ...apply the new settings...
                    blockEntity.applySettings(packet.configuration());
                    
                    // ...and distribute it to all clients again
                    // Iterate over all players tracking a position in the world and send the packet to each player
                    PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) context.player().level(), new ChunkPos(particleSpawnerScreenHandler.getBlockEntity().getBlockPos()), new ParticleSpawnerConfigurationS2CPayload(blockEntity.getBlockPos(), blockEntity.getConfiguration()));
                }
            }
        };
    }
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

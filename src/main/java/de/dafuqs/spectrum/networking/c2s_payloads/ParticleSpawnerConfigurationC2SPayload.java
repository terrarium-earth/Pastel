package de.dafuqs.spectrum.networking.c2s_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerConfiguration;
import de.dafuqs.spectrum.inventories.ParticleSpawnerScreenHandler;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.networking.s2c_payloads.ParticleSpawnerConfigurationS2CPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public record ParticleSpawnerConfigurationC2SPayload(
        ParticleSpawnerConfiguration configuration) implements CustomPacketPayload {
    
    public static final CustomPacketPayload.Type<ParticleSpawnerConfigurationC2SPayload> ID = SpectrumC2SPackets.makeId("change_particle_spawner_settings");
    public static final StreamCodec<FriendlyByteBuf, ParticleSpawnerConfigurationC2SPayload> CODEC = StreamCodec.composite(
            ParticleSpawnerConfiguration.PACKET_CODEC,
            ParticleSpawnerConfigurationC2SPayload::configuration,
            ParticleSpawnerConfigurationC2SPayload::new
    );
    
    public static ServerPlayNetworking.PlayPayloadHandler<ParticleSpawnerConfigurationC2SPayload> getPayloadHandler() {
        return (packet, context) -> {
            // receive the client packet...
            if (context.player().containerMenu instanceof ParticleSpawnerScreenHandler particleSpawnerScreenHandler) {
                ParticleSpawnerBlockEntity blockEntity = particleSpawnerScreenHandler.getBlockEntity();
                if (blockEntity != null) {
                    // ...apply the new settings...
                    blockEntity.applySettings(packet.configuration());
                    
                    // ...and distribute it to all clients again
                    // Iterate over all players tracking a position in the world and send the packet to each player
                    for (ServerPlayer serverPlayerEntity : PlayerLookup.tracking((ServerLevel) blockEntity.getLevel(), blockEntity.getBlockPos())) {
                        ServerPlayNetworking.send(serverPlayerEntity, new ParticleSpawnerConfigurationS2CPayload(blockEntity.getBlockPos(), blockEntity.getConfiguration()));
                    }
                }
            }
        };
    }
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

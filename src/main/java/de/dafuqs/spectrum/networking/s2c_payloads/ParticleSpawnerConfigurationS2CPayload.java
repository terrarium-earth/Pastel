package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerConfiguration;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ParticleSpawnerConfigurationS2CPayload(BlockPos pos, ParticleSpawnerConfiguration configuration) implements CustomPacketPayload {
	
	public static final Type<ParticleSpawnerConfigurationS2CPayload> ID = SpectrumC2SPackets.makeId("change_particle_spawner_settings_client");
	public static final StreamCodec<FriendlyByteBuf, ParticleSpawnerConfigurationS2CPayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, ParticleSpawnerConfigurationS2CPayload::pos,
			ParticleSpawnerConfiguration.PACKET_CODEC, ParticleSpawnerConfigurationS2CPayload::configuration,
			ParticleSpawnerConfigurationS2CPayload::new
	);
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(ParticleSpawnerConfigurationS2CPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		if (client.level.getBlockEntity(payload.pos()) instanceof ParticleSpawnerBlockEntity particleSpawnerBlockEntity) {
			particleSpawnerBlockEntity.applySettings(payload.configuration());
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

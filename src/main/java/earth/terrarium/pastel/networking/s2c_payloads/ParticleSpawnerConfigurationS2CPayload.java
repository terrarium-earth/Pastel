package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerConfiguration;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ParticleSpawnerConfigurationS2CPayload(BlockPos pos, ParticleSpawnerConfiguration configuration) implements CustomPacketPayload {
	
	public static final Type<ParticleSpawnerConfigurationS2CPayload> ID = PastelC2SPackets.makeId("change_particle_spawner_settings_client");
	public static final StreamCodec<FriendlyByteBuf, ParticleSpawnerConfigurationS2CPayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, ParticleSpawnerConfigurationS2CPayload::pos,
			ParticleSpawnerConfiguration.STREAM_CODEC, ParticleSpawnerConfigurationS2CPayload::configuration,
			ParticleSpawnerConfigurationS2CPayload::new
	);
	
	@SuppressWarnings("resource")
	public static void execute(ParticleSpawnerConfigurationS2CPayload payload, IPayloadContext context) {
		if (context.player().level().getBlockEntity(payload.pos()) instanceof ParticleSpawnerBlockEntity particleSpawnerBlockEntity) {
			particleSpawnerBlockEntity.applySettings(payload.configuration());
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

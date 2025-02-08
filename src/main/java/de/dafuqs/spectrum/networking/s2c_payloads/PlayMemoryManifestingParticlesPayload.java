package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.memory.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.*;
import org.joml.*;

public record PlayMemoryManifestingParticlesPayload(BlockPos pos, int eggColor1, int eggColor2, int amount) implements CustomPayload {
	
	public static final Id<PlayMemoryManifestingParticlesPayload> ID = SpectrumC2SPackets.makeId("play_memory_manifesting_particles");
	public static final PacketCodec<PacketByteBuf, PlayMemoryManifestingParticlesPayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, PlayMemoryManifestingParticlesPayload::pos,
			PacketCodecs.INTEGER, PlayMemoryManifestingParticlesPayload::eggColor1,
			PacketCodecs.INTEGER, PlayMemoryManifestingParticlesPayload::eggColor2,
			PacketCodecs.INTEGER, PlayMemoryManifestingParticlesPayload::amount,
			PlayMemoryManifestingParticlesPayload::new
	);
	
	public static void playMemoryManifestingParticles(ServerWorld serverWorld, @NotNull BlockPos pos, EntityType<?> entityType, int amount) {
		Pair<Integer, Integer> eggColors = MemoryBlockEntity.getEggColorsForEntity(entityType);
		for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, pos)) {
			ServerPlayNetworking.send(player, new PlayMemoryManifestingParticlesPayload(pos, eggColors.getLeft(), eggColors.getRight(), amount));
		}
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayMemoryManifestingParticlesPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		Random random = client.world.random;
		
		Vector3f colorVec1 = SpectrumColorHelper.colorIntToVec(payload.eggColor1);
		Vector3f colorVec2 = SpectrumColorHelper.colorIntToVec(payload.eggColor1);
		
		BlockPos pos = payload.pos;
		for (int i = 0; i < payload.amount; i++) {
			int randomLifetime = 30 + random.nextInt(20);
			
			// color1
			client.world.addParticle(
					new DynamicParticleEffect(SpectrumParticleTypes.WHITE_CRAFTING, 0.5F, colorVec1, 1.0F, randomLifetime, false, true),
					pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ(),
					0.15 - random.nextFloat() * 0.3, random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3
			);
			
			// color2
			client.world.addParticle(
					new DynamicParticleEffect(SpectrumParticleTypes.WHITE_CRAFTING, 0.5F, colorVec2, 1.0F, randomLifetime, false, true),
					pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
					0.15 - random.nextFloat() * 0.3, random.nextFloat() * 0.15 + 0.1, 0.15 - random.nextFloat() * 0.3
			);
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

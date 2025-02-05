package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.blocks.pedestal.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public record PlayPedestalUpgradedParticlePayload(BlockPos pedestalPos, PedestalRecipeTier newTier) implements CustomPayload {
	
	public static final Id<PlayPedestalUpgradedParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_upgraded_particle");
	public static final PacketCodec<PacketByteBuf, PlayPedestalUpgradedParticlePayload> CODEC = PacketCodec.tuple(
			BlockPos.PACKET_CODEC, PlayPedestalUpgradedParticlePayload::pedestalPos,
			PedestalRecipeTier.PACKET_CODEC, PlayPedestalUpgradedParticlePayload::newTier,
			PlayPedestalUpgradedParticlePayload::new
	);
	
	public static void spawnPedestalUpgradeParticles(World world, BlockPos pedestalPos, @NotNull PedestalVariant newPedestalVariant) {
		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pedestalPos)) {
			ServerPlayNetworking.send(player, new PlayPedestalUpgradedParticlePayload(pedestalPos, newPedestalVariant.getRecipeTier()));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(PlayPedestalUpgradedParticlePayload payload, ClientPlayNetworking.Context context) {
		PedestalBlock.spawnUpgradeParticleEffectsForTier(payload.pedestalPos, payload.newTier);
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

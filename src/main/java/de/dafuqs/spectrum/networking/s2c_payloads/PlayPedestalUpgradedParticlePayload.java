package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.block.PedestalVariant;
import de.dafuqs.spectrum.blocks.pedestal.PedestalBlock;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.recipe.pedestal.PedestalRecipeTier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record PlayPedestalUpgradedParticlePayload(BlockPos pedestalPos, PedestalRecipeTier newTier) implements CustomPacketPayload {
	
	public static final Type<PlayPedestalUpgradedParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_upgraded_particle");
	public static final StreamCodec<FriendlyByteBuf, PlayPedestalUpgradedParticlePayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, PlayPedestalUpgradedParticlePayload::pedestalPos,
			PedestalRecipeTier.PACKET_CODEC, PlayPedestalUpgradedParticlePayload::newTier,
			PlayPedestalUpgradedParticlePayload::new
	);
	
	public static void spawnPedestalUpgradeParticles(Level world, BlockPos pedestalPos, @NotNull PedestalVariant newPedestalVariant) {
		for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) world, pedestalPos)) {
			ServerPlayNetworking.send(player, new PlayPedestalUpgradedParticlePayload(pedestalPos, newPedestalVariant.getRecipeTier()));
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayPedestalUpgradedParticlePayload payload, ClientPlayNetworking.Context context) {
		PedestalBlock.spawnUpgradeParticleEffectsForTier(payload.pedestalPos, payload.newTier);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.api.block.PedestalVariant;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlock;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipeTier;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public record PlayPedestalUpgradedParticlePayload(BlockPos pedestalPos, PedestalRecipeTier newTier) implements CustomPacketPayload {
	
	public static final Type<PlayPedestalUpgradedParticlePayload> ID = SpectrumC2SPackets.makeId("play_pedestal_upgraded_particle");
	public static final StreamCodec<FriendlyByteBuf, PlayPedestalUpgradedParticlePayload> CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC, PlayPedestalUpgradedParticlePayload::pedestalPos,
			PedestalRecipeTier.STREAM_CODEC, PlayPedestalUpgradedParticlePayload::newTier,
			PlayPedestalUpgradedParticlePayload::new
	);
	
	public static void spawnPedestalUpgradeParticles(Level world, BlockPos pedestalPos, @NotNull PedestalVariant newPedestalVariant) {
		PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) world, new ChunkPos(pedestalPos), new PlayPedestalUpgradedParticlePayload(pedestalPos, newPedestalVariant.getRecipeTier()));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayPedestalUpgradedParticlePayload payload, IPayloadContext context) {
		PedestalBlock.spawnUpgradeParticleEffectsForTier(payload.pedestalPos, payload.newTier);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

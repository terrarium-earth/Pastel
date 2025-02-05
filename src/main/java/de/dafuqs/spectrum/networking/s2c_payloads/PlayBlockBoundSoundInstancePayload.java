package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.sound.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

// MaxDurationTicks of <1 means "stop playing"
public record PlayBlockBoundSoundInstancePayload(SoundEvent soundEvent, BlockPos pos, RegistryEntry<Block> block, int maxDurationTicks) implements CustomPayload {
	
	public static final Id<PlayBlockBoundSoundInstancePayload> ID = SpectrumC2SPackets.makeId("play_block_bound_sound_instance");
	public static final PacketCodec<RegistryByteBuf, PlayBlockBoundSoundInstancePayload> CODEC = PacketCodec.tuple(
			SoundEvent.PACKET_CODEC, PlayBlockBoundSoundInstancePayload::soundEvent,
			BlockPos.PACKET_CODEC, PlayBlockBoundSoundInstancePayload::pos,
			PacketCodecs.registryEntry(RegistryKeys.BLOCK), PlayBlockBoundSoundInstancePayload::block,
			PacketCodecs.INTEGER, PlayBlockBoundSoundInstancePayload::maxDurationTicks,
			PlayBlockBoundSoundInstancePayload::new
	);
	
	public static void sendPlayBlockBoundSoundInstance(SoundEvent soundEvent, @NotNull ServerWorld world, BlockPos pos, int maxDurationTicks) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
			ServerPlayNetworking.send(player, new PlayBlockBoundSoundInstancePayload(soundEvent, pos, world.getBlockState(pos).getBlock().getRegistryEntry(), maxDurationTicks));
		}
	}
	
	public static void sendCancelBlockBoundSoundInstance(@NotNull ServerWorld world, BlockPos pos) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(world, pos)) {
			ServerPlayNetworking.send(player, new PlayBlockBoundSoundInstancePayload(SoundEvents.INTENTIONALLY_EMPTY, pos, world.getBlockState(pos).getBlock().getRegistryEntry(), -1));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(PlayBlockBoundSoundInstancePayload payload, ClientPlayNetworking.Context context) {
		if (payload.maxDurationTicks < 0) {
			CraftingBlockSoundInstance.stopPlayingOnPos(payload.pos);
		} else {
			CraftingBlockSoundInstance.startSoundInstance(payload.soundEvent, payload.pos, payload.block.value(), payload.maxDurationTicks);
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

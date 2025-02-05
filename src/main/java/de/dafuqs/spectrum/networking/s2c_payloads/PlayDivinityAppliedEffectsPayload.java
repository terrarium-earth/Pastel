package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;

public record PlayDivinityAppliedEffectsPayload() implements CustomPayload {
	
	public static final Id<PlayDivinityAppliedEffectsPayload> ID = SpectrumC2SPackets.makeId("play_divinity_applied_effects");
	public static final PacketCodec<PacketByteBuf, PlayDivinityAppliedEffectsPayload> CODEC = PacketCodec.ofStatic((buf, value) -> {
	}, buf -> new PlayDivinityAppliedEffectsPayload());
	
	public static void playDivinityAppliedEffects(ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new PlayDivinityAppliedEffectsPayload());
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayDivinityAppliedEffectsPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		PlayerEntity player = client.player;
		client.particleManager.addEmitter(player, SpectrumParticleTypes.DIVINITY, 30);
		client.gameRenderer.showFloatingItem(SpectrumItems.DIVINATION_HEART.getDefaultStack());
		client.world.playSound(null, player.getBlockPos(), SpectrumSoundEvents.FAILING_PLACED, SoundCategory.PLAYERS, 1.0F, 1.0F);
		ParticleHelper.playParticleWithPatternAndVelocityClient(player.getWorld(), player.getPos(), SpectrumParticleTypes.WHITE_CRAFTING, VectorPattern.SIXTEEN, 0.4);
		ParticleHelper.playParticleWithPatternAndVelocityClient(player.getWorld(), player.getPos(), SpectrumParticleTypes.RED_CRAFTING, VectorPattern.SIXTEEN, 0.4);
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}

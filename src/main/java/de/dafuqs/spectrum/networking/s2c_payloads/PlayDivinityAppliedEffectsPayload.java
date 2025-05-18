package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.ParticleHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.particle.VectorPattern;
import de.dafuqs.spectrum.particle.effect.ColoredCraftingParticleEffect;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public record PlayDivinityAppliedEffectsPayload() implements CustomPacketPayload {
	
	public static final Type<PlayDivinityAppliedEffectsPayload> ID = SpectrumC2SPackets.makeId("play_divinity_applied_effects");
	public static final StreamCodec<FriendlyByteBuf, PlayDivinityAppliedEffectsPayload> CODEC = StreamCodec.of((buf, value) -> {
	}, buf -> new PlayDivinityAppliedEffectsPayload());
	
	public static void playDivinityAppliedEffects(ServerPlayer player) {
		ServerPlayNetworking.send(player, new PlayDivinityAppliedEffectsPayload());
	}
	
	@SuppressWarnings("resource")
	@Environment(EnvType.CLIENT)
	public static void execute(PlayDivinityAppliedEffectsPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		Player player = client.player;
		client.particleEngine.createTrackingEmitter(player, SpectrumParticleTypes.DIVINITY, 30);
		client.gameRenderer.displayItemActivation(SpectrumItems.DIVINATION_HEART.getDefaultInstance());
		client.level.playSound(null, player.blockPosition(), SpectrumSoundEvents.FAILING_PLACED, SoundSource.PLAYERS, 1.0F, 1.0F);
		ParticleHelper.playParticleWithPatternAndVelocityClient(player.level(), player.position(), ColoredCraftingParticleEffect.WHITE, VectorPattern.SIXTEEN, 0.4);
		ParticleHelper.playParticleWithPatternAndVelocityClient(player.level(), player.position(), ColoredCraftingParticleEffect.RED, VectorPattern.SIXTEEN, 0.4);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

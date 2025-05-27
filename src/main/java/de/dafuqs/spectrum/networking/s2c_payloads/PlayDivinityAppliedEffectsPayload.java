package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.helpers.ParticleHelper;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.particle.VectorPattern;
import de.dafuqs.spectrum.particle.effect.ColoredCraftingParticleEffect;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;
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
		PacketDistributor.sendToPlayer(player, new PlayDivinityAppliedEffectsPayload());
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayDivinityAppliedEffectsPayload payload, IPayloadContext context) {
		Player player = context.player();
		var level = player.level();
		var client = Minecraft.getInstance();
		client.particleEngine.createTrackingEmitter(player, SpectrumParticleTypes.DIVINITY, 30);
		client.gameRenderer.displayItemActivation(SpectrumItems.DIVINATION_HEART.get().getDefaultInstance());
		level.playSound(null, player.blockPosition(), SpectrumSoundEvents.FAILING_PLACED, SoundSource.PLAYERS, 1.0F, 1.0F);
		ParticleHelper.playParticleWithPatternAndVelocityClient(level, player.position(), ColoredCraftingParticleEffect.WHITE, VectorPattern.SIXTEEN, 0.4);
		ParticleHelper.playParticleWithPatternAndVelocityClient(level, player.position(), ColoredCraftingParticleEffect.RED, VectorPattern.SIXTEEN, 0.4);
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

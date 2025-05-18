package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import de.dafuqs.spectrum.sound.DivinitySoundInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;

public record PlayAscensionAppliedEffectsPayload() implements CustomPacketPayload {
	
	public static final Type<PlayAscensionAppliedEffectsPayload> ID = SpectrumC2SPackets.makeId("play_ascension_applied_effects");
	public static final StreamCodec<FriendlyByteBuf, PlayAscensionAppliedEffectsPayload> CODEC = StreamCodec.of((buf, value) -> {
	}, buf -> new PlayAscensionAppliedEffectsPayload());
	
	public static void playAscensionAppliedEffects(ServerPlayer player) {
		ServerPlayNetworking.send(player, new PlayAscensionAppliedEffectsPayload());
	}
	
	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	public static void execute(PlayAscensionAppliedEffectsPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		client.level.playSound(null, client.player.blockPosition(), SpectrumSoundEvents.FADING_PLACED, SoundSource.PLAYERS, 1.0F, 1.0F);
		client.getSoundManager().play(new DivinitySoundInstance());
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}

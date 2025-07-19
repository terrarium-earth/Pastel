package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.helpers.render.ParticleHelper;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSoundEvents;
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

    public static final Type<PlayDivinityAppliedEffectsPayload> ID = PastelC2SPackets.makeId(
        "play_divinity_applied_effects");
    public static final StreamCodec<FriendlyByteBuf, PlayDivinityAppliedEffectsPayload> CODEC = StreamCodec.of(
        (buf, value) -> {
        }, buf -> new PlayDivinityAppliedEffectsPayload()
    );

    public static void playDivinityAppliedEffects(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new PlayDivinityAppliedEffectsPayload());
    }

    public static void execute(PlayDivinityAppliedEffectsPayload payload, IPayloadContext context) {
        execute(context.player());
    }

    @OnlyIn(Dist.CLIENT)
    private static void execute(Player player) {
        var level = player.level();
        var client = Minecraft.getInstance();
        client.particleEngine.createTrackingEmitter(player, PastelParticleTypes.DIVINITY, 30);
        client.gameRenderer.displayItemActivation(PastelItems.DIVINATION_HEART.get()
                                                                              .getDefaultInstance());
        level.playSound(
            null, player.blockPosition(), PastelSoundEvents.FAILING_PLACED, SoundSource.PLAYERS, 1.0F, 1.0F);
        ParticleHelper.playParticleWithPatternAndVelocityClient(
            level, player.position(), ColoredCraftingParticleEffect.WHITE, VectorPattern.SIXTEEN, 0.4);
        ParticleHelper.playParticleWithPatternAndVelocityClient(
            level, player.position(), ColoredCraftingParticleEffect.RED, VectorPattern.SIXTEEN, 0.4);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

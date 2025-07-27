package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.registries.PastelSounds;
import earth.terrarium.pastel.sound.DivinitySoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PlayAscensionAppliedEffectsPayload() implements CustomPacketPayload {

    public static final Type<PlayAscensionAppliedEffectsPayload> ID = PastelC2SPackets.makeId(
        "play_ascension_applied_effects");
    public static final StreamCodec<FriendlyByteBuf, PlayAscensionAppliedEffectsPayload> CODEC = StreamCodec.of(
        (buf, value) -> {
        }, buf -> new PlayAscensionAppliedEffectsPayload()
    );

    public static void playAscensionAppliedEffects(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new PlayAscensionAppliedEffectsPayload());
    }

    public static void execute(PlayAscensionAppliedEffectsPayload payload, IPayloadContext context) {
        execute(context.player());
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    private static void execute(Player player) {
        var level = player.level();
        level.playSound(null, player.blockPosition(), PastelSounds.FADING_PLACED, SoundSource.PLAYERS, 1.0F, 1.0F);
        Minecraft.getInstance()
                 .getSoundManager()
                 .play(new DivinitySoundInstance());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

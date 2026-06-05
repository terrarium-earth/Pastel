package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.components.PaintbrushComponent;
import earth.terrarium.pastel.items.magic_items.PaintbrushItem;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record PaintbrushModeSwitchPayload(int index) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PaintbrushModeSwitchPayload> TYPE = PastelC2SPackets.makeId(
        "paintbrush_mode_switch");
    public static final StreamCodec<ByteBuf, PaintbrushModeSwitchPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, PaintbrushModeSwitchPayload::index, PaintbrushModeSwitchPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static IPayloadHandler<PaintbrushModeSwitchPayload> getPayloadHandler() {
        return (customPacketPayload, iPayloadContext) -> {
            var player = iPayloadContext.player();
            if (!player.getItemInHand(InteractionHand.MAIN_HAND)
                       .is(PastelItems.PAINTBRUSH)) return;
            var stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            var component = stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT);
            switch (customPacketPayload.index) {
                case 0 -> stack.set(
                    PastelDataComponentTypes.PAINTBRUSH, new PaintbrushComponent(
                        PaintbrushComponent.PaintbrushMode.PAINT, component.color(), component.brown(),
                        component.greenPos(), component.greenDim()
                    )
                );
                case 1 -> stack.set(
                    PastelDataComponentTypes.PAINTBRUSH, new PaintbrushComponent(
                        PaintbrushComponent.PaintbrushMode.SPELL, component.color(), component.brown(),
                        component.greenPos(), component.greenDim()
                    )
                );
                case 2 -> stack.set(
                    PastelDataComponentTypes.PAINTBRUSH, new PaintbrushComponent(
                        PaintbrushComponent.PaintbrushMode.INFO, component.color(), component.brown(),
                        component.greenPos(), component.greenDim()
                    )
                );
            }
            player.playSound(PastelSounds.CAST_RADIANCE);
        };
    }
}

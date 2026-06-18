package earth.terrarium.pastel.networking.c2s_payloads;

import earth.terrarium.pastel.components.ExchangingStaffComponent;
import earth.terrarium.pastel.components.PaintbrushComponent;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import static earth.terrarium.pastel.items.magic_items.ExchangeStaffItem.MAX_RANGE;

public record ExchangingStaffAdjustPayload(int index) implements CustomPacketPayload {
    public static final Type<ExchangingStaffAdjustPayload> TYPE = PastelC2SPackets
        .makeId(
            "exchanging_staff_adjust"
        );

    public static final StreamCodec<ByteBuf, ExchangingStaffAdjustPayload> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.INT,
            ExchangingStaffAdjustPayload::index,
            ExchangingStaffAdjustPayload::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static IPayloadHandler<ExchangingStaffAdjustPayload> getPayloadHandler() {
        return (customPacketPayload, iPayloadContext) -> {
            var player = iPayloadContext.player();
            if (!player
                .getItemInHand(InteractionHand.MAIN_HAND)
                .is(PastelItems.EXCHANGING_STAFF)) return;
            var stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            stack
                .set(
                    PastelDataComponentTypes.EXCHANGING_STAFF,
                    new ExchangingStaffComponent((customPacketPayload.index + 1) % (MAX_RANGE + 1))
                );
            player.playSound(PastelSounds.CAST_RADIANCE);
        };
    }
}

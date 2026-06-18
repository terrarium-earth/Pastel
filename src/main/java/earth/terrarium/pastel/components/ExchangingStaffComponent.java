package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.items.magic_items.ExchangeStaffItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ExchangingStaffComponent(
    int range
) {
    public static final ExchangingStaffComponent DEFAULT = new ExchangingStaffComponent(
        ExchangeStaffItem.MAX_RANGE
    );

    public static final Codec<ExchangingStaffComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    Codec.INT.fieldOf("range").forGetter(ExchangingStaffComponent::range)
                )
                .apply(
                    i,
                    ExchangingStaffComponent::new
                )
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ExchangingStaffComponent> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.INT,
            ExchangingStaffComponent::range,
            ExchangingStaffComponent::new
        );
}

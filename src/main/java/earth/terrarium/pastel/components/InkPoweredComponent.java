package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.InkPoweredStatusEffectInstance;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record InkPoweredComponent(List<InkPoweredStatusEffectInstance> effects) {

    public static final InkPoweredComponent DEFAULT = new InkPoweredComponent(List.of());

    public static final Codec<InkPoweredComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    InkPoweredStatusEffectInstance.CODEC
                        .listOf()
                        .fieldOf("effects")
                        .forGetter(InkPoweredComponent::effects)
                )
                .apply(
                    i,
                    InkPoweredComponent::new
                )
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, InkPoweredComponent> STREAM_CODEC = StreamCodec
        .composite(
            InkPoweredStatusEffectInstance.STREAM_CODEC.apply(ByteBufCodecs.list()),
            InkPoweredComponent::effects,
            InkPoweredComponent::new
        );

}

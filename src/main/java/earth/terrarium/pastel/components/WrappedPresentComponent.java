package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.blocks.present.PresentBlock;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

import java.util.HashMap;
import java.util.Map;

public record WrappedPresentComponent(
    boolean wrapped,
    PresentBlock.WrappingPaper variant,
    Map<Integer, Integer> colors
) {

    public static final WrappedPresentComponent DEFAULT = new WrappedPresentComponent(
        false,
        PresentBlock.WrappingPaper.RED,
        Map.of()
    );

    public static final Codec<WrappedPresentComponent> CODEC = RecordCodecBuilder
        .create(
            instance -> instance
                .group(
                    Codec.BOOL
                        .optionalFieldOf("wrapped", false)
                        .forGetter(WrappedPresentComponent::wrapped),
                    StringRepresentable
                        .fromEnum(PresentBlock.WrappingPaper::values)
                        .fieldOf("variant")
                        .forGetter(WrappedPresentComponent::variant),
                    Codec
                        .unboundedMap(Codec.STRING.xmap(Integer::parseInt, Object::toString), ExtraCodecs.POSITIVE_INT)
                        .fieldOf("colors")
                        .forGetter(WrappedPresentComponent::colors)
                )
                .apply(
                    instance,
                    WrappedPresentComponent::new
                )
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, WrappedPresentComponent> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.BOOL,
            WrappedPresentComponent::wrapped,
            PacketCodecHelper.enumOf(PresentBlock.WrappingPaper::values),
            WrappedPresentComponent::variant,
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.VAR_INT, ByteBufCodecs.VAR_INT),
            WrappedPresentComponent::colors,
            WrappedPresentComponent::new
        );

}

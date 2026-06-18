package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record CustomPotionDataComponent(boolean unidentifiable, int additionalDrinkDuration)
    implements
    TooltipProvider {

    public static final CustomPotionDataComponent DEFAULT = new CustomPotionDataComponent(false, 0);

    public static final Codec<CustomPotionDataComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    Codec.BOOL
                        .fieldOf("unidentifiable")
                        .forGetter(c -> c.unidentifiable),
                    Codec.INT
                        .fieldOf("additional_drink_duration")
                        .forGetter(c -> c.additionalDrinkDuration)
                )
                .apply(
                    i,
                    CustomPotionDataComponent::new
                )
        );

    public static final StreamCodec<ByteBuf, CustomPotionDataComponent> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.BOOL,
            c -> c.unidentifiable,
            ByteBufCodecs.INT,
            c -> c.additionalDrinkDuration,
            CustomPotionDataComponent::new
        );

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        int additionalDrinkDuration = this.additionalDrinkDuration();
        if (additionalDrinkDuration > 0) {
            tooltip.accept(Component.translatable("item.pastel.potion.slower_to_drink"));
        } else if (additionalDrinkDuration < 0) {
            tooltip.accept(Component.translatable("item.pastel.potion.faster_to_drink"));
        }
    }

}

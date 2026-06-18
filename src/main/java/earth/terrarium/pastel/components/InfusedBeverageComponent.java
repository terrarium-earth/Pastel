package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record InfusedBeverageComponent(String variant, int color) implements TooltipProvider {

    public static final InfusedBeverageComponent DEFAULT = new InfusedBeverageComponent("unknown", 0xfff4c6cb);

    public static final Codec<InfusedBeverageComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    Codec.STRING
                        .optionalFieldOf("variant", "unknown")
                        .forGetter(InfusedBeverageComponent::variant),
                    ColorHelper.CODEC
                        .optionalFieldOf("color", 0xfff4c6cb)
                        .forGetter(InfusedBeverageComponent::color)
                )
                .apply(
                    i,
                    InfusedBeverageComponent::new
                )
        );

    public static final StreamCodec<ByteBuf, InfusedBeverageComponent> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.STRING_UTF8,
            InfusedBeverageComponent::variant,
            ByteBufCodecs.VAR_INT,
            InfusedBeverageComponent::color,
            InfusedBeverageComponent::new
        );

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        tooltip
            .accept(
                Component
                    .translatable("item.pastel.infused_beverage.tooltip.variant." + variant)
                    .withStyle(ChatFormatting.YELLOW)
            );
    }

}

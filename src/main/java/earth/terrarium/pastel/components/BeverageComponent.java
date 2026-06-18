package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record BeverageComponent(long daysAged, int alcoholPercent, float thickness) implements TooltipProvider {

    public static final BeverageComponent DEFAULT = new BeverageComponent(0, 0, 0);

    public static final Codec<BeverageComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    Codec.LONG
                        .optionalFieldOf("days_aged", 0L)
                        .forGetter(BeverageComponent::daysAged),
                    Codec.INT
                        .optionalFieldOf("alcohol_percent", 0)
                        .forGetter(BeverageComponent::alcoholPercent),
                    Codec.FLOAT
                        .optionalFieldOf("thickness", 0f)
                        .forGetter(BeverageComponent::thickness)
                )
                .apply(
                    i,
                    BeverageComponent::new
                )
        );

    public static final StreamCodec<ByteBuf, BeverageComponent> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.VAR_LONG,
            BeverageComponent::daysAged,
            ByteBufCodecs.VAR_INT,
            BeverageComponent::alcoholPercent,
            ByteBufCodecs.FLOAT,
            BeverageComponent::thickness,
            BeverageComponent::new
        );

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        if (daysAged > 365) {
            long ageInDays = daysAged % 365;
            long ageInYears = Math.floorDiv(daysAged, 365);
            if (ageInDays == 0)
                tooltip
                    .accept(
                        Component
                            .translatable("item.pastel.infused_beverage.tooltip.age_years", ageInYears, alcoholPercent)
                            .withStyle(ChatFormatting.GRAY)
                    );
            else
                tooltip
                    .accept(
                        Component
                            .translatable(
                                "item.pastel.infused_beverage.tooltip.age_composite",
                                ageInYears,
                                ageInDays,
                                alcoholPercent
                            )
                            .withStyle(ChatFormatting.GRAY)
                    );
        } else {
            tooltip
                .accept(
                    Component
                        .translatable("item.pastel.infused_beverage.tooltip.age", daysAged, alcoholPercent)
                        .withStyle(ChatFormatting.GRAY)
                );
        }
    }

}

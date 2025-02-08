package de.dafuqs.spectrum.components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import io.netty.buffer.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.network.codec.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.function.*;

public record InfusedBeverageComponent(String variant, int color) implements TooltipAppender {
	
	public static final InfusedBeverageComponent DEFAULT = new InfusedBeverageComponent("unknown", 0xfff4c6cb);
	
	public static final Codec<InfusedBeverageComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.optionalFieldOf("variant", "unknown").forGetter(InfusedBeverageComponent::variant),
			SpectrumColorHelper.CODEC.optionalFieldOf("color", 0xfff4c6cb).forGetter(InfusedBeverageComponent::color)
	).apply(i, InfusedBeverageComponent::new));
	
	public static final PacketCodec<ByteBuf, InfusedBeverageComponent> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.STRING, InfusedBeverageComponent::variant,
			PacketCodecs.VAR_INT, InfusedBeverageComponent::color,
			InfusedBeverageComponent::new
	);
	
	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
		tooltip.accept(Text.translatable("item.spectrum.infused_beverage.tooltip.variant." + variant).formatted(Formatting.YELLOW));
	}
	
}

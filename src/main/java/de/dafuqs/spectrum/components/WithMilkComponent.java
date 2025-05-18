package de.dafuqs.spectrum.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

public record WithMilkComponent() implements TooltipProvider {
	
	public static final Codec<WithMilkComponent> CODEC = Codec.unit(WithMilkComponent::new);
	public static final StreamCodec<ByteBuf, WithMilkComponent> PACKET_CODEC = StreamCodec.unit(new WithMilkComponent());
	
	@Override
	public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
		tooltip.accept(Component.translatable("item.spectrum.restoration_tea.tooltip_milk"));
	}
	
}

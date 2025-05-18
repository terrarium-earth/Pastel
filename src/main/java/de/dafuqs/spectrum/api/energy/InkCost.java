package de.dafuqs.spectrum.api.energy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record InkCost(InkColor color, long cost) {
	
	public static final Codec<InkCost> CODEC = RecordCodecBuilder.create(i -> i.group(
			InkColor.CODEC.fieldOf("color").forGetter(InkCost::color),
			Codec.LONG.fieldOf("cost").forGetter(InkCost::cost)
	).apply(i, InkCost::new));
	
	public static final StreamCodec<ByteBuf, InkCost> PACKET_CODEC = StreamCodec.composite(
			InkColor.PACKET_CODEC, InkCost::color,
			ByteBufCodecs.VAR_LONG, InkCost::cost,
			InkCost::new
	);
	
}
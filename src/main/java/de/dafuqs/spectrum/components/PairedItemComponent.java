package de.dafuqs.spectrum.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record PairedItemComponent(long signature) {
	
	public static final Codec<PairedItemComponent> CODEC = Codec.LONG.xmap(PairedItemComponent::new, PairedItemComponent::signature);
	
	public static final StreamCodec<ByteBuf, PairedItemComponent> STREAM_CODEC = ByteBufCodecs.VAR_LONG.map(PairedItemComponent::new, PairedItemComponent::signature);
	
}

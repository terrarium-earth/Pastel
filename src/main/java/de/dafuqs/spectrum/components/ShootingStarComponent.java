package de.dafuqs.spectrum.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ShootingStarComponent(int remainingHits, boolean hardened) {
	
	public static final ShootingStarComponent DEFAULT = new ShootingStarComponent(5, false);
	
	public static final Codec<ShootingStarComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.INT.fieldOf("remaining_hits").forGetter(c -> c.remainingHits),
			Codec.BOOL.fieldOf("hardened").forGetter(c -> c.hardened)
	).apply(i, ShootingStarComponent::new));
	
	public static final StreamCodec<ByteBuf, ShootingStarComponent> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, c -> c.remainingHits,
			ByteBufCodecs.BOOL, c -> c.hardened,
			ShootingStarComponent::new
	);
	
}

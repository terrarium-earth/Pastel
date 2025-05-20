package de.dafuqs.spectrum.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record WorkstaffComponent(boolean canTill, boolean canShoot, int fortuneLevel) {
	
	public static final WorkstaffComponent DEFAULT = new WorkstaffComponent(true, false, 4);
	
	public static final Codec<WorkstaffComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.BOOL.fieldOf("can_till").forGetter(c -> c.canTill),
			Codec.BOOL.fieldOf("can_shoot").forGetter(c -> c.canShoot),
			Codec.INT.fieldOf("fortune_level").forGetter(c -> c.fortuneLevel)
	).apply(i, WorkstaffComponent::new));
	
	public static final StreamCodec<ByteBuf, WorkstaffComponent> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.BOOL,
			c -> c.canTill,
			ByteBufCodecs.BOOL,
			c -> c.canShoot,
			ByteBufCodecs.VAR_INT,
			c -> c.fortuneLevel,
			WorkstaffComponent::new
	);
	
}

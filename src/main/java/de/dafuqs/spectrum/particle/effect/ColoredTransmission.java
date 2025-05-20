package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class ColoredTransmission extends SimpleTransmission {
	
	public static final Codec<ColoredTransmission> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			Vec3.CODEC.fieldOf("origin").forGetter(c -> c.origin),
			PositionSource.CODEC.fieldOf("destination").forGetter(c -> c.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter(c -> c.arrivalInTicks),
			Codec.INT.fieldOf("color").forGetter(c -> c.color)
	).apply(instance, ColoredTransmission::new));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, ColoredTransmission> STREAM_CODEC = StreamCodec.composite(
			PacketCodecHelper.VEC3D, c -> c.origin,
			PositionSource.STREAM_CODEC, c -> c.destination,
			ByteBufCodecs.INT, c -> c.arrivalInTicks,
			ByteBufCodecs.INT, c -> c.color,
			ColoredTransmission::new
	);
	
	protected final int color;
	
	public ColoredTransmission(Vec3 origin, PositionSource destination, int arrivalInTicks, int color) {
		super(origin, destination, arrivalInTicks);
		this.color = color;
	}
	
	public int getDyeColor() {
		return this.color;
	}
	
}

package earth.terrarium.pastel.particle.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.particle.SpectrumParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.gameevent.PositionSource;

public class ColoredTransmissionParticleEffect extends TransmissionParticleEffect {
	
	public static final MapCodec<ColoredTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			PositionSource.CODEC.fieldOf("destination").forGetter((effect) -> effect.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter((effect) -> effect.arrivalInTicks),
			Codec.INT.fieldOf("color").forGetter((effect) -> effect.color)
	).apply(i, ColoredTransmissionParticleEffect::new));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, ColoredTransmissionParticleEffect> STREAM_CODEC = StreamCodec.composite(
			PositionSource.STREAM_CODEC, c -> c.destination,
			ByteBufCodecs.VAR_INT, c -> c.arrivalInTicks,
			ByteBufCodecs.INT, c -> c.color,
			ColoredTransmissionParticleEffect::new
	);
	
	public final int color;
	
	public ColoredTransmissionParticleEffect(PositionSource positionSource, Integer arrivalInTicks, int color) {
		super(SpectrumParticleTypes.COLORED_TRANSMISSION, positionSource, arrivalInTicks);
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
}

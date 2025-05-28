package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.CodecHelper;
import earth.terrarium.pastel.helpers.PacketCodecHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.apache.commons.lang3.math.Fraction;

public record ExtendedBundleComponent(Fraction maxOccupancy, int maxStacks, boolean ignoreStacks) {
	
	public static final ExtendedBundleComponent DEFAULT = new ExtendedBundleComponent(Fraction.ONE, 1, true);
	
	public static final Codec<ExtendedBundleComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			CodecHelper.FRACTION.fieldOf("max_occupancy").forGetter(c -> c.maxOccupancy),
			Codec.INT.fieldOf("max_stacks").forGetter(c -> c.maxStacks),
			Codec.BOOL.fieldOf("ignore_stacks").forGetter(c -> c.ignoreStacks)
	).apply(i, ExtendedBundleComponent::new));
	
	public static final StreamCodec<ByteBuf, ExtendedBundleComponent> STREAM_CODEC = StreamCodec.composite(
			PacketCodecHelper.FRACTION, c -> c.maxOccupancy,
			ByteBufCodecs.VAR_INT, c -> c.maxStacks,
			ByteBufCodecs.BOOL, c -> c.ignoreStacks,
			ExtendedBundleComponent::new
	);
	
	public ExtendedBundleComponent(int maxStacks) {
		this(Fraction.getFraction(maxStacks, 1), maxStacks, false);
	}
	
}

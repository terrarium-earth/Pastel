package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.InkStorage;
import earth.terrarium.pastel.api.energy.color.InkColor;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.HashMap;
import java.util.Map;

public record InkStorageComponent(long maxEnergyTotal, long maxPerColor, Map<InkColor, Long> storedEnergy) {
	
	public static final Codec<InkStorageComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.LONG.fieldOf("max_energy_total").forGetter(c -> c.maxEnergyTotal),
			Codec.LONG.fieldOf("max_per_color").forGetter(c -> c.maxPerColor),
			ExtraCodecs.strictUnboundedMap(InkColor.CODEC, Codec.LONG).fieldOf("stored_energy").forGetter(c -> c.storedEnergy)
	).apply(instance, InkStorageComponent::new));
	
	public static final StreamCodec<ByteBuf, InkStorageComponent> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_LONG, c -> c.maxEnergyTotal,
			ByteBufCodecs.VAR_LONG, c -> c.maxPerColor,
			ByteBufCodecs.map(HashMap::new, InkColor.STREAM_CODEC, ByteBufCodecs.VAR_LONG), c -> c.storedEnergy,
			InkStorageComponent::new
	);
	
	public InkStorageComponent(InkStorage storage) {
		this(storage.getMaxTotal(), storage.getMaxPerColor(), storage.getEnergy());
	}
	
}

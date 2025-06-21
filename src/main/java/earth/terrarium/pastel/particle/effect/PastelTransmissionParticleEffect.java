package earth.terrarium.pastel.particle.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;

public record PastelTransmissionParticleEffect(List<BlockPos> nodePositions, ItemStack stack, int travelTime, int color) implements ParticleOptions {
	
	public static final MapCodec<PastelTransmissionParticleEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			BlockPos.CODEC.listOf().fieldOf("positions").forGetter((particleEffect) -> particleEffect.nodePositions),
			ItemStack.CODEC.fieldOf("stack").forGetter((effect) -> effect.stack),
			Codec.INT.fieldOf("travel_time").forGetter((particleEffect) -> particleEffect.travelTime),
			Codec.INT.fieldOf("color").forGetter((particleEffect) -> particleEffect.color)
	).apply(i, PastelTransmissionParticleEffect::new));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, PastelTransmissionParticleEffect> STREAM_CODEC = StreamCodec.composite(
			BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), c -> c.nodePositions,
			ItemStack.STREAM_CODEC, c -> c.stack,
			ByteBufCodecs.VAR_INT, c -> c.travelTime,
			ByteBufCodecs.VAR_INT, c -> c.color,
			PastelTransmissionParticleEffect::new
	);
	
	@Override
	public ParticleType<PastelTransmissionParticleEffect> getType() {
		return PastelParticleTypes.PASTEL_TRANSMISSION;
	}
	
	@Override
	public String toString() {
		int nodeCount = this.nodePositions.size();
		BlockPos source = this.nodePositions.getFirst();
		BlockPos destination = this.nodePositions.getLast();
		int d = source.getX();
		int e = source.getY();
		int f = source.getZ();
		int g = destination.getX();
		int h = destination.getY();
		int i = destination.getZ();
		return String.format(Locale.ROOT, "%s %d %d %d %d %d %d %d %d %d", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.travelTime, nodeCount, d, e, f, g, h, i, this.color);
	}
	
}

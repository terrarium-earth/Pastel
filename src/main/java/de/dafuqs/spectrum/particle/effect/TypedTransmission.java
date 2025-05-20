package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.Vec3;

public class TypedTransmission extends SimpleTransmission {
	
	public enum Variant implements StringRepresentable {
		BLOCK_POS,
		ITEM,
		EXPERIENCE,
		REDSTONE,
		HUMMINGSTONE;
		
		@Override
		public String getSerializedName() {
			return name();
		}
	}
	
	public static final Codec<TypedTransmission> CODEC = RecordCodecBuilder.create(i -> i.group(
			Vec3.CODEC.fieldOf("origin").forGetter(c -> c.origin),
			PositionSource.CODEC.fieldOf("destination").forGetter(c -> c.destination),
			Codec.INT.fieldOf("arrival_in_ticks").forGetter(c -> c.arrivalInTicks),
			StringRepresentable.fromEnum(Variant::values).fieldOf("variant").forGetter(c -> c.variant)
	).apply(i, TypedTransmission::new));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, TypedTransmission> STREAM_CODEC = StreamCodec.composite(
			PacketCodecHelper.VEC3D, c -> c.origin,
			PositionSource.STREAM_CODEC, c -> c.destination,
			ByteBufCodecs.INT, c -> c.arrivalInTicks,
			PacketCodecHelper.enumOf(Variant::values), c -> c.variant,
			TypedTransmission::new
	);
	
	private final Variant variant;
	
	public TypedTransmission(Vec3 origin, PositionSource destination, int arrivalInTicks, Variant variant) {
		super(origin, destination, arrivalInTicks);
		this.variant = variant;
	}
	
	public Variant getVariant() {
		return this.variant;
	}
	
}

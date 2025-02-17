package de.dafuqs.spectrum.particle.effect;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.particle.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.registry.*;
import net.minecraft.util.dynamic.*;
import org.joml.*;

public record DynamicParticleEffect(ParticleType<?> particleType, float gravity, Vector3f color, float scale,
									int lifetimeTicks, boolean collisions, boolean glowing, boolean alwaysShow) implements ParticleEffect {
	
	public static final MapCodec<DynamicParticleEffect> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			Registries.PARTICLE_TYPE.getCodec().fieldOf("particle_type").forGetter(c -> c.particleType),
			Codec.FLOAT.fieldOf("gravity").forGetter(c -> c.gravity),
			Codecs.VECTOR_3F.fieldOf("color").forGetter(c -> c.color),
			Codec.FLOAT.fieldOf("scale").forGetter(c -> c.scale),
			Codec.INT.fieldOf("lifetime_ticks").forGetter(c -> c.lifetimeTicks),
			Codec.BOOL.fieldOf("collisions").forGetter(c -> c.collisions),
			Codec.BOOL.fieldOf("glow_in_the_dark").forGetter(c -> c.glowing),
			Codec.BOOL.optionalFieldOf("always_show", false).forGetter(c -> c.alwaysShow)
	).apply(i, DynamicParticleEffect::new));
	
	public static final PacketCodec<RegistryByteBuf, DynamicParticleEffect> PACKET_CODEC = PacketCodecHelper.tuple(
			PacketCodecs.registryValue(RegistryKeys.PARTICLE_TYPE), c -> c.particleType,
			PacketCodecs.FLOAT, c -> c.gravity,
			PacketCodecs.VECTOR3F, c -> c.color,
			PacketCodecs.FLOAT, c -> c.scale,
			PacketCodecs.VAR_INT, c -> c.lifetimeTicks,
			PacketCodecs.BOOL, c -> c.collisions,
			PacketCodecs.BOOL, c -> c.glowing,
			PacketCodecs.BOOL, c -> c.alwaysShow,
			DynamicParticleEffect::new
	);
	
	public DynamicParticleEffect(float gravity, Vector3f color, float scale, int lifetimeTicks, boolean collisions, boolean glowing) {
		this(SpectrumParticleTypes.SHOOTING_STAR, gravity, color, scale, lifetimeTicks, collisions, glowing);
	}
	
	public DynamicParticleEffect(float gravity, Vector3f color, float scale, int lifetimeTicks, boolean collisions, boolean glowing, boolean alwaysShow) {
		this(SpectrumParticleTypes.SHOOTING_STAR, gravity, color, scale, lifetimeTicks, collisions, glowing, alwaysShow);
	}
	
	public DynamicParticleEffect(ParticleType<?> particleType, float gravity, Vector3f color, float scale, int lifetimeTicks, boolean collisions, boolean glowing) {
		this(particleType, gravity, color, scale, lifetimeTicks, collisions, glowing, false);
	}
	
	@Override
	public ParticleType<?> getType() {
		return alwaysShow ? SpectrumParticleTypes.DYNAMIC_ALWAYS_SHOW : SpectrumParticleTypes.DYNAMIC;
	}
	
	@Override
	public String toString() {
		return String.valueOf(Registries.PARTICLE_TYPE.getId(this.getType()));
	}
	
}

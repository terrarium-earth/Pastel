package de.dafuqs.spectrum.recipe;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;

public abstract class RecipeScaling {
	
	public static final Codec<ScalingData> CODEC = RecordCodecBuilder.create(i -> i.group(
			SpectrumRegistries.RECIPE_SCALING.getCodec().fieldOf("type").forGetter(d -> d.type),
			Codec.INT.optionalFieldOf("start", 0).forGetter(d -> d.start),
			Codec.INT.fieldOf("scaling_value").forGetter(d -> d.scalingValue),
			Codec.DOUBLE.optionalFieldOf("scaling_factor", 1.0).forGetter(d -> d.scalingFactor)
	).apply(i, ScalingData::new));
	
	public static final PacketCodec<RegistryByteBuf, ScalingData> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.registryValue(SpectrumRegistryKeys.RECIPE_SCALING), d -> d.type,
			PacketCodecs.VAR_INT, d -> d.start,
			PacketCodecs.VAR_INT, d -> d.scalingValue,
			PacketCodecs.DOUBLE, d -> d.scalingFactor,
			ScalingData::new
	);
	
	public static final RecipeScaling LINEAR = new RecipeScaling(SpectrumCommon.locate("linear")) {
		@Override
		int getInputCount(double scaling, ScalingData data) {
			return data.start + (int) Math.round(data.scalingValue * scaling * data.scalingFactor);
		}
	};
	
	public static final RecipeScaling DOUBLING = new RecipeScaling(SpectrumCommon.locate("doubling")) {
		@Override
		int getInputCount(double scaling, ScalingData data) {
			return data.start + data.scalingValue << Math.round((scaling * data.scalingFactor));
		}
	};
	
	public static final RecipeScaling SQUARE = new RecipeScaling(SpectrumCommon.locate("square")) {
		@Override
		int getInputCount(double scaling, ScalingData data) {
			return (int) (data.start + Math.round(Math.pow(data.scalingValue, scaling * data.scalingFactor)));
		}
	};
	
	private final Identifier id;
	
	public RecipeScaling(Identifier id) {
		this.id = id;
	}
	
	abstract int getInputCount(double scaling, ScalingData data);
	
	public Identifier getId() {
		return id;
	}
	
	public record ScalingData(RecipeScaling type, int start, int scalingValue, double scalingFactor) {
		public int apply(double scaling) {
			return type.getInputCount(scaling, this);
		}
	}
}

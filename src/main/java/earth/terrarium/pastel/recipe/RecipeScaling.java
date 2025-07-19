package earth.terrarium.pastel.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelRegistries;
import earth.terrarium.pastel.registries.PastelRegistryKeys;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;

public abstract class RecipeScaling {
	
	public static final Codec<ScalingData> CODEC = RecordCodecBuilder.<ScalingData>create(i -> i.group(
			PastelRegistries.RECIPE_SCALING.byNameCodec().fieldOf("type").forGetter(d -> d.type),
			Codec.INT.optionalFieldOf("start", 0).forGetter(d -> d.start),
			Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("scaling_value", 0).forGetter(d -> d.scalingValue),
			Codec.doubleRange(0.0, Double.MAX_VALUE).optionalFieldOf("scaling_factor", 1.0).forGetter(d -> d.scalingFactor),
			Codec.INT.listOf(0, 255).optionalFieldOf("indexes", Collections.emptyList()).forGetter(d -> d.indexes)
	).apply(i, ScalingData::new));
	
	public static final StreamCodec<RegistryFriendlyByteBuf, ScalingData> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.registry(PastelRegistryKeys.RECIPE_SCALING), d -> d.type,
			ByteBufCodecs.VAR_INT, d -> d.start,
			ByteBufCodecs.VAR_INT, d -> d.scalingValue,
			ByteBufCodecs.DOUBLE, d -> d.scalingFactor,
			ByteBufCodecs.VAR_INT.apply(ByteBufCodecs.list()), d -> d.indexes,
			ScalingData::new
	);
	
	public static final RecipeScaling LINEAR = new RecipeScaling(PastelCommon.locate("linear")) {
		@Override
		int getInputCount(double scaling, ScalingData data) {
			return data.start + (int) Math.round(data.scalingValue * scaling * data.scalingFactor);
		}
	};
	
	public static final RecipeScaling DOUBLING = new RecipeScaling(PastelCommon.locate("doubling")) {
		@Override
		int getInputCount(double scaling, ScalingData data) {
			return data.start + data.scalingValue << Math.round(((scaling - 1) * data.scalingFactor));
		}
	};
	
	public static final RecipeScaling EXPONENTIAL = new RecipeScaling(PastelCommon.locate("exponential")) {
		@Override
		int getInputCount(double scaling, ScalingData data) {
			return (int) (data.start + Math.round(Math.pow(data.scalingValue, scaling * data.scalingFactor)));
		}
	};
	
	public static final RecipeScaling INDEXED = new RecipeScaling(PastelCommon.locate("indexed")) {
		@Override
		int getInputCount(double scaling, ScalingData data) {
			var size = data.indexes.size();
			return data.indexes.get(Math.clamp((int) Math.round(scaling - 1), 0, size - 1));
		}
	};
	
	private final ResourceLocation id;
	
	public RecipeScaling(ResourceLocation id) {
		this.id = id;
	}
	
	abstract int getInputCount(double scaling, ScalingData data);
	
	public ResourceLocation getId() {
		return id;
	}
	
	public record ScalingData(RecipeScaling type, int start, int scalingValue, double scalingFactor, List<Integer> indexes) {
		public int apply(double scaling) {
			return type.getInputCount(scaling, this);
		}
	}
	
	public static ScalingData linear(int start, int scalingValue, double scalingFactor, Integer... indices) {
		return new ScalingData(LINEAR, start, scalingValue, scalingFactor, List.of(indices));
	}
	
	public static ScalingData doubling(int scalingValue, Integer... indices) {
		return new ScalingData(DOUBLING, 0, scalingValue, 1.0F, List.of(indices));
	}
	
	public static ScalingData doubling(int start, int scalingValue, double scalingFactor, Integer... indices) {
		return new ScalingData(DOUBLING, start, scalingValue, scalingFactor, List.of(indices));
	}
	
	public static ScalingData exponential(int start, int scalingValue, double scalingFactor, Integer... indices) {
		return new ScalingData(EXPONENTIAL, start, scalingValue, scalingFactor, List.of(indices));
	}
	
	public static ScalingData indices(Integer... indices) {
		return indexed(0, 0, 1.0f, indices);
	}
	
	public static ScalingData indexed(int start, int scalingValue, double scalingFactor, Integer... indices) {
		return new ScalingData(INDEXED, start, scalingValue, scalingFactor, List.of(indices));
	}
}

package de.dafuqs.spectrum.helpers;

import com.google.gson.*;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.mixin.accessors.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class CodecHelper {
	
	public static Codec<Identifier> SPECTRUM_IDENTIFIER = Codec.STRING.xmap(SpectrumCommon::ofSpectrum, Identifier::toString);
	
	public static MapCodec<RegistryWrapper.WrapperLookup> LOOKUP = new MapCodec<>() {
		@Override
		public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
			return Stream.empty();
		}
		
		@Override
		public <T> DataResult<RegistryWrapper.WrapperLookup> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
			if (dynamicOps instanceof RegistryOps<T> registryOps) {
				var infoGetter = ((RegistryOpsAccessor) registryOps).getRegistryInfoGetter();
				var lookup = ((CachedRegistryInfoGetterAccessor) infoGetter).getRegistriesLookup();
				return DataResult.success(lookup);
			}
			return DataResult.error(() -> "The LOOKUP codec requires RegistryOps.");
		}
		
		@Override
		public <T> RecordBuilder<T> encode(RegistryWrapper.WrapperLookup wrapperLookup, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
			return recordBuilder;
		}
	};
	
	public static <L, R> MapCodec<Pair<L, R>> mapPair(MapCodec<L> leftCodec, MapCodec<R> rightCodec) {
		return RecordCodecBuilder.mapCodec(i -> i.group(
				leftCodec.forGetter(Pair::getLeft),
				rightCodec.forGetter(Pair::getRight)
		).apply(i, Pair::new));
	}
	
	public static <T> Codec<List<T>> singleOrList(Codec<T> codec) {
		return Codec.withAlternative(codec.listOf(), codec, List::of);
	}
	
	public interface ThrowableFunction<I, O, E extends Throwable> {
		O apply(I input) throws E;
	}
	
	public static <I, O, E extends Throwable> Function<I, DataResult<O>> throwable(ThrowableFunction<I, O, E> throwable) {
		return input -> {
			try {
				return DataResult.success(throwable.apply(input));
			} catch (Throwable e) {
				return DataResult.error(e::getMessage);
			}
		};
	}
	
	public static <T, D> Optional<T> from(DynamicOps<D> ops, Codec<T> codec, D elem) {
		if (elem == null) return Optional.empty();
		return codec.decode(ops, elem).result().map(com.mojang.datafixers.util.Pair::getFirst);
	}
	
	public static <T> Optional<T> fromNbt(Codec<T> codec, NbtElement nbt) {
		return from(NbtOps.INSTANCE, codec, nbt);
	}
	
	public static <T> T fromNbt(Codec<T> codec, NbtElement nbt, T defaultValue) {
		return fromNbt(codec, nbt).orElse(defaultValue);
	}
	
	public static <T> Optional<T> fromJson(Codec<T> codec, JsonElement json) {
		return from(JsonOps.INSTANCE, codec, json);
	}
	
	public static <T> T fromJson(Codec<T> codec, JsonElement json, T defaultValue) {
		return fromJson(codec, json).orElse(defaultValue);
	}
	
	public static <T> void toNbt(Codec<T> codec, T value, Consumer<? super NbtElement> ifValid) {
		codec.encodeStart(NbtOps.INSTANCE, value).result().ifPresent(ifValid);
	}
	
	public static <T> void writeNbt(NbtCompound nbt, String key, Codec<T> codec, T value) {
		toNbt(codec, value, elem -> nbt.put(key, elem));
	}
	
}

package de.dafuqs.spectrum.helpers;

import com.mojang.datafixers.util.*;
import de.dafuqs.spectrum.recipe.*;
import io.netty.buffer.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.encoding.*;
import net.minecraft.predicate.*;
import net.minecraft.registry.*;
import net.minecraft.util.Pair;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.apache.commons.lang3.math.*;

import java.lang.reflect.*;
import java.util.function.*;

public class PacketCodecHelper {
	
	public static PacketCodec<ByteBuf, Fraction> FRACTION = PacketCodecHelper.pair(PacketCodecs.VAR_INT, PacketCodecs.VAR_INT).xmap(pair -> Fraction.getFraction(pair.getLeft(), pair.getRight()), frac -> new Pair<>(frac.getNumerator(), frac.getDenominator()));
	public static final PacketCodec<ByteBuf, Vec3i> VEC3I = PacketCodec.tuple(PacketCodecs.VAR_INT, Vec3i::getX, PacketCodecs.VAR_INT, Vec3i::getY, PacketCodecs.VAR_INT, Vec3i::getZ, Vec3i::new);
	public static final PacketCodec<ByteBuf, Vec3d> VEC3D = PacketCodec.tuple(PacketCodecs.DOUBLE, Vec3d::getX, PacketCodecs.DOUBLE, Vec3d::getY, PacketCodecs.DOUBLE, Vec3d::getZ, Vec3d::new);
	public static final PacketCodec<ByteBuf, NumberRange.IntRange> INT_RANGE = PacketCodec.tuple(
			PacketCodecs.VAR_INT, range -> range.min().orElse(Integer.MIN_VALUE),
			PacketCodecs.VAR_INT, range -> range.max().orElse(Integer.MAX_VALUE),
			NumberRange.IntRange::between
	);
	
	public static final PacketCodec<ByteBuf, LightPredicate> LIGHT_PREDICATE = INT_RANGE.xmap(LightPredicate::new, LightPredicate::range);
	public static final PacketCodec<RegistryByteBuf, FluidPredicate> FLUID_PREDICATE = PacketCodec.tuple(
			PacketCodecs.optional(PacketCodecs.registryEntryList(RegistryKeys.FLUID)), FluidPredicate::fluids,
			PacketCodecs.optional(StatePredicate.PACKET_CODEC), FluidPredicate::state,
			FluidPredicate::new
	);
	
	public static final PacketCodec<ByteBuf, BlockState> BLOCK_STATE = PacketCodecs.STRING.xmap(string -> RecipeUtils.blockStateDataFromString(string).result().orElse(Blocks.AIR.getDefaultState()), RecipeUtils::blockStateToString);
	
	public static final PacketCodec<RegistryByteBuf, RegistryWrapper.WrapperLookup> LOOKUP = PacketCodec.ofStatic((buf, value) -> {
	}, RegistryByteBuf::getRegistryManager);
	
	public static <O extends ByteBuf, L, R> PacketCodec<O, Pair<L, R>> pair(PacketCodec<? super O, L> left, PacketCodec<? super O, R> right) {
		return PacketCodec.tuple(left, Pair::getLeft, right, Pair::getRight, Pair::new);
	}
	
	/**
	 * Use this if you can't use PacketCodecs.registryValue, such as when it isn't a RegistryByteBuf,
	 * since it writes a whole string instead of an int.
	 */
	public static <T> PacketCodec<ByteBuf, T> registryValueByName(Registry<T> registry) {
		return Identifier.PACKET_CODEC.xmap(registry::get, registry::getId);
	}
	
	public static <E extends Enum<E>> PacketCodec<ByteBuf, E> enumOf(Supplier<E[]> valuesSupplier) {
		var values = valuesSupplier.get();
		return new PacketCodec<>() {
			public E decode(ByteBuf byteBuf) {
				return values[VarInts.read(byteBuf)];
			}
			
			public void encode(ByteBuf byteBuf, E value) {
				VarInts.write(byteBuf, value.ordinal());
			}
		};
	}
	
	public static <B extends ByteBuf, V> PacketCodec<B, V[]> array(Class<V> clazz, PacketCodec<B, V> codec) {
		return new PacketCodec<>() {
			@Override
			@SuppressWarnings("unchecked")
			public V[] decode(B buf) {
				var length = VarInts.read(buf);
				var array = (V[]) Array.newInstance(clazz, length);
				for (int i = 0; i < length; i++)
					array[i] = codec.decode(buf);
				return array;
			}
			
			@Override
			public void encode(B buf, V[] value) {
				VarInts.write(buf, value.length);
				for (var v : value)
					codec.encode(buf, v);
			}
		};
	}
	
	public static <B extends ByteBuf, C> PacketCodec<B, C> tuple(
			Supplier<C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				return to.get();
			}
			
			@Override
			public void encode(B buf, C value) {
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			Function<T1, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				return to.apply(a1);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			BiFunction<T1, T2, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				return to.apply(a1, a2);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			Function3<T1, T2, T3, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				return to.apply(a1, a2, a3);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			Function4<T1, T2, T3, T4, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				return to.apply(a1, a2, a3, a4);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			Function5<T1, T2, T3, T4, T5, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				return to.apply(a1, a2, a3, a4, a5);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			Function6<T1, T2, T3, T4, T5, T6, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			Function7<T1, T2, T3, T4, T5, T6, T7, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				var a10 = codec10.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			PacketCodec<? super B, T11> codec11, Function<C, T11> from11,
			Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				var a10 = codec10.decode(buf);
				var a11 = codec11.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
				codec11.encode(buf, from11.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			PacketCodec<? super B, T11> codec11, Function<C, T11> from11,
			PacketCodec<? super B, T12> codec12, Function<C, T12> from12,
			Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				var a10 = codec10.decode(buf);
				var a11 = codec11.decode(buf);
				var a12 = codec12.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
				codec11.encode(buf, from11.apply(value));
				codec12.encode(buf, from12.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			PacketCodec<? super B, T11> codec11, Function<C, T11> from11,
			PacketCodec<? super B, T12> codec12, Function<C, T12> from12,
			PacketCodec<? super B, T13> codec13, Function<C, T13> from13,
			Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				var a10 = codec10.decode(buf);
				var a11 = codec11.decode(buf);
				var a12 = codec12.decode(buf);
				var a13 = codec13.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
				codec11.encode(buf, from11.apply(value));
				codec12.encode(buf, from12.apply(value));
				codec13.encode(buf, from13.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			PacketCodec<? super B, T11> codec11, Function<C, T11> from11,
			PacketCodec<? super B, T12> codec12, Function<C, T12> from12,
			PacketCodec<? super B, T13> codec13, Function<C, T13> from13,
			PacketCodec<? super B, T14> codec14, Function<C, T14> from14,
			Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				var a10 = codec10.decode(buf);
				var a11 = codec11.decode(buf);
				var a12 = codec12.decode(buf);
				var a13 = codec13.decode(buf);
				var a14 = codec14.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
				codec11.encode(buf, from11.apply(value));
				codec12.encode(buf, from12.apply(value));
				codec13.encode(buf, from13.apply(value));
				codec14.encode(buf, from14.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			PacketCodec<? super B, T11> codec11, Function<C, T11> from11,
			PacketCodec<? super B, T12> codec12, Function<C, T12> from12,
			PacketCodec<? super B, T13> codec13, Function<C, T13> from13,
			PacketCodec<? super B, T14> codec14, Function<C, T14> from14,
			PacketCodec<? super B, T15> codec15, Function<C, T15> from15,
			Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				var a10 = codec10.decode(buf);
				var a11 = codec11.decode(buf);
				var a12 = codec12.decode(buf);
				var a13 = codec13.decode(buf);
				var a14 = codec14.decode(buf);
				var a15 = codec15.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
				codec11.encode(buf, from11.apply(value));
				codec12.encode(buf, from12.apply(value));
				codec13.encode(buf, from13.apply(value));
				codec14.encode(buf, from14.apply(value));
				codec15.encode(buf, from15.apply(value));
			}
		};
	}
	
	public static <B extends ByteBuf, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> PacketCodec<B, C> tuple(
			PacketCodec<? super B, T1> codec1, Function<C, T1> from1,
			PacketCodec<? super B, T2> codec2, Function<C, T2> from2,
			PacketCodec<? super B, T3> codec3, Function<C, T3> from3,
			PacketCodec<? super B, T4> codec4, Function<C, T4> from4,
			PacketCodec<? super B, T5> codec5, Function<C, T5> from5,
			PacketCodec<? super B, T6> codec6, Function<C, T6> from6,
			PacketCodec<? super B, T7> codec7, Function<C, T7> from7,
			PacketCodec<? super B, T8> codec8, Function<C, T8> from8,
			PacketCodec<? super B, T9> codec9, Function<C, T9> from9,
			PacketCodec<? super B, T10> codec10, Function<C, T10> from10,
			PacketCodec<? super B, T11> codec11, Function<C, T11> from11,
			PacketCodec<? super B, T12> codec12, Function<C, T12> from12,
			PacketCodec<? super B, T13> codec13, Function<C, T13> from13,
			PacketCodec<? super B, T14> codec14, Function<C, T14> from14,
			PacketCodec<? super B, T15> codec15, Function<C, T15> from15,
			PacketCodec<? super B, T16> codec16, Function<C, T16> from16,
			Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, C> to
	) {
		return new PacketCodec<>() {
			@Override
			public C decode(B buf) {
				var a1 = codec1.decode(buf);
				var a2 = codec2.decode(buf);
				var a3 = codec3.decode(buf);
				var a4 = codec4.decode(buf);
				var a5 = codec5.decode(buf);
				var a6 = codec6.decode(buf);
				var a7 = codec7.decode(buf);
				var a8 = codec8.decode(buf);
				var a9 = codec9.decode(buf);
				var a10 = codec10.decode(buf);
				var a11 = codec11.decode(buf);
				var a12 = codec12.decode(buf);
				var a13 = codec13.decode(buf);
				var a14 = codec14.decode(buf);
				var a15 = codec15.decode(buf);
				var a16 = codec16.decode(buf);
				return to.apply(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16);
			}
			
			@Override
			public void encode(B buf, C value) {
				codec1.encode(buf, from1.apply(value));
				codec2.encode(buf, from2.apply(value));
				codec3.encode(buf, from3.apply(value));
				codec4.encode(buf, from4.apply(value));
				codec5.encode(buf, from5.apply(value));
				codec6.encode(buf, from6.apply(value));
				codec7.encode(buf, from7.apply(value));
				codec8.encode(buf, from8.apply(value));
				codec9.encode(buf, from9.apply(value));
				codec10.encode(buf, from10.apply(value));
				codec11.encode(buf, from11.apply(value));
				codec12.encode(buf, from12.apply(value));
				codec13.encode(buf, from13.apply(value));
				codec14.encode(buf, from14.apply(value));
				codec15.encode(buf, from15.apply(value));
				codec16.encode(buf, from16.apply(value));
			}
		};
	}
	
}

package de.dafuqs.spectrum.api.predicate.location;

import com.mojang.serialization.Codec;
import de.dafuqs.spectrum.helpers.TimeHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;

public record TimeOfDayPredicate(TimeHelper.TimeOfDay name, MinMaxBounds.Ints range) {
	
	private static final Codec<TimeOfDayPredicate> NAMED_CODEC = StringRepresentable.fromEnum(TimeHelper.TimeOfDay::values)
			.xmap(t -> new TimeOfDayPredicate(t, MinMaxBounds.Ints.between(t.from, t.to-1)), TimeOfDayPredicate::name);
	
	private static final Codec<TimeOfDayPredicate> RANGED_CODEC = MinMaxBounds.Ints.CODEC
			.xmap(t -> new TimeOfDayPredicate(null, t), TimeOfDayPredicate::range);
	
	private static final Codec<TimeOfDayPredicate> VALUED_CODEC = Codec.INT
			.xmap(t -> new TimeOfDayPredicate(null, MinMaxBounds.Ints.exactly(t)), p -> p.range.min().orElse(0));
	
	public static final Codec<TimeOfDayPredicate> CODEC = Codec.withAlternative(NAMED_CODEC, Codec.withAlternative(RANGED_CODEC, VALUED_CODEC));
	
	public static final StreamCodec<ByteBuf, TimeOfDayPredicate> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, range -> range.min().orElse(Integer.MIN_VALUE),
			ByteBufCodecs.VAR_INT, range -> range.max().orElse(Integer.MAX_VALUE),
			MinMaxBounds.Ints::between
	).map(
			range -> new TimeOfDayPredicate(null, range),
			TimeOfDayPredicate::range
	);

	public boolean test(ServerLevel world) {
		return range.matches((int) (world.getDayTime() % 24000));
	}
	
}
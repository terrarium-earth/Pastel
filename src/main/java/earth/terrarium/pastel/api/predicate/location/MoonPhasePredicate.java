package earth.terrarium.pastel.api.predicate.location;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;

public enum MoonPhasePredicate implements StringRepresentable {
	FULL_MOON,
	WANING_GIBBOUS,
	THIRD_QUARTER,
	WANING_CRESCENT,
	NEW_MOON,
	WAXING_CRESCENT,
	FIRST_QUARTER,
	WAXING_GIBBOUS;
	
	public static final Codec<MoonPhasePredicate> CODEC = StringRepresentable.fromEnum(MoonPhasePredicate::values);
	public static final StreamCodec<ByteBuf, MoonPhasePredicate> STREAM_CODEC = PacketCodecHelper.enumOf(MoonPhasePredicate::values);
	
	public boolean test(ServerLevel world) {
		return ordinal() == world.getMoonPhase();
	}
	
	@Override
	public String getSerializedName() {
		return name().toLowerCase();
	}
	
}
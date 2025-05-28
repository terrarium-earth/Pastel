package earth.terrarium.pastel.api.predicate.location;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.helpers.PacketCodecHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public enum WeatherPredicate implements StringRepresentable {
	CLEAR_SKY(world -> !world.isRaining()),
	RAIN(Level::isRaining),
	STRICT_RAIN(world -> world.isRaining() && !world.isThundering()),
	THUNDER(Level::isThundering),
	NOT_THUNDER(world -> !world.isThundering());
	
	public static final Codec<WeatherPredicate> CODEC = StringRepresentable.fromEnum(WeatherPredicate::values);
	public static final StreamCodec<ByteBuf, WeatherPredicate> STREAM_CODEC = PacketCodecHelper.enumOf(WeatherPredicate::values);
	
	private final Function<ServerLevel, Boolean> test;
	
	WeatherPredicate(Function<ServerLevel, Boolean> test) {
		this.test = test;
	}
	
	public boolean test(ServerLevel world) {
		return test.apply(world);
	}
	
	@Override
	public String getSerializedName() {
		return name().toLowerCase();
	}
	
}
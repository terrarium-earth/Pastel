package earth.terrarium.pastel.events.game;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class ExactPositionSource implements PositionSource {
	
	public static final MapCodec<ExactPositionSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			Vec3.CODEC.fieldOf("pos").forGetter((blockPositionSource) -> blockPositionSource.pos)
	).apply(instance, ExactPositionSource::new));
	
	public static final StreamCodec<ByteBuf, ExactPositionSource> STREAM_CODEC = StreamCodec.composite(PacketCodecHelper.VEC3D, (source) -> source.pos, ExactPositionSource::new);
	
	final Vec3 pos;
	
	public ExactPositionSource(Vec3 pos) {
		this.pos = pos;
	}
	
	@Override
	public Optional<Vec3> getPosition(Level world) {
		return Optional.of(this.pos);
	}
	
	@Override
	public PositionSourceType<?> getType() {
		return PastelPositionSources.EXACT.value();
	}
	
	public static class Type implements PositionSourceType<ExactPositionSource> {
		public Type() {
		}
		
		public MapCodec<ExactPositionSource> codec() {
			return ExactPositionSource.CODEC;
		}
		
		public StreamCodec<ByteBuf, ExactPositionSource> streamCodec() {
			return ExactPositionSource.STREAM_CODEC;
		}
	}
	
}

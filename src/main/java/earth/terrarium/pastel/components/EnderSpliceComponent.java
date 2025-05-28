package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.PacketCodecHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public record EnderSpliceComponent(Optional<Vec3> pos, Optional<ResourceKey<Level>> dimension, Optional<String> targetName, Optional<UUID> targetUUID) {
	
	public static final EnderSpliceComponent DEFAULT = new EnderSpliceComponent(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	
	public static final Codec<EnderSpliceComponent> CODEC = RecordCodecBuilder.create(i -> i.group(
			Vec3.CODEC.optionalFieldOf("pos").forGetter(c -> c.pos),
			ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("dimension").forGetter(c -> c.dimension),
			Codec.STRING.optionalFieldOf("target_name").forGetter(c -> c.targetName),
			UUIDUtil.STRING_CODEC.optionalFieldOf("target_uuid").forGetter(c -> c.targetUUID)
	).apply(i, EnderSpliceComponent::new));
	
	public static final StreamCodec<ByteBuf, EnderSpliceComponent> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.optional(PacketCodecHelper.VEC3D), EnderSpliceComponent::pos,
			ByteBufCodecs.optional(ResourceKey.streamCodec(Registries.DIMENSION)), EnderSpliceComponent::dimension,
			ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), EnderSpliceComponent::targetName,
			ByteBufCodecs.optional(UUIDUtil.STREAM_CODEC), EnderSpliceComponent::targetUUID,
			EnderSpliceComponent::new
	);
	
	public EnderSpliceComponent(Vec3 pos, ResourceKey<Level> dimension) {
		this(Optional.of(pos), Optional.of(dimension), Optional.empty(), Optional.empty());
	}
	
	public EnderSpliceComponent(String targetName, UUID targetUUID) {
		this(Optional.empty(), Optional.empty(), Optional.of(targetName), Optional.of(targetUUID));
	}
	
}

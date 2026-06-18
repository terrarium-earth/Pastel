package earth.terrarium.pastel.components;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record EnderSpliceComponent(
    Optional<Vec3> pos,
    Optional<ResourceKey<Level>> dimension,
    Optional<Component> targetName,
    Optional<GameProfile> targetGameProfile
) {

    public static final EnderSpliceComponent DEFAULT = new EnderSpliceComponent(
        Optional.empty(),
        Optional.empty(),
        Optional.empty(),
        Optional.empty()
    );

    public static final Codec<EnderSpliceComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    Vec3.CODEC
                        .optionalFieldOf("pos")
                        .forGetter(c -> c.pos),
                    ResourceKey
                        .codec(Registries.DIMENSION)
                        .optionalFieldOf("dimension")
                        .forGetter(c -> c.dimension),
                    ComponentSerialization.CODEC
                        .optionalFieldOf("target_name")
                        .forGetter(c -> c.targetName),
                    ExtraCodecs.GAME_PROFILE
                        .optionalFieldOf("target_uuid")
                        .forGetter(c -> c.targetGameProfile)
                )
                .apply(
                    i,
                    EnderSpliceComponent::new
                )
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, EnderSpliceComponent> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.optional(PacketCodecHelper.VEC3D),
            EnderSpliceComponent::pos,
            ByteBufCodecs.optional(ResourceKey.streamCodec(Registries.DIMENSION)),
            EnderSpliceComponent::dimension,
            ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC),
            EnderSpliceComponent::targetName,
            ByteBufCodecs.optional(ByteBufCodecs.GAME_PROFILE),
            EnderSpliceComponent::targetGameProfile,
            EnderSpliceComponent::new
        );

    public EnderSpliceComponent(Vec3 pos, ResourceKey<Level> dimension) {
        this(Optional.of(pos), Optional.of(dimension), Optional.empty(), Optional.empty());
    }

    public EnderSpliceComponent(Component targetName, GameProfile targetGameProfile) {
        this(Optional.empty(), Optional.empty(), Optional.of(targetName), Optional.of(targetGameProfile));
    }

}

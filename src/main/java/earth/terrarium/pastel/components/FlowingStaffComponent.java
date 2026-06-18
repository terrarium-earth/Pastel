package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;

public record FlowingStaffComponent(
    Optional<BlockPos> pos1,
    Optional<BlockPos> pos2,
    boolean cornerSwitch,
    Optional<Component> blockName
) {
    public static final FlowingStaffComponent DEFAULT = new FlowingStaffComponent(
        Optional.empty(),
        Optional.empty(),
        false,
        Optional.empty()
    );

    public static final Codec<FlowingStaffComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    BlockPos.CODEC
                        .optionalFieldOf("pos1")
                        .forGetter(FlowingStaffComponent::pos1),
                    BlockPos.CODEC
                        .optionalFieldOf("pos2")
                        .forGetter(FlowingStaffComponent::pos2),
                    Codec.BOOL
                        .fieldOf(
                            "cornerSwitch"
                        )
                        .forGetter(FlowingStaffComponent::cornerSwitch),
                    ComponentSerialization.CODEC
                        .optionalFieldOf("blockName")
                        .forGetter(FlowingStaffComponent::blockName)
                )
                .apply(
                    i,
                    FlowingStaffComponent::new
                )
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, FlowingStaffComponent> STREAM_CODEC = StreamCodec
        .composite(
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs::optional),
            FlowingStaffComponent::pos1,
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs::optional),
            FlowingStaffComponent::pos2,
            ByteBufCodecs.BOOL,
            FlowingStaffComponent::cornerSwitch,
            ComponentSerialization.OPTIONAL_STREAM_CODEC,
            FlowingStaffComponent::blockName,
            FlowingStaffComponent::new
        );
}

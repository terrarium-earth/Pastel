package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public record StoredBlockEntityComponent(BlockState state, CompoundTag blockEntityData) {

    public static final Codec<StoredBlockEntityComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    BlockState.CODEC
                        .fieldOf(
                            "state"
                        )
                        .forGetter(StoredBlockEntityComponent::state),
                    CompoundTag.CODEC
                        .fieldOf("blockEntityData")
                        .forGetter(
                            StoredBlockEntityComponent::blockEntityData
                        )
                )
                .apply(
                    i,
                    StoredBlockEntityComponent::new
                )
        );
}

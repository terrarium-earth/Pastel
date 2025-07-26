package earth.terrarium.pastel.logistics.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.blocks.pastel_nodes.PastelNodeBlockEntity;
import earth.terrarium.pastel.helpers.data.SchedulerMap;
import earth.terrarium.pastel.helpers.interaction.InWorldInteractionHelper;
import earth.terrarium.pastel.logistics.api.Payload;
import earth.terrarium.pastel.registries.PastelRegistries;
import earth.terrarium.pastel.registries.PastelRegistryKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Transmission<T, C> implements SchedulerMap.Callback {

    @SuppressWarnings("unchecked")
    public static final Codec<Transmission<?, ?>> CODEC = RecordCodecBuilder.create(i -> i.group(
        BlockPos.CODEC.listOf()
                      .fieldOf(
                          "node_positions")
                      .forGetter(Transmission::getNodePositions),
        PastelRegistries.LOGISTIC_PAYLOAD.byNameCodec().dispatch(Payload.Wrapper::getType, Payload::wrapperCodec)
                                         .fieldOf("reference").forGetter(Transmission::getWrapper),
        Codec.INT.fieldOf(
            "amount")
                  .forGetter(Transmission::getCount),
        Codec.INT.fieldOf(
            "vertex_time")
                 .forGetter(Transmission::getVertexTime)).apply(
                     i,
                     Transmission::new
    ));

    @SuppressWarnings("unchecked")
    public static final StreamCodec<RegistryFriendlyByteBuf, Transmission<?, ?>> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), Transmission::getNodePositions,
        ByteBufCodecs.registry(PastelRegistryKeys.LOGISTIC_PAYLOAD).dispatch(Payload.Wrapper::getType, Payload::wrapperStream), Transmission::getWrapper,
        ByteBufCodecs.VAR_INT, Transmission::getCount,
        ByteBufCodecs.VAR_INT, Transmission::getVertexTime,
        Transmission::new
    );

    private @Nullable ServerPastelNetwork network;
    private final List<BlockPos> nodePositions;
    private final T reference;
    private final Payload<T, C> payload;
    private final int count;
    private final int vertexTime;

    public Transmission(List<BlockPos> nodePositions, Payload.Wrapper<T, C> reference, int count, int vertexTime) {
        this.nodePositions = nodePositions;
        this.reference = reference.getWrapped();
        this.count = count;
        this.vertexTime = vertexTime;
        this.payload = reference.getType();
    }

    public void setNetwork(@NotNull ServerPastelNetwork network) {
        this.network = network;
    }

    public @Nullable PastelNetwork<ServerLevel> getNetwork() {
        return this.network;
    }

    public List<BlockPos> getNodePositions() {
        return nodePositions;
    }

    public int getVertexTime() {
        return vertexTime;
    }

    public int getTransmissionDuration() {
        return vertexTime * (nodePositions.size() - 1);
    }

    public Payload.Wrapper getWrapper() {
        return payload.wrap(reference);
    }

    public int getCount() {
        return this.count;
    }

    public BlockPos getStartPos() {
        return this.nodePositions.getFirst();
    }

    @Override
    public void trigger() {
        arriveAtDestination();
    }

    private void arriveAtDestination() {
        if (nodePositions.isEmpty()) {
            return;
        }

        @NotNull BlockPos destinationPos = nodePositions.get(nodePositions.size() - 1);
        @Nullable PastelNodeBlockEntity destinationNode = this.network.getLoadedNodeAt(destinationPos);
        Level world = this.network.getLevel();

        var inserted = 0;
        if (destinationNode != null) {
            var destinationHandler = destinationNode.getConnectedHandler();
            if (destinationHandler != null) {
                inserted = count;
                inserted -= payload.getCount(payload.insert(null, reference, count, false));
                destinationNode.addItemCountUnderway(-count);
            }
        }
        if (inserted != count) {
            InWorldInteractionHelper.scatter(
                world, destinationPos.getX() + 0.5, destinationPos.getY() + 0.5, destinationPos.getZ() + 0.5, reference,
                count - inserted
            );
        }
    }

}

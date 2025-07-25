package earth.terrarium.pastel.logistics.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.blocks.pastel_nodes.PastelNodeBlockEntity;
import earth.terrarium.pastel.helpers.data.SchedulerMap;
import earth.terrarium.pastel.helpers.interaction.InWorldInteractionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Transmission implements SchedulerMap.Callback {

    public static final Codec<Transmission> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                              BlockPos.CODEC.listOf()
                                                                                                            .fieldOf(
                                                                                                                "node_positions")
                                                                                                            .forGetter(Transmission::getNodePositions),
                                                                                              ItemStack.CODEC.fieldOf("variant")
                                                                                                             .forGetter(Transmission::getStack),
                                                                                              Codec.LONG.fieldOf(
                                                                                                  "amount")
                                                                                                        .forGetter(Transmission::getAmount),
                                                                                              Codec.INT.fieldOf(
                                                                                                  "vertex_time")
                                                                                                       .forGetter(Transmission::getVertexTime)
                                                                                          )
                                                                                          .apply(
                                                                                              i,
                                                                                              Transmission::new
                                                                                          ));

    public static final StreamCodec<RegistryFriendlyByteBuf, Transmission> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), Transmission::getNodePositions,
        ItemStack.STREAM_CODEC, Transmission::getStack,
        ByteBufCodecs.VAR_LONG, Transmission::getAmount,
        ByteBufCodecs.VAR_INT, Transmission::getVertexTime,
        Transmission::new
    );

    private @Nullable ServerPastelNetwork network;
    private final List<BlockPos> nodePositions;
    private final ItemStack stack;
    private final long amount;
    private final int vertexTime;

    public Transmission(List<BlockPos> nodePositions, ItemStack stack, long amount, int vertexTime) {
        this.nodePositions = nodePositions;
        this.stack = stack;
        this.amount = amount;
        this.vertexTime = vertexTime;
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

    public ItemStack getStack() {
        return this.stack;
    }

    public long getAmount() {
        return this.amount;
    }

    public BlockPos getStartPos() {
        return this.nodePositions.get(0);
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

        long inserted = 0;
        if (destinationNode != null) {
            var destinationHandler = destinationNode.getConnectedHandler();
            if (destinationHandler != null) {
                inserted = amount;
                inserted -= ItemHandlerHelper.insertItemStacked(
                                                 destinationHandler, stack.copyWithCount((int) amount), false)
                                             .getCount();
                destinationNode.addItemCountUnderway(-amount);
            }
        }
        if (inserted != amount) {
            InWorldInteractionHelper.scatter(
                world, destinationPos.getX() + 0.5, destinationPos.getY() + 0.5, destinationPos.getZ() + 0.5, stack,
                amount - inserted
            );
        }
    }

}

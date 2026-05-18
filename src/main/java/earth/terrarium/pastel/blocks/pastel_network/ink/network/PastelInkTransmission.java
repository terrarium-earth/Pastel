package earth.terrarium.pastel.blocks.pastel_network.ink.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.InkStorageBlockEntity;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.blocks.pastel_network.ink.nodes.PastelInkNodeBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.network.PastelNetwork;
import earth.terrarium.pastel.blocks.pastel_network.network.PastelTransmission;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import earth.terrarium.pastel.helpers.data.SchedulerMap;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PastelInkTransmission extends PastelTransmission {

    public static final Codec<PastelInkTransmission> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                              BlockPos.CODEC.listOf()
                                                                                                            .fieldOf(
                                                                                                                "node_positions")
                                                                                                            .forGetter(
                                                                                                                PastelInkTransmission::getNodePositions),
                                                                                              InkColor.CODEC.fieldOf("ink").forGetter(PastelInkTransmission::getInkColor),
                                                                                              Codec.LONG.fieldOf(
                                                                                                  "amount")
                                                                                                        .forGetter(
                                                                                                            PastelInkTransmission::getAmount),
                                                                                              Codec.INT.fieldOf(
                                                                                                  "vertex_time")
                                                                                                       .forGetter(
                                                                                                           PastelInkTransmission::getVertexTime)
                                                                                          )
                                                                                             .apply(
                                                                                                 i,
                                                                                                 PastelInkTransmission::new
                                                                                          ));

    public static final StreamCodec<RegistryFriendlyByteBuf, PastelInkTransmission> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), PastelInkTransmission::getNodePositions,
        InkColor.STREAM_CODEC,PastelInkTransmission::getInkColor,
        ByteBufCodecs.VAR_LONG, PastelInkTransmission::getAmount,
        ByteBufCodecs.VAR_INT, PastelInkTransmission::getVertexTime,
        PastelInkTransmission::new
    );

    private @Nullable ServerPastelInkNetwork network;
    private final List<BlockPos> nodePositions;
    private final InkColor inkC;
    private final long amount;
    private final int vertexTime;
    //To re-use the transmission code we need an itemstack.... hence 'ghost'
    private static final ItemStack ghost = new ItemStack(PastelItems.INK_FLASK.get());

    public PastelInkTransmission(List<BlockPos> nodePositions, InkColor inky, long amount, int vertexTime) {
        super(nodePositions,ghost,amount,vertexTime);
        this.nodePositions = nodePositions;
        this.inkC = inky;
        this.amount = amount;
        this.vertexTime = vertexTime;
    }

    public void setNetwork(@NotNull ServerPastelInkNetwork network) {
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

    public InkColor getInkColor() {
        return this.inkC;
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
        @Nullable PastelInkNodeBlockEntity destinationNode = this.network.getLoadedNodeAt(destinationPos);

        if(destinationNode != null) {
            InkStorageBlockEntity<?> handler = destinationNode.getConnectedHandler();
            if(handler != null) {
                handler.getEnergyStorage()
                       .addEnergy(inkC, amount);
                handler.setInkDirty();
                destinationNode.addItemCountUnderway(-amount);
            }
        }
    }
}

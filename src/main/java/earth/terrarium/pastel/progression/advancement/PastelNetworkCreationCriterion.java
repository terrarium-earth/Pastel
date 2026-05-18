package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.pastel_network.ink.network.ServerPastelInkNetwork;
import earth.terrarium.pastel.blocks.pastel_network.ink.nodes.PastelInkNodeType;
import earth.terrarium.pastel.blocks.pastel_network.network.PastelNetwork;
import earth.terrarium.pastel.blocks.pastel_network.network.ServerPastelNetwork;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeType;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Optional;

public class PastelNetworkCreationCriterion extends SimpleCriterionTrigger<PastelNetworkCreationCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("pastel_network_creation");

    public void trigger(ServerPlayer player,PastelNetwork<ServerLevel> network) {

        if(network instanceof ServerPastelInkNetwork inkd) {
            this.trigger(
                player, (conditions) -> conditions.matches(
                    inkd.getLoadedNodes(PastelInkNodeType.CONNECTION)
                           .size(), inkd.getLoadedNodes(PastelInkNodeType.PROVIDER)
                                           .size(),
                    0, 0, inkd.getLoadedNodes(PastelInkNodeType.GATHER)
                                                           .size(), 0
                )
            );
        }
        else if (network instanceof ServerPastelNetwork inknt) {
            this.trigger(
                player, (conditions) -> conditions.matches(
                    inknt.getLoadedNodes(PastelNodeType.CONNECTION)
                           .size(), inknt.getLoadedNodes(PastelNodeType.PROVIDER)
                                           .size(),
                    inknt.getLoadedNodes(PastelNodeType.STORAGE)
                           .size(), inknt.getLoadedNodes(PastelNodeType.SENDER)
                                           .size(), inknt.getLoadedNodes(PastelNodeType.GATHER)
                                                           .size(), inknt.getLoadedNodes(PastelNodeType.BUFFER)
                                                                           .size()
                )
            );
        }
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        MinMaxBounds.Ints totalNodes,
        MinMaxBounds.Ints connectionNodes,
        MinMaxBounds.Ints providerNodes,
        MinMaxBounds.Ints storageNodes,
        MinMaxBounds.Ints senderNodes,
        MinMaxBounds.Ints gatherNodes,
        MinMaxBounds.Ints bufferNodes
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                                                                                                        ContextAwarePredicate.CODEC.optionalFieldOf("player")
                                                                                                                                   .forGetter(Conditions::player),
                                                                                                        MinMaxBounds.Ints.CODEC.optionalFieldOf("total_nodes", MinMaxBounds.Ints.ANY)
                                                                                                                               .forGetter(Conditions::totalNodes),
                                                                                                        MinMaxBounds.Ints.CODEC.optionalFieldOf("connection_nodes", MinMaxBounds.Ints.ANY)
                                                                                                                               .forGetter(Conditions::connectionNodes),
                                                                                                        MinMaxBounds.Ints.CODEC.optionalFieldOf("provider_nodes", MinMaxBounds.Ints.ANY)
                                                                                                                               .forGetter(Conditions::providerNodes),
                                                                                                        MinMaxBounds.Ints.CODEC.optionalFieldOf("storage_nodes", MinMaxBounds.Ints.ANY)
                                                                                                                               .forGetter(Conditions::storageNodes),
                                                                                                        MinMaxBounds.Ints.CODEC.optionalFieldOf("sender_nodes", MinMaxBounds.Ints.ANY)
                                                                                                                               .forGetter(Conditions::senderNodes),
                                                                                                        MinMaxBounds.Ints.CODEC.optionalFieldOf("gather_nodes", MinMaxBounds.Ints.ANY)
                                                                                                                               .forGetter(Conditions::gatherNodes),
                                                                                                        MinMaxBounds.Ints.CODEC.optionalFieldOf("buffer_nodes", MinMaxBounds.Ints.ANY)
                                                                                                                               .forGetter(Conditions::bufferNodes)
                                                                                                    )
                                                                                                    .apply(
                                                                                                        instance,
                                                                                                        Conditions::new
                                                                                                    ));


        public boolean matches(
            int connectionNodes, int providerNodes, int storageNodes, int senderNodes, int gatherNodes,
            int bufferNodes
        ) {
            return this.totalNodes.matches(
                connectionNodes + providerNodes + storageNodes + senderNodes + gatherNodes) &&
                   this.connectionNodes.matches(connectionNodes) && this.providerNodes.matches(providerNodes) &&
                   this.storageNodes.matches(storageNodes) && this.senderNodes.matches(senderNodes) &&
                   this.gatherNodes.matches(gatherNodes) && this.bufferNodes.matches(bufferNodes);
        }

    }

}

package earth.terrarium.pastel.blocks.pastel_network.network;

import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PastelNetwork<W extends Level> {

    protected Graph<BlockPos, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    protected final W world;
    protected final UUID uuid;
    protected int color;

    public enum NodePriority {
        GENERIC,
        MODERATE,
        HIGH
    }

    public PastelNetwork(W world, UUID uuid, int color) {
        this.world = world;
        this.uuid = uuid;
        this.color = color;
    }

    public int size() {
        return graph.vertexSet()
                    .size();
    }

    public W getLevel() {
        return this.world;
    }

    public Graph<BlockPos, DefaultEdge> getGraph() {
        return this.graph;
    }

    public boolean addEdge(PastelNodeBlockEntity node, PastelNodeBlockEntity parent) {
        return addEdge(node.getBlockPos(), parent.getBlockPos());
    }

    public boolean addEdge(BlockPos pos1, BlockPos pos2) {
        // under ordinary circumstances, these two lines do nothing since the node should already be in the network if
        // this is called. HOWEVER if you ctrl-pick block a pastel node the network uuid is saved meaning it thinks it
        // is in the network when it is not. so we add it to the graph to prevent a crash. this doesn't have any side
        // effects since ServerPastelNetwork will override the node's network uuid if you move it to another network
        graph.addVertex(pos1);
        graph.addVertex(pos2);
        if (!hasEdge(pos1, pos2)) {
            graph.addEdge(pos1, pos2);
            return true;
        }
        return false;
    }

    public boolean hasEdge(BlockPos pos1, BlockPos pos2) {
        if (!graph.containsVertex(pos1) || !graph.containsVertex(pos2))
            return false;

        return graph.containsEdge(pos1, pos2);
    }

    public boolean removeEdge(PastelNodeBlockEntity node, PastelNodeBlockEntity parent) {
        return graph.removeEdge(node.getBlockPos(), parent.getBlockPos()) != null;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(Optional<DyeColor> dyeColor) {
        if (dyeColor.isEmpty())
            return;

        var newColor = dyeColor.get()
                               .getTextureDiffuseColor();

        if (newColor == this.color)
            return;

        this.color = newColor;
    }

    public void setGraph(Graph<BlockPos, DefaultEdge> graph) {
        this.graph = graph;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof PastelNetwork<?> p) {
            return this.uuid.equals(p.uuid);
        }
        return false;
    }

    //TODO: make this into a CODEC after the 1.21.1 port is done.
    public CompoundTag graphToNbt() {
        var vertices = new ArrayList<>(graph.vertexSet());
        var graphStorage = new CompoundTag();
        graphStorage.putInt("Size", vertices.size());
        for (int i = 0; i < vertices.size(); i++) {
            var vertex = vertices.get(i);

            // Store the Vertex
            graphStorage.putLong("Vertex" + i, vertex.asLong());

            // Save the edges
            int currentVertex = i;
            var edgeIndexes = graph.edgesOf(vertex)
                                   .stream()
                                   .map(graph::getEdgeTarget)
                                   .mapToInt(vertices::indexOf)
                                   .filter(v -> v != currentVertex)
                                   .boxed()
                                   .collect(Collectors.toList());

            if (edgeIndexes.isEmpty())
                continue;

            edgeIndexes.add(0, vertices.indexOf(vertex));

            graphStorage.putIntArray("EdgeIndexes" + i, edgeIndexes);
        }

        return graphStorage;
    }

    public static Graph<BlockPos, DefaultEdge> graphFromNbt(CompoundTag nbt) {
        Graph<BlockPos, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        var size = nbt.getInt("Size");
        var vertices = new ArrayList<BlockPos>();
        for (int i = 0; i < size; i++) {
            var vertex = BlockPos.of(nbt.getLong("Vertex" + i));
            vertices.add(vertex);
            graph.addVertex(vertex);
        }

        for (int i = 0; i < size; i++) {
            if (!nbt.contains("EdgeIndexes" + i))
                continue;
            var edgeIndexes = nbt.getIntArray("EdgeIndexes" + i);
            var source = vertices.get(edgeIndexes[0]);
            for (int targetIndex = 1; targetIndex < edgeIndexes.length; targetIndex++) {
                var target = vertices.get(edgeIndexes[targetIndex]);
                if (!graph.containsEdge(source, target))
                    graph.addEdge(source, target);
            }
        }

        return graph;
    }

    public String getNodeDebugText() {
        return "UUID: " +
               uuid.toString() +
               " - Edges: " +
               graph.edgeSet()
                    .size() +
               " - Vertices: " +
               graph.vertexSet()
                    .size();
    }

}

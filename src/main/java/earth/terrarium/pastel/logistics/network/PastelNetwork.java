package earth.terrarium.pastel.logistics.network;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class PastelNetwork<L extends Level> {

    protected Graph<BlockPos, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    protected final L level;
    protected final UUID uuid;
    protected int color;

    public PastelNetwork(L level, UUID uuid, int color) {
        this.level = level;
        this.uuid = uuid;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public UUID getUuid() {
        return uuid;
    }

    public L getLevel() {
        return level;
    }

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

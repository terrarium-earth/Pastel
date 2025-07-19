package earth.terrarium.pastel.blocks.pastel_network.network;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.blocks.pastel_network.PastelRenderHelper;
import net.minecraft.client.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.joml.Math;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class ClientPastelNetworkManager implements PastelNetworkManager<ClientLevel, ClientPastelNetwork>, Clearable {

    protected static final int MAX_RENDER_DISTANCE_SQUARED = 48 * 48;

    private final List<ClientPastelNetwork> networks = new ArrayList<>();

    @Override
    public Optional<? extends ClientPastelNetwork> getNetwork(UUID uuid) {
        return networks.stream()
                       .filter(n -> n.uuid.equals(uuid))
                       .findFirst();
    }

    @Override
    public ClientPastelNetwork createNetwork(ClientLevel level, UUID uuid, int color) {
        ClientPastelNetwork network = new ClientPastelNetwork(level, uuid, color);
        this.networks.add(network);
        return network;
    }

    public void renderLines(ClientLevel level, PoseStack matrices, MultiBufferSource bufferSource, Camera camera) {
        final Vec3 pos = camera.getPosition();
        final BlockPos blockPos = camera.getBlockPosition();

        long worldTime = level.getGameTime();
        for (ClientPastelNetwork network : this.networks) {
            if (network.getLevel()
                       .dimensionType() != level.dimensionType()) continue;

            Graph<BlockPos, DefaultEdge> graph = network.getGraph();
            int color = network.getColor();
            float[] colors = PastelRenderHelper.unpackNormalizedColor(color);

            for (DefaultEdge edge : graph.edgeSet()) {
                BlockPos source = graph.getEdgeSource(edge);
                BlockPos target = graph.getEdgeTarget(edge);

                // do not render lines that are far away to save a lot of fps
                if (blockPos.distSqr(source) > MAX_RENDER_DISTANCE_SQUARED && blockPos.distSqr(target) >
                                                                              MAX_RENDER_DISTANCE_SQUARED) {
                    continue;
                }

                matrices.pushPose();
                matrices.translate(-pos.x, -pos.y, -pos.z);
                var cross = source.cross(target);
                var interval = (cross.getX() + cross.getY() + cross.getZ() + worldTime) % 1000000F;
                var alpha = (1.0 - (Math.max(Math.sin((interval / 17F)) * 2.5 - 2, 0)));
                colors[0] = (float) alpha;
                PastelRenderHelper.renderLineTo(matrices, bufferSource, colors, source, target);

                matrices.popPose();
            }
        }
    }

    @Override
    public void clearContent() {
        this.networks.clear();
    }

    @Override
    public void removeNetwork(UUID uuid) {
        ClientPastelNetwork foundNetwork = null;
        for (ClientPastelNetwork network : this.networks) {
            if (network.uuid.equals(uuid)) {
                foundNetwork = network;
                break;
            }
        }
        if (foundNetwork != null) {
            this.networks.remove(foundNetwork);
        }
    }

}

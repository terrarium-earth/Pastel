package earth.terrarium.pastel.logistics.network;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.UUID;

public class ClientPastelNetwork extends PastelNetwork<ClientLevel> {


    public ClientPastelNetwork(ClientLevel world, UUID uuid, int color) {
        super(world, uuid, color);
    }

}

package earth.terrarium.pastel.logistics.network;

import net.minecraft.server.level.ServerLevel;

import java.util.UUID;

public class ServerPastelNetwork extends PastelNetwork<ServerLevel> {

    public ServerPastelNetwork(ServerLevel world, UUID uuid, int color) {
        super(world, uuid, color);
    }
}

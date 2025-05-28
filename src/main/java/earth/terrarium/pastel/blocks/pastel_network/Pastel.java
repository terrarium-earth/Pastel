package earth.terrarium.pastel.blocks.pastel_network;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.blocks.pastel_network.network.ClientPastelNetworkManager;
import earth.terrarium.pastel.blocks.pastel_network.network.PastelNetworkManager;
import earth.terrarium.pastel.blocks.pastel_network.network.ServerPastelNetworkManager;
import earth.terrarium.pastel.particle.render.EarlyRenderingParticleContainer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.server.*;
import net.neoforged.neoforge.server.*;

public class Pastel {

    @OnlyIn(Dist.CLIENT)
    private static ClientPastelNetworkManager clientManager;
    private static ServerPastelNetworkManager serverManager;

    @OnlyIn(Dist.CLIENT)
    public static ClientPastelNetworkManager getClientInstance() {
        if (clientManager == null) {
            clientManager = new ClientPastelNetworkManager();
        }
        return clientManager;
    }

    public static ServerPastelNetworkManager getServerInstance() {
        if (serverManager == null && ServerLifecycleHooks.getCurrentServer() != null) {
            serverManager = ServerPastelNetworkManager.get(ServerLifecycleHooks.getCurrentServer().overworld());
        }
        return serverManager;
    }
	
	public static PastelNetworkManager<?, ?> getInstance(boolean client) {
        if (client) {
            return getClientInstance();
        } else {
            return getServerInstance();
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void clearClientInstance() {
		getClientInstance().clearContent();
        EarlyRenderingParticleContainer.clear();
    }

    public static void clearServerInstance(ServerStoppedEvent event) {
        serverManager = null;
    }

}

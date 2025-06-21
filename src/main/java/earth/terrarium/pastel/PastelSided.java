package earth.terrarium.pastel;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class PastelSided {

    /**
     * Bouncer
     */
    public static MinecraftServer getClientServer() {
        return Minecraft.getInstance().getSingleplayerServer();
    }
}

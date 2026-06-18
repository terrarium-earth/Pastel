package earth.terrarium.pastel.entity.entity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(
    Dist.CLIENT
)
public class CanvasWorkaroundPlayerEntity extends RemotePlayer {
    public PlayerInfo playerInfo;

    public GameProfile profile;

    public CanvasWorkaroundPlayerEntity(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
        profile = gameProfile;
        playerInfo = new PlayerInfo(profile, false);
    }

    @Override
    protected @Nullable PlayerInfo getPlayerInfo() {
        var playerInfo = super.getPlayerInfo();
        if (playerInfo == null) {
            playerInfo = new PlayerInfo(profile, false);
        }
        return playerInfo;
    }

    @Override
    public PlayerSkin getSkin() {
        if (Iterables.getFirst(profile.getProperties().get("textures"), null) == null) {
            var profileResponse = Minecraft
                .getInstance()
                .getMinecraftSessionService()
                .fetchProfile(profile.getId(), true);
            if (profileResponse != null) profile = profileResponse.profile();
        }
        return super.getSkin();
    }
}

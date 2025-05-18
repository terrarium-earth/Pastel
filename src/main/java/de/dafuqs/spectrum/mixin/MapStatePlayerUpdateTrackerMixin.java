package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.spectrum.items.map.ArtisansAtlasState;
import de.dafuqs.spectrum.networking.s2c_payloads.SyncArtisansAtlasPayload;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Optional;

@Mixin(MapItemSavedData.HoldingPlayer.class)
public class MapStatePlayerUpdateTrackerMixin {

    @Shadow
    @Final
    public Player player;

    @Inject(method = "nextUpdatePacket", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/saveddata/maps/MapId;BZLjava/util/Collection;Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData$MapPatch;)Lnet/minecraft/network/protocol/game/ClientboundMapItemDataPacket;"), cancellable = true)
    private void spectrum$getArtisansAtlasPacket(MapId mapId, CallbackInfoReturnable<Packet<?>> cir, @Local MapItemSavedData.MapPatch updateData, @Local Collection<MapDecoration> icons) {
        Level world = player.level();
		if (world != null && world.getMapData(mapId) instanceof ArtisansAtlasState state) {
			var mapPacket = new ClientboundMapItemDataPacket(mapId, state.scale, state.locked, icons, updateData);
			var payload = new SyncArtisansAtlasPayload(Optional.ofNullable(state.getTargetId()), mapPacket);
			var customPacket = new ClientboundCustomPayloadPacket(payload);
			cir.setReturnValue(customPacket);
		}
    }
}

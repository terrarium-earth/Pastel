package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.items.map.ArtisansAtlasState;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
    MapItemSavedData.class
)
public class MapStateMixin {

    // Caches the created state between the two mixins
    @Unique @Nullable private static ArtisansAtlasState atlasState = null;

    @Inject(
        method = "load", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;<init>" + "(IIBZZZLnet/minecraft/resources/ResourceKey;)V"
        )
    )
    private static void fromNbt_newMapState(
        CompoundTag nbt,
        HolderLookup.Provider registryLookup,
        CallbackInfoReturnable<MapItemSavedData> cir,
        @Local
        ResourceKey<Level> registryKey,
        @Local(
            ordinal = 0
        )
        int centerX,
        @Local(
            ordinal = 1
        )
        int centerZ,
        @Local
        byte scale,
        @Local(
            ordinal = 0
        )
        boolean showIcons,
        @Local(
            ordinal = 1
        )
        boolean unlimitedTracking,
        @Local(
            ordinal = 2
        )
        boolean locked
    ) {
        if (nbt.contains("isArtisansAtlas", Tag.TAG_BYTE) && nbt.getBoolean("isArtisansAtlas")) {
            atlasState = new ArtisansAtlasState(
                centerX,
                centerZ,
                scale,
                showIcons,
                unlimitedTracking,
                locked,
                registryKey,
                nbt
            );
        }
    }

    @ModifyVariable(
        method = "load", slice = @Slice(
            from = @At(
                value = "INVOKE", target = "Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;<init>" + "(IIBZZZLnet/minecraft/resources/ResourceKey;)V"
            ), to = @At(
                value = "TAIL"
            )
        ), at = @At(
            value = "STORE"
        )
    )
    private static MapItemSavedData fromNbt_storeMapState(MapItemSavedData vanillaState) {
        if (atlasState != null) {
            ArtisansAtlasState state = atlasState;
            atlasState = null;
            return state;
        }
        return vanillaState;
    }

}

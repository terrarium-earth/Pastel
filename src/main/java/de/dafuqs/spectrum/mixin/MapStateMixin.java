package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.sugar.*;
import de.dafuqs.spectrum.items.map.*;
import net.minecraft.item.map.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(MapState.class)
public class MapStateMixin {

    // Caches the created state between the two mixins
	@Unique
	@Nullable
    private static ArtisansAtlasState atlasState = null;
	
	@Inject(method = "fromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/map/MapState;<init>(IIBZZZLnet/minecraft/registry/RegistryKey;)V"))
	private static void spectrum$fromNbt_newMapState(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfoReturnable<MapState> cir, @Local RegistryKey<World> registryKey, @Local(ordinal = 0) int centerX, @Local(ordinal = 1) int centerZ, @Local byte scale, @Local(ordinal = 0) boolean showIcons, @Local(ordinal = 1) boolean unlimitedTracking, @Local(ordinal = 2) boolean locked) {
        if (nbt.contains("isArtisansAtlas", NbtElement.BYTE_TYPE) && nbt.getBoolean("isArtisansAtlas")) {
			atlasState = new ArtisansAtlasState(centerX, centerZ, scale, showIcons, unlimitedTracking, locked, registryKey, nbt);
        }
    }

    @ModifyVariable(
            method = "fromNbt",
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/map/MapState;<init>(IIBZZZLnet/minecraft/registry/RegistryKey;)V"),
                    to = @At(value = "TAIL")
            ),
            at = @At(value = "STORE")
    )
    private static MapState spectrum$fromNbt_storeMapState(MapState vanillaState) {
        if (atlasState != null) {
            ArtisansAtlasState state = atlasState;
            atlasState = null;
            return state;
        }
        return vanillaState;
    }

}

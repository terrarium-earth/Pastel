package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import earth.terrarium.pastel.loot.PastelLootGrossHacks;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ReloadableServerRegistries.class)
public class ReloadableServerRegistriesMixin {

    @ModifyExpressionValue(method = "reload", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/LayeredRegistryAccess;getAccessForLoading(Ljava/lang/Object;)Lnet/minecraft/core/RegistryAccess$Frozen;"))
    private static RegistryAccess.Frozen catchRegistryAccess(RegistryAccess.Frozen original) {
        PastelLootGrossHacks.access = original;
        return original;
    }
}

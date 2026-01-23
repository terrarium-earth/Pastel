package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import earth.terrarium.pastel.items.armor.CrystalArmorItem;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @WrapOperation(method = "aiStep",
                   at = @At(value = "INVOKE",
                            target = "Lnet/minecraft/client/player/LocalPlayer;onGround()Z",
                            ordinal = 3))
    private boolean doubleJump(LocalPlayer instance, Operation<Boolean> original) {
        boolean actuallyOnGround = original.call(instance);
        return CrystalArmorItem.doubleJump(instance, actuallyOnGround);
    }
}

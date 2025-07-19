package earth.terrarium.pastel.mixin;

import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockAttachedEntity.class)
public abstract class MC252934Mixin {

    @Redirect(method = "readAdditionalSaveData",
              at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V",
                       remap = false))
    private void fixMC252934(Logger thisLogger, String format, Object arg) {
    }

}

package earth.terrarium.pastel.mixin.client;

import earth.terrarium.pastel.PastelClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@OnlyIn(Dist.CLIENT)
@Mixin(DimensionType.class)
public abstract class DimensionTypeMixin {

    @ModifyArg(method = "timeOfDay", at = @At(value = "INVOKE", target = "Ljava/util/OptionalLong;orElse(J)J"))
    private long getLerpedSkyAngle(long time) {
        if (!Minecraft.getInstance()
                      .isPaused() && PastelClient.skyLerper.isActive((DimensionType) (Object) this)) {
            return PastelClient.skyLerper.tickLerp(time, Minecraft.getInstance()
                                                                  .getTimer()
                                                                  .getGameTimeDeltaPartialTick(false)
            );
        } else {
            return time;
        }
    }

}

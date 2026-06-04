package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.registries.PastelFluidTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(Entity.class)
public abstract class EntityApplyFluidsMixin {

    @Final
    @Shadow
    private Set<TagKey<Fluid>> fluidOnEyes;

    @Inject(method = "isEyeInFluid", at = @At("RETURN"), cancellable = true)
    public void isSubmergedIn(TagKey<Fluid> fluidTag, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && fluidTag == FluidTags.WATER) {
            cir.setReturnValue(this.fluidOnEyes.contains(PastelFluidTags.SWIMMABLE_FLUID));
        }
    }

    @Inject(method = "isUnderWater", at = @At("RETURN"), cancellable = true)
    public void isSubmergedInWater(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && this.fluidOnEyes.contains(PastelFluidTags.SWIMMABLE_FLUID)) {
            //this.submergedFluidTag.add(FluidTags.WATER);
            cir.setReturnValue(true);
        }
    }
}

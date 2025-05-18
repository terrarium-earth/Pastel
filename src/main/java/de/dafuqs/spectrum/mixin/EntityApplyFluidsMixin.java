package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.api.entity.TouchingWaterAware;
import de.dafuqs.spectrum.registries.SpectrumFluidTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(Entity.class)
public abstract class EntityApplyFluidsMixin implements TouchingWaterAware {
	
	@Final
	@Shadow
	private Set<TagKey<Fluid>> fluidOnEyes;

	@Unique
	private boolean actuallyTouchingWater = false;

	@Override
	public boolean spectrum$isActuallyTouchingWater() {
		return this.actuallyTouchingWater;
	}

	@Override
	public void spectrum$setActuallyTouchingWater(boolean actuallyTouchingWater) { this.actuallyTouchingWater = actuallyTouchingWater; }
	
	@Inject(method = "isEyeInFluid", at = @At("RETURN"), cancellable = true)
	public void spectrum$isSubmergedIn(TagKey<Fluid> fluidTag, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue() && fluidTag == FluidTags.WATER) {
			cir.setReturnValue(this.fluidOnEyes.contains(SpectrumFluidTags.SWIMMABLE_FLUID));
		}
	}
	
	@Inject(method = "isUnderWater", at = @At("RETURN"), cancellable = true)
	public void spectrum$isSubmergedInWater(CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue() && this.fluidOnEyes.contains(SpectrumFluidTags.SWIMMABLE_FLUID)) {
			//this.submergedFluidTag.add(FluidTags.WATER);
			cir.setReturnValue(true);
		}
	}
	

	



	
}

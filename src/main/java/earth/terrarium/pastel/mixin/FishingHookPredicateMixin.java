package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.entity.entity.SpectrumFishingBobberEntity;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FishingHookPredicate.class)
public abstract class FishingHookPredicateMixin {
	
	@Shadow
	@Final
	private Optional<Boolean> inOpenWater;
	
	@Inject(method = "matches", at = @At(value = "HEAD"), cancellable = true)
	public void spectrum$test(Entity entity, ServerLevel world, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof SpectrumFishingBobberEntity spectrumFishingBobberEntity) {
			if (this.inOpenWater.isEmpty()) {
				cir.setReturnValue(true);
			}
			cir.setReturnValue(this.inOpenWater.get() == spectrumFishingBobberEntity.isInOpenWater());
		}
	}
	
}

package de.dafuqs.spectrum.mixin;

import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SpawnerBlockEntity.class, SignBlockEntity.class})
public abstract class AllowCopyBlockEntityDataMixin {
	
	@Inject(method = "onlyOpCanSetNbt", at = @At("HEAD"), cancellable = true)
	public void allowPlacingSpawnerWithBlockData(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		callbackInfoReturnable.setReturnValue(false);
	}
	
}
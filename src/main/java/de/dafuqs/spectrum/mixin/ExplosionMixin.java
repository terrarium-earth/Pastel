package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.blocks.PrimordialFireBlock;
import de.dafuqs.spectrum.registries.SpectrumDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public class ExplosionMixin {
	
	@Shadow
	@Final
	private DamageSource damageSource;
	
	@Inject(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BaseFireBlock;getState(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
	private void spectrum$modifyExplosion(boolean particles, CallbackInfo ci) {
		if (this.damageSource.is(SpectrumDamageTypes.INCANDESCENCE)) {
			PrimordialFireBlock.EXPLOSION_CAUSES_PRIMORDIAL_FIRE_FLAG = true;
		}
	}
	
}

package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.registries.PastelDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireBlock.class)
public class FireBlockMixin {
	
	@ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/server/level/ServerLevel.isRaining ()Z", ordinal = 0))
	public boolean spectrum$extinguishInPermanentRain(boolean original, @Local(argsOnly = true) ServerLevel world) {
		if (world.dimension().equals(PastelDimensions.DIMENSION_KEY)) {
			return true;
		}
		return original;
	}
	
	@ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "net/minecraft/server/level/ServerLevel.isRaining ()Z", ordinal = 1))
	public boolean spectrum$assuageInPermanentRain(boolean original, @Local(argsOnly = true) ServerLevel world) {
		if (world.dimension().equals(PastelDimensions.DIMENSION_KEY)) {
			return true;
		}
		return original;
	}
}

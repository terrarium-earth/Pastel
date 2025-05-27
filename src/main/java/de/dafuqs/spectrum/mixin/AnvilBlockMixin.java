package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin {
	
	@Inject(at = @At("HEAD"), method = "damage", cancellable = true)
	private static void makeBedrockAnvilUnbreakable(BlockState fallingState, CallbackInfoReturnable<BlockState> callbackInfoReturnable) {
		if (fallingState.is(SpectrumBlocks.BEDROCK_ANVIL.get())) {
			callbackInfoReturnable.setReturnValue(fallingState);
		}
	}
}

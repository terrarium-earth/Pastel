package earth.terrarium.pastel.mixin.client;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(HalfTransparentBlock.class)
public abstract class TranslucentBlockMixin {

	@Inject(method = "skipRendering", at = @At("HEAD"), cancellable = true)
	public void dontRenderVanillaPlayerOnlyGlass(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (state.is(Blocks.GLASS) && stateFrom.is(PastelBlocks.SEMI_PERMEABLE_GLASS.get())
				|| state.is(Blocks.TINTED_GLASS) && stateFrom.is(PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS.get()))
			callbackInfoReturnable.setReturnValue(true);
	}
	
}
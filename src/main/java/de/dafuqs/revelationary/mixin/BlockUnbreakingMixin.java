package de.dafuqs.revelationary.mixin;

import de.dafuqs.revelationary.RevelationRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public abstract class BlockUnbreakingMixin {
	/**
	 * Prevent players from accidentally mining unrevealed blocks. In no way exhaustive.
	 * Cloaked plants will still drop themselves when the block below them is broken, for example
	 */
	@Inject(method = "getDestroyProgress", at = @At("HEAD"), cancellable = true)
	public void revelationary$calcBlockBreakingDelta(BlockState state, Player player, BlockGetter world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
		if(!RevelationRegistry.isVisibleTo(state, player)) {
			cir.setReturnValue(0F);
		}
	}
}

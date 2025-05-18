package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.blocks.CrackedEndPortalFrameBlock;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.EnderEyeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderEyeItem.class)
public abstract class EnderEyeItemMixin {
	
	@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
	public void useOnBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> callbackInfoReturnable) {
		Level world = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		BlockState blockState = world.getBlockState(blockPos);
		boolean eyeAdded = false;
		if (blockState.is(SpectrumBlocks.CRACKED_END_PORTAL_FRAME) && blockState.getValue(CrackedEndPortalFrameBlock.EYE_TYPE).equals(CrackedEndPortalFrameBlock.EndPortalFrameEye.NONE)) {
			BlockState targetBlockState = blockState.setValue(CrackedEndPortalFrameBlock.EYE_TYPE, CrackedEndPortalFrameBlock.EndPortalFrameEye.WITH_EYE_OF_ENDER);
			Block.pushEntitiesUp(blockState, targetBlockState, world, blockPos);
			world.setBlock(blockPos, targetBlockState, 2);
			world.updateNeighbourForOutputSignal(blockPos, SpectrumBlocks.CRACKED_END_PORTAL_FRAME);
			eyeAdded = true;
		} else if (blockState.is(Blocks.END_PORTAL_FRAME) && blockState.getValue(EndPortalFrameBlock.HAS_EYE).equals(false)) {
			BlockState targetBlockState = blockState.setValue(EndPortalFrameBlock.HAS_EYE, true);
			Block.pushEntitiesUp(blockState, targetBlockState, world, blockPos);
			world.setBlock(blockPos, targetBlockState, 2);
			world.updateNeighbourForOutputSignal(blockPos, Blocks.END_PORTAL_FRAME);
			eyeAdded = true;
		}
		
		if (eyeAdded) {
			if (world.isClientSide) {
				callbackInfoReturnable.setReturnValue(InteractionResult.SUCCESS);
			} else {
				context.getItemInHand().shrink(1);
				world.levelEvent(LevelEvent.END_PORTAL_FRAME_FILL, blockPos, 0);
				
				// Search for a valid end portal position. Found => create portal!
				CrackedEndPortalFrameBlock.checkAndFillEndPortal(world, blockPos);
				
				callbackInfoReturnable.setReturnValue(InteractionResult.CONSUME);
			}
		}
	}
	
}

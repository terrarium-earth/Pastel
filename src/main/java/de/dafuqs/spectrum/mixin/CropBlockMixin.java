package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.dafuqs.spectrum.blocks.farming.SpectrumFarmlandBlock;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

	@ModifyExpressionValue(method = "getGrowthSpeed", at = @At(value = "INVOKE", ordinal = 0, target = "net/minecraft/world/level/BlockGetter.getBlockState (Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
	private static BlockState spectrum$getAvailableMoisture(BlockState original) {
		Block originalBlock = original.getBlock();
		if (originalBlock instanceof SpectrumFarmlandBlock) {
			return Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, original.getValue(FarmBlock.MOISTURE));
		}
		return original;
	}
	
	@Inject(method = "growCrops", at = @At("HEAD"), cancellable = true)
	private void spectrum$cancelGrowthAttempts(Level world, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (world.getBlockState(pos.below()).is(SpectrumBlocks.TILLED_SHALE_CLAY)) {
			ci.cancel();
		}
	}
	
	@Inject(method = "growCrops", at = @At("HEAD"), cancellable = true)
	public void spectrum$hasRandomTicks(Level world, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (world.getBlockState(pos.below()).is(SpectrumBlocks.TILLED_SHALE_CLAY)) {
			ci.cancel();
		}
	}
	
}

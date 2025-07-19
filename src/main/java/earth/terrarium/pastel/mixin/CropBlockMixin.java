package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import earth.terrarium.pastel.blocks.farming.PastelFarmlandBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
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
	private static BlockState getAvailableMoisture(BlockState original) {
		Block originalBlock = original.getBlock();
		if (originalBlock instanceof PastelFarmlandBlock) {
			return Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, original.getValue(FarmBlock.MOISTURE));
		}
		return original;
	}
	
	@Inject(method = "growCrops", at = @At("HEAD"), cancellable = true)
	private void cancelGrowthAttempts(Level world, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (world.getBlockState(pos.below()).is(PastelBlocks.TILLED_SHALE_CLAY.get())) {
			ci.cancel();
		}
	}
	
	@Inject(method = "growCrops", at = @At("HEAD"), cancellable = true)
	public void hasRandomTicks(Level world, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (world.getBlockState(pos.below()).is(PastelBlocks.TILLED_SHALE_CLAY.get())) {
			ci.cancel();
		}
	}
	
}

package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.blocks.PrimordialFireBlock;
import de.dafuqs.spectrum.registries.SpectrumBlockTags;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class AbstractFireBlockMixin {

    @Inject(at = @At("HEAD"), method = "getState", cancellable = true)
    private static void spectrum$getFireState(BlockGetter world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = world.getBlockState(blockpos);
		if (blockstate.is(SpectrumBlockTags.PRIMORDIAL_FIRE_BASE_BLOCKS) || PrimordialFireBlock.EXPLOSION_CAUSES_PRIMORDIAL_FIRE_FLAG) {
			PrimordialFireBlock.EXPLOSION_CAUSES_PRIMORDIAL_FIRE_FLAG = false;
			cir.setReturnValue((SpectrumBlocks.PRIMORDIAL_FIRE).getStateForPosition(world, pos));
		}
	}

}
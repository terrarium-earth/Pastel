package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.blocks.PrimordialFireBlock;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
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
    private static void getFireState(BlockGetter world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = world.getBlockState(blockpos);
        if (blockstate.is(PastelBlockTags.PRIMORDIAL_FIRE_BASE_BLOCKS) ||
            PrimordialFireBlock.EXPLOSION_CAUSES_PRIMORDIAL_FIRE_FLAG) {
            PrimordialFireBlock.EXPLOSION_CAUSES_PRIMORDIAL_FIRE_FLAG = false;
            cir.setReturnValue(
                ((PrimordialFireBlock) PastelBlocks.PRIMORDIAL_FIRE.get()).getStateForPosition(world, pos));
        }
    }

}

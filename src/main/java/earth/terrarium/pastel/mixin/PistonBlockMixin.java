package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import earth.terrarium.pastel.registries.PastelBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonBaseBlock.class)
public class PistonBlockMixin {

    @WrapOperation(method = "isPushable", at = @At(value = "INVOKE",
                                                   target = "Lnet/minecraft/world/level/block/state/BlockState;" +
                                                            "getDestroySpeed(Lnet/minecraft/world/level/BlockGetter;" +
                                                            "Lnet/minecraft/core/BlockPos;)F"))
    private static float enableUnbreakableMovement(
        BlockState instance, BlockGetter blockView, BlockPos pos, Operation<Float> original) {
        if (instance.is(PastelBlockTags.UNBREAKABLE_MOVABLE)) {
            return 0F;
        }
        return original.call(instance, blockView, pos);
    }
}

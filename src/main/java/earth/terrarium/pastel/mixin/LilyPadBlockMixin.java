package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.registries.PastelFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
    WaterlilyBlock.class
)
public class LilyPadBlockMixin {
    @ModifyReturnValue(
        method = "mayPlaceOn", at = @At(
            "RETURN"
        )
    )
    public boolean extendLilyPlaceables(boolean original, BlockState floor, BlockGetter world, BlockPos pos) {
        if (original)
            return true;
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.above());
        return (fluidState.getType() == PastelFluids.HUMUS.get() || fluidState.getType() == PastelFluids.LIQUID_CRYSTAL
            .get()) && fluidState2.getType() == Fluids.EMPTY;
    }
}

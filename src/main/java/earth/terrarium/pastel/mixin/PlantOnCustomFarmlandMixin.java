package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.blocks.farming.PastelFarmlandBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
    {
        CropBlock.class, StemBlock.class, AttachedStemBlock.class, PitcherCropBlock.class
}
)
public abstract class PlantOnCustomFarmlandMixin {

    @ModifyReturnValue(
        method = "mayPlaceOn", at = @At(
            "RETURN"
        )
    )
    public boolean canPlantOnTopOfCustomFarmland(
        boolean original,
        @NotNull BlockState floor,
        BlockGetter world,
        BlockPos pos
    ) {
        return original || floor.getBlock() instanceof PastelFarmlandBlock;
    }

}

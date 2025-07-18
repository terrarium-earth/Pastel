package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.registries.PastelBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Animal.class})
public class AnimalEntityMixin {

    // Enabled animals to spawn and pathfind
    // it does, however, not remove the ambient light requirement for animal spawns
    @Inject(method = "getWalkTargetValue", at = @At("HEAD"), cancellable = true)
    public void getPathfindingFavor(BlockPos pos, LevelReader world, CallbackInfoReturnable<Float> cir) {
        if (world.getBlockState(pos.below())
                 .is(PastelBlockTags.ANIMALS_SPAWNABLE_ON_ADDITIONS)) {
            cir.setReturnValue(10.0F);
        }
    }


}

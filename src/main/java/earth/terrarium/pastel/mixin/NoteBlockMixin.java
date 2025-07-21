package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {

    @Inject(method = "getCustomSoundId", at = @At("HEAD"), cancellable = true)
    protected void customNoteBlockSound(Level world, BlockPos pos, CallbackInfoReturnable<ResourceLocation> cir) {
        BlockState state = world.getBlockState(pos.above());
        if (state.getBlock() instanceof PastelSkullBlock pastelSkullBlock) {
            cir.setReturnValue(pastelSkullBlock.getType()
                                               .getNoteBlockSound());
        }
    }

}

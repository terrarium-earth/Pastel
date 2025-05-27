package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.blocks.deeper_down.flora.SawbladeHollyBushBlock;
import de.dafuqs.spectrum.blocks.jade_vines.JadeVinePlantBlock;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsDispenseItemBehavior.class)
public class ShearsDispenserBehaviorMixin {

    @Inject(at = @At("HEAD"), method = "tryShearBeehive", cancellable = true)
    private static void spectrum$shearsShearSawbladeHollyBushes(ServerLevel world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.is(SpectrumBlocks.SAWBLADE_HOLLY_BUSH.get())) {
            int age = blockState.getValue(SawbladeHollyBushBlock.AGE);
            if (SawbladeHollyBushBlock.canBeSheared(age)) {
                // we do not have the real shears item used in the dispenser here, but for the default loot table that does not make much of a difference
                for (ItemStack stack : JadeVinePlantBlock.getHarvestedStacks(blockState, world, pos, world.getBlockEntity(pos), null, Items.SHEARS.getDefaultInstance(), SpectrumLootTables.SAWBLADE_HOLLY_SHEARING)) {
                    SawbladeHollyBushBlock.popResource(world, pos, stack);
                }
    
                BlockState newState = blockState.setValue(SawbladeHollyBushBlock.AGE, age - 1);
                world.setBlock(pos, newState, Block.UPDATE_CLIENTS);
                world.gameEvent(null, GameEvent.SHEAR, pos);
                world.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
    
                cir.setReturnValue(true);
            }
        }
    }

}

package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.blocks.imbrifer.flora.SawbladeHollyBushBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Fox.FoxEatBerriesGoal.class)
public abstract class EatBerriesGoalMixin extends MoveToBlockGoal {

    @Unique
    private final Fox foxEntity = (Fox) mob;

    public EatBerriesGoalMixin(PathfinderMob mob, double speed, int range) {
        super(mob, speed, range);
    }

    @Inject(method = "isValidTarget", at = @At("HEAD"), cancellable = true)
    private void isTargetPos(LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.is(PastelBlocks.SAWBLADE_HOLLY_BUSH.get()) && blockState.getValue(SawbladeHollyBushBlock.AGE) ==
                                                                     SawbladeHollyBushBlock.MAX_AGE) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "onReachedTarget", at = @At("HEAD"), cancellable = true)
    private void eatBerries(CallbackInfo ci) {
        if (foxEntity.level()
                     .getGameRules()
                     .getBoolean(GameRules.RULE_MOBGRIEFING)) {
            BlockState blockState = foxEntity.level()
                                             .getBlockState(this.blockPos);
            if (blockState.is(PastelBlocks.SAWBLADE_HOLLY_BUSH.get())) {
                pickSawbladeHollyBerries(blockState);
                ci.cancel();
            }
        }
    }

    @Unique
    private void pickSawbladeHollyBerries(BlockState state) {
        Level world = foxEntity.level();
        int age = state.getValue(SawbladeHollyBushBlock.AGE);
        int berriesPlucked = 1 + world.random.nextInt(2) + (age == SawbladeHollyBushBlock.MAX_AGE ? 1 : 0);
        ItemStack itemStack = foxEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty()) {
            foxEntity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(PastelItems.SAWBLADE_HOLLY_BERRY.get()));
            --berriesPlucked;
        }

        if (berriesPlucked > 0) {
            Block.popResource(
                world, this.blockPos, new ItemStack(PastelItems.SAWBLADE_HOLLY_BERRY.get(), berriesPlucked));
        }

        foxEntity.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
        world.setBlock(this.blockPos, state.setValue(SawbladeHollyBushBlock.AGE, 1), Block.UPDATE_CLIENTS);
    }


}

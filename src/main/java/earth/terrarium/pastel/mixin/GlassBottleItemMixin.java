package earth.terrarium.pastel.mixin;

import com.cmdpro.databank.DatabankUtils;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
    BottleItem.class
)
public abstract class GlassBottleItemMixin {

    @Shadow
    protected abstract ItemStack turnBottleIntoItem(ItemStack stack, Player player, ItemStack outputStack);

    @Inject(
        method = "use", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"
        ), cancellable = true
    )
    public void onUse(
        Level world,
        Player user,
        InteractionHand hand,
        CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir,
        @Local
        ItemStack handStack,
        @Local
        BlockPos blockPos
    ) {
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.is(PastelBlocks.FADING.get()) && PastelCommon.CONFIG.CanBottleUpFading && DatabankUtils
            .hasAdvancement(user, PastelAdvancements.Unlocks.Items.BOTTLE_OF_FADING)) {

            world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
            world
                .playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.BOTTLE_FILL_DRAGONBREATH,
                    SoundSource.NEUTRAL,
                    1.0F,
                    1.0F
                );
            cir
                .setReturnValue(
                    InteractionResultHolder
                        .sidedSuccess(
                            this
                                .turnBottleIntoItem(
                                    handStack,
                                    user,
                                    PastelItems.BOTTLE_OF_FADING
                                        .get()
                                        .getDefaultInstance()
                                ),
                            world.isClientSide()
                        )
                );

        } else if (blockState.is(PastelBlocks.FAILING.get()) && PastelCommon.CONFIG.CanBottleUpFailing && DatabankUtils
            .hasAdvancement(user, PastelAdvancements.Unlocks.Items.BOTTLE_OF_FAILING)) {

                world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                world
                    .playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.BOTTLE_FILL_DRAGONBREATH,
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                    );
                cir
                    .setReturnValue(
                        InteractionResultHolder
                            .sidedSuccess(
                                this
                                    .turnBottleIntoItem(
                                        handStack,
                                        user,
                                        PastelItems.BOTTLE_OF_FAILING
                                            .get()
                                            .getDefaultInstance()
                                    ),
                                world.isClientSide()
                            )
                    );

            } else if (blockState.is(PastelBlocks.RUIN.get()) && PastelCommon.CONFIG.CanBottleUpRuin && DatabankUtils
                .hasAdvancement(user, PastelAdvancements.Unlocks.Items.BOTTLE_OF_RUIN)) {

                    world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    world
                        .playSound(
                            null,
                            user.getX(),
                            user.getY(),
                            user.getZ(),
                            SoundEvents.BOTTLE_FILL_DRAGONBREATH,
                            SoundSource.NEUTRAL,
                            1.0F,
                            1.0F
                        );
                    cir
                        .setReturnValue(
                            InteractionResultHolder
                                .sidedSuccess(
                                    this
                                        .turnBottleIntoItem(
                                            handStack,
                                            user,
                                            PastelItems.BOTTLE_OF_RUIN
                                                .get()
                                                .getDefaultInstance()
                                        ),
                                    world.isClientSide()
                                )
                        );

                } else if (blockState
                    .is(PastelBlocks.FORFEITURE.get()) && PastelCommon.CONFIG.CanBottleUpForfeiture && DatabankUtils
                        .hasAdvancement(user, PastelAdvancements.Unlocks.Items.BOTTLE_OF_FORFEITURE)) {

                            world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                            world
                                .playSound(
                                    null,
                                    user.getX(),
                                    user.getY(),
                                    user.getZ(),
                                    SoundEvents.BOTTLE_FILL_DRAGONBREATH,
                                    SoundSource.NEUTRAL,
                                    1.0F,
                                    1.0F
                                );
                            cir
                                .setReturnValue(
                                    InteractionResultHolder
                                        .sidedSuccess(
                                            this
                                                .turnBottleIntoItem(
                                                    handStack,
                                                    user,
                                                    PastelItems.BOTTLE_OF_FORFEITURE
                                                        .get()
                                                        .getDefaultInstance()
                                                ),
                                            world.isClientSide()
                                        )
                                );
                        }
    }

}

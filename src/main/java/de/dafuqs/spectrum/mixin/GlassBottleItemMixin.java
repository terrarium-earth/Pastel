package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumAdvancements;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
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

@Mixin(BottleItem.class)
public abstract class GlassBottleItemMixin {
	
	@Shadow
	protected abstract ItemStack turnBottleIntoItem(ItemStack stack, Player player, ItemStack outputStack);
	
	@Inject(method = "use",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"), cancellable = true)
	public void onUse(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, @Local ItemStack handStack, @Local BlockPos blockPos) {
		BlockState blockState = world.getBlockState(blockPos);
		
		if (blockState.is(SpectrumBlocks.FADING.get())
				&& SpectrumCommon.CONFIG.CanBottleUpFading
				&& AdvancementHelper.hasAdvancement(user, SpectrumAdvancements.UNLOCK_BOTTLE_OF_FADING)) {
			
			world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
			cir.setReturnValue(InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(handStack, user, SpectrumItems.BOTTLE_OF_FADING.get().getDefaultInstance()), world.isClientSide()));
			
		} else if (blockState.is(SpectrumBlocks.FAILING.get())
				&& SpectrumCommon.CONFIG.CanBottleUpFailing
				&& AdvancementHelper.hasAdvancement(user, SpectrumAdvancements.UNLOCK_BOTTLE_OF_FAILING)) {
			
			world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
			cir.setReturnValue(InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(handStack, user, SpectrumItems.BOTTLE_OF_FAILING.get().getDefaultInstance()), world.isClientSide()));
			
		} else if (blockState.is(SpectrumBlocks.RUIN.get())
				&& SpectrumCommon.CONFIG.CanBottleUpRuin
				&& AdvancementHelper.hasAdvancement(user, SpectrumAdvancements.UNLOCK_BOTTLE_OF_RUIN)) {
			
			world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
			cir.setReturnValue(InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(handStack, user, SpectrumItems.BOTTLE_OF_RUIN.get().getDefaultInstance()), world.isClientSide()));
			
		} else if (blockState.is(SpectrumBlocks.FORFEITURE.get())
				&& SpectrumCommon.CONFIG.CanBottleUpForfeiture
				&& AdvancementHelper.hasAdvancement(user, SpectrumAdvancements.UNLOCK_BOTTLE_OF_FORFEITURE)) {
			
			world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
			cir.setReturnValue(InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(handStack, user, SpectrumItems.BOTTLE_OF_FORFEITURE.get().getDefaultInstance()), world.isClientSide()));
		}
	}
	
}

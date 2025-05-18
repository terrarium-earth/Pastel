package de.dafuqs.spectrum.api.item;

import de.dafuqs.spectrum.helpers.AoEHelper;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public interface AoEBreakingTool {
	
	default void onTryBreakBlock(ItemStack stack, BlockPos pos, Player player) {
		Level world = player.level();
		BlockHitResult hitResult = (BlockHitResult) player.pick(10, 1, false);
		if (!world.isClientSide && hitResult.getType() == HitResult.Type.BLOCK) {
			Direction side = hitResult.getDirection();
			if (canUseAoE(player, stack)) {
				AoEHelper.doAoEBlockBreaking(player, stack, pos, side, getAoERange(stack));
			}
		}
	}
	
	/**
	 * Called when breaking a block to check if the stack can use it's AoE ability
	 * Return false if AoE ability disabled / player can't pay energy for AoE mining, ...
	 *
	 * @param stack the stack blocks get broken with
	 * @return true to do AoE mining, false to skip AoE mining
	 */
	boolean canUseAoE(Player player, ItemStack stack);
	
	/**
	 * The range this tool breaks blocks via AoE
	 *
	 * @param stack the AoEBreakingTool stack
	 * @return max square radius of block breaking
	 */
	default int getAoERange(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.AOE, 0);
	}
	
}

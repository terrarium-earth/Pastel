package de.dafuqs.spectrum.blocks.lava_sponge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class WetLavaSpongeItem extends BlockItem {
	
	public WetLavaSpongeItem(Block block, Item.Properties itemSettings) {
		super(block, itemSettings);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world != null && entity != null) {
			// play fire sound, set player and surroundings on fire
			if (world.isClientSide) {
				RandomSource random = world.getRandom();
				if (random.nextInt(50) == 0) {
					entity.playSound(SoundEvents.FIRE_EXTINGUISH, 0.4F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.2F);
				}
			} else {
				int r = world.getRandom().nextInt(120);
				if (r < 2) {
					entity.setRemainingFireTicks(25);
				} else if (r < 3) {
					if (world.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
						RandomSource random = world.getRandom();
						int xOffset = 3 - random.nextInt(7);
						int yOffset = 1 - random.nextInt(3);
						int zOffset = 3 - random.nextInt(7);
						
						BlockPos targetPos = BlockPos.containing(entity.position()).offset(xOffset, yOffset, zOffset);
						if (BaseFireBlock.canBePlacedAt(world, targetPos, Direction.UP)) {
							world.setBlockAndUpdate(targetPos, BaseFireBlock.getState(world, targetPos));
						}
					}
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.spectrum.wet_lava_sponge.tooltip"));
	}
	
}

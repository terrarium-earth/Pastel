package de.dafuqs.spectrum.items.food;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class DrinkItem extends Item {
	
	protected @Nullable Text tooltip;
	
	public DrinkItem(Settings settings) {
		super(settings);
	}
	
	public DrinkItem(Settings settings, String tooltip) {
		super(settings);
		this.tooltip = Text.translatable(tooltip).formatted(Formatting.GRAY);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		ItemStack itemStack = super.finishUsing(stack, world, user);
		
		if (user instanceof PlayerEntity player) {
			if (!player.getAbilities().creativeMode) {
				if (stack.isEmpty()) {
					return new ItemStack(Items.GLASS_BOTTLE);
				}
				player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		
		return itemStack;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
	
	public SoundEvent getEatSound() {
		return SoundEvents.ENTITY_GENERIC_DRINK;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		if (this.tooltip != null) {
			tooltip.add(this.tooltip);
		}
	}
	
}

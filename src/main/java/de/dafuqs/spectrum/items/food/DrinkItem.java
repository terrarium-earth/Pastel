package de.dafuqs.spectrum.items.food;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrinkItem extends Item {
	
	protected @Nullable Component tooltip;
	
	public DrinkItem(Properties settings) {
		super(settings);
	}
	
	public DrinkItem(Properties settings, String tooltip) {
		super(settings);
		this.tooltip = Component.translatable(tooltip).withStyle(ChatFormatting.GRAY);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		ItemStack itemStack = super.finishUsingItem(stack, world, user);
		
		if (user instanceof Player player) {
			if (!player.getAbilities().instabuild) {
				if (stack.isEmpty()) {
					return new ItemStack(Items.GLASS_BOTTLE);
				}
				player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		
		return itemStack;
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.DRINK;
	}
	
	public SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_DRINK;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		if (this.tooltip != null) {
			tooltip.add(this.tooltip);
		}
	}
	
}

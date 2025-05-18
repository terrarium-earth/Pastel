package de.dafuqs.spectrum.items;

import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MysteriousLocketItem extends Item {
	
	public MysteriousLocketItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		if (!world.isClientSide) {
			ItemStack handStack = user.getItemInHand(hand);
			if (handStack.has(SpectrumDataComponentTypes.SOCKETED)) {
				handStack.shrink(1);
				user.getInventory().placeItemBackInInventory(SpectrumItems.MYSTERIOUS_COMPASS.getDefaultInstance());
				world.playSound(null, user.getX(), user.getY(), user.getZ(), SpectrumSoundEvents.UNLOCK, SoundSource.NEUTRAL, 1.0F, 1.0F);
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.spectrum.mysterious_locket.tooltip").withStyle(ChatFormatting.GRAY));
		if (stack.has(SpectrumDataComponentTypes.SOCKETED)) {
			tooltip.add(Component.translatable("item.spectrum.mysterious_locket.tooltip_socketed").withStyle(ChatFormatting.GRAY));
		} else {
			tooltip.add(Component.translatable("item.spectrum.mysterious_locket.tooltip_empty").withStyle(ChatFormatting.GRAY));
		}
	}
	
}

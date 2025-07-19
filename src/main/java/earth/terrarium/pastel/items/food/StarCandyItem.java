package earth.terrarium.pastel.items.food;

import earth.terrarium.pastel.items.trinkets.WhispyCircletItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class StarCandyItem extends Item {
	
	public StarCandyItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		ItemStack itemStack = super.finishUsingItem(stack, world, user);
		if (!world.isClientSide) {
			WhispyCircletItem.removeSingleStatusEffect(user, MobEffectCategory.HARMFUL);
		}
		return itemStack;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.star_candy.tooltip").withStyle(ChatFormatting.GRAY));
	}
	
}

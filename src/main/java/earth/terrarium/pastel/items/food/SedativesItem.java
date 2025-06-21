package earth.terrarium.pastel.items.food;

import earth.terrarium.pastel.items.ItemWithTooltip;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCures;

public class SedativesItem extends ItemWithTooltip {
	
	public SedativesItem(Properties settings, String tooltip) {
		super(settings, tooltip);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
		if (user instanceof ServerPlayer serverplayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
			serverplayer.awardStat(Stats.ITEM_USED.get(this));
		}

		if (!level.isClientSide) {
			user.removeEffectsCuredBy(PastelStatusEffects.Cures.SEDATIVES);
		}

		if (user instanceof Player player) {
			return ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET), false);
		} else {
			stack.consume(1, user);
			return stack;
		}
	}
	
}

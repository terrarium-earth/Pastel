package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.entity.PlayerEntityAccessor;
import earth.terrarium.pastel.compat.gofish.GoFishCompat;
import earth.terrarium.pastel.helpers.Ench;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;

import java.util.List;

public abstract class PastelFishingRodItem extends FishingRodItem {

	public PastelFishingRodItem(Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);

		PlayerEntityAccessor playerEntityAccessor = ((PlayerEntityAccessor) user);
		if (playerEntityAccessor.getSpectrumBobber() != null) {
			if (!world.isClientSide) {
				int damage = playerEntityAccessor.getSpectrumBobber().use(itemStack);
				itemStack.hurtAndBreak(damage, user, LivingEntity.getSlotForHand(hand));
			}

			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
			user.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
		} else {
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
			if (world instanceof ServerLevel serverWorld) {
				var drm = world.registryAccess();
				int luckBonus = EnchantmentHelper.getFishingLuckBonus(serverWorld, itemStack, user);
				int waitTimeReductionTicks = (int)(EnchantmentHelper.getFishingTimeReduction(serverWorld, itemStack, user) * 20.0F);
				int exuberanceLevel = Ench.getLevel(drm, PastelEnchantments.EXUBERANCE, itemStack);
				int bigCatchLevel = Ench.getLevel(drm, PastelEnchantments.BIG_CATCH, itemStack);
				int serendipityReelLevel = Ench.getLevel(drm, PastelEnchantments.SERENDIPITY_REEL, itemStack);
				boolean inventoryInsertion = Ench.hasEnchantment(drm, PastelEnchantments.INVENTORY_INSERTION, itemStack);
				boolean shouldSmeltDrops = shouldSmeltDrops(itemStack);
				spawnBobber(user, world, luckBonus, waitTimeReductionTicks, exuberanceLevel, bigCatchLevel, serendipityReelLevel, inventoryInsertion, shouldSmeltDrops);
			}

			user.awardStat(Stats.ITEM_USED.get(this));
			user.gameEvent(GameEvent.ITEM_INTERACT_START);
		}

		return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
	}

	public abstract void spawnBobber(Player user, Level world, int luckOfTheSeaLevel, int lureLevel, int exuberanceLevel, int bigCatchLevel, int serendipityReelLevel, boolean inventoryInsertion, boolean shouldSmeltDrops);

	public boolean canFishIn(FluidState fluidState) {
		return fluidState.is(FluidTags.WATER);
	}

	public boolean shouldSmeltDrops(ItemStack itemStack) {
		return EnchantmentHelper.hasTag(itemStack, PastelEnchantmentTags.SMELTS_MORE_LOOT) || GoFishCompat.hasDeepfry(itemStack);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.pastel_fishing_rods.tooltip").withStyle(ChatFormatting.GRAY));
	}

}

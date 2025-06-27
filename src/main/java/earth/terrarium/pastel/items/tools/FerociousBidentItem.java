package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.render.SlotBackgroundEffectProvider;
import earth.terrarium.pastel.helpers.Ench;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// riptide w/o weather requirement; damages enemies on touch; iframes?
public class FerociousBidentItem extends MalachiteBidentItem implements SlotBackgroundEffectProvider, InkPowered {
	
	public static final InkCost RIPTIDE_COST = new InkCost(InkColors.WHITE, 10);
	public static final int BUILTIN_RIPTIDE_LEVEL = 1;
	
	public FerociousBidentItem(Item.Properties settings, double attackSpeed, double damage, float armorPierce, float protPierce) {
		super(settings, attackSpeed, damage, armorPierce, protPierce);
	}
	
	@Override
	public List<InkColor> getUsedColors() {
		return List.of(RIPTIDE_COST.color());
	}
	
	@Override
	public int getRiptideLevel(HolderLookup.Provider lookup, ItemStack stack) {
		return Math.max(Ench.getLevel(lookup, Enchantments.RIPTIDE, stack), BUILTIN_RIPTIDE_LEVEL);
	}

	@Override
	public boolean canStartRiptide(Player player, ItemStack stack) {
		return !isDisabled(stack) && (super.canStartRiptide(player, stack) || InkPowered.tryDrainEnergy(player, RIPTIDE_COST));
	}
	
	@Override
	public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.onUseTick(world, user, stack, remainingUseTicks);
		if (user.isAutoSpinAttack() && user instanceof Player player) {
			
			int useTime = this.getUseDuration(stack, user) - remainingUseTicks;
			if (useTime % 10 == 0) {
				if (InkPowered.tryDrainEnergy(player, RIPTIDE_COST)) {
					stack.hurtAndBreak(1, user, LivingEntity.getSlotForHand(user.getUsedItemHand()));
				} else {
					user.releaseUsingItem();
					return;
				}
			}
			
			yeetPlayer(player, getRiptideLevel(world.registryAccess(), stack) / 128F - 0.75F);
			player.startAutoSpinAttack(20, 12.0F, stack);
			
			for (LivingEntity entityAround : world.getEntities(EntityTypeTest.forClass(LivingEntity.class), player.getBoundingBox().inflate(2), LivingEntity::isAlive)) {
				if (entityAround != player) {
					entityAround.hurt(world.damageSources().playerAttack(player), 2);
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.ferocious_glass_crest_bident.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.ferocious_glass_crest_bident.tooltip2").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.ferocious_glass_crest_bident.tooltip3").withStyle(ChatFormatting.GRAY));
		addInkPoweredTooltip(tooltip);
	}
	
	@Override
	public boolean canBeDisabled() {
		return true;
	}
	
	@Override
	public SlotBackgroundEffectProvider.SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		var usable = InkPowered.hasAvailableInk(player, RIPTIDE_COST);
		return usable ? SlotBackgroundEffectProvider.SlotEffect.BORDER_FADE : SlotBackgroundEffectProvider.SlotEffect.NONE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		return InkColors.ORANGE_COLOR;
	}
	
	@Override
	public float getDefenseMultiplier(LivingEntity target, ItemStack stack) {
		return 0.66F;
	}
	
	@Override
	public float getProtReduction(LivingEntity target, ItemStack stack) {
		return 0.33F;
	}
}

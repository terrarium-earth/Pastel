package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.InkPoweredStatusEffectInstance;
import earth.terrarium.pastel.api.item.InkPoweredPotionFillable;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import java.util.List;

public class PotionPendantItem extends SpectrumTrinketItem implements InkPoweredPotionFillable {
	
	private final static int TRIGGER_EVERY_X_TICKS = 300;
	private final static int EFFECT_DURATION = TRIGGER_EVERY_X_TICKS + 220; // always keeps the effect active & prevents the 10 seconds of screen flashing when night vision runs out
	
	private final int maxEffectCount;
	private final int maxAmplifier;
	
	public PotionPendantItem(Properties settings, int maxEffectCount, int maxAmplifier, ResourceLocation unlockIdentifier) {
		super(settings, unlockIdentifier);
		this.maxEffectCount = maxEffectCount;
		this.maxAmplifier = maxAmplifier;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		appendPotionFillableTooltip(stack, tooltip, Component.translatable("item.pastel.potion_pendant.when_worn"), false, context.tickRate());
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return super.isFoil(stack) || stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).hasEffects();
	}
	
	@Override
	public int maxEffectCount() {
		return maxEffectCount;
	}
	
	@Override
	public int maxEffectAmplifier() {
		return maxAmplifier;
	}

	@Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
		Level world = slotContext.entity().level();
		super.onEquip(slotContext, prevStack, stack);
		if (!world.isClientSide && slotContext.entity() instanceof Player player) {
			grantEffects(stack, player);
		}
	}

	@Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
		LivingEntity entity = slotContext.entity();

		Level world = entity.level();
		super.curioTick(slotContext, stack);
		if (!world.isClientSide && entity.level().getGameTime() % TRIGGER_EVERY_X_TICKS == 0 && entity instanceof Player player) {
			grantEffects(stack, player);
		}
	}
	
	private void grantEffects(ItemStack stack, Player player) {
		for (InkPoweredStatusEffectInstance inkPoweredEffect : InkPoweredPotionFillable.getEffects(stack)) {
			if (InkPowered.tryDrainEnergy(player, inkPoweredEffect.getInkCost())) {
				MobEffectInstance effect = inkPoweredEffect.getStatusEffectInstance();
				player.addEffect(new MobEffectInstance(effect.getEffect(), EFFECT_DURATION, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), true));
			}
		}
	}
	
}

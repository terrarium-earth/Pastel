package de.dafuqs.spectrum.items.tools;

import de.dafuqs.revelationary.api.advancements.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.particle.effect.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.particle.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class NightfallsBladeItem extends ToolItem implements InkPoweredPotionFillable, SlotBackgroundEffectProvider {
	
	private static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/equipment/nightfalls_blade");
	
	public NightfallsBladeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, settings.attributeModifiers(AttributeModifiersComponent.builder()
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(SpectrumEntityAttributes.REACH_MODIFIER_ID, -1.5F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.build()));
	}

	@Override
	public int maxEffectCount() {
		return 1;
	}
	
	@Override
	public int maxEffectAmplifier() {
		return 2;
	}

	@Override
	public boolean isWeapon() {
		return true;
	}
	
	@Override
	public long adjustFinalCostFor(@NotNull InkPoweredStatusEffectInstance instance) {
		var mod = SpectrumStatusEffects.isStrongSleepEffect(instance) ? 1 : 0;
		return Math.round(Math.pow(instance.getInkCost().cost(), 1.75 + instance.getStatusEffectInstance().getAmplifier() + mod));
	}
	
	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if(target.isAlive() && attacker instanceof PlayerEntity player) {
			if (AdvancementHelper.hasAdvancement(player, UNLOCK_IDENTIFIER)) {
				List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
				for(InkPoweredStatusEffectInstance instance : effects) {
					if(InkPowered.tryDrainEnergy(player, instance.getInkCost().color(), instance.getInkCost().cost())) {
						World world = attacker.getWorld();
						if (world.isClient) {
							world.addParticle(new DynamicParticleEffect(ParticleTypes.EFFECT, 0.1F, SpectrumColorHelper.colorIntToVec(instance.getStatusEffectInstance().getEffectType().value().getColor()), 0.5F, 120, true, true),
									target.getParticleX(0.5D), target.getBodyY(0.5D), target.getParticleZ(0.5D),
									world.random.nextFloat() - 0.5, world.random.nextFloat() - 0.5, world.random.nextFloat() - 0.5
							);
						} else {
							target.addStatusEffect(instance.getStatusEffectInstance(), attacker);
						}
					}
				}
			}
		}
		return super.postHit(stack, target, attacker);
	}
	
	@Override
	public boolean hasGlint(ItemStack stack) {
		return super.hasGlint(stack) || !stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).hasEffects();
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		appendPotionFillableTooltip(stack, tooltip, Text.translatable("item.spectrum.nightfalls_blade.when_struck"), true, context.getUpdateTickRate());
	}
	
	@Override
	public SlotBackgroundEffectProvider.SlotEffect backgroundType(@Nullable PlayerEntity player, ItemStack stack) {
		List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
		if (effects.isEmpty()) {
			return SlotBackgroundEffectProvider.SlotEffect.NONE;
		}
		
		var effect = effects.getFirst();
		var usable = InkPowered.hasAvailableInk(player, new InkCost(effect.getInkCost().color(), adjustFinalCostFor(effect)));
		return usable ? SlotBackgroundEffectProvider.SlotEffect.BORDER_FADE : SlotEffect.BORDER;
	}
	
	@Override
	public int getBackgroundColor(@Nullable PlayerEntity player, ItemStack stack, float tickDelta) {
		List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
		if (effects.isEmpty())
			return 0x000000;
		
		return effects.getFirst().getColor();
	}
	
	@Override
	public float getEffectOpacity(@Nullable PlayerEntity player, ItemStack stack, float tickDelta) {
		List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
		if (effects.isEmpty())
			return 0F;
		
		var effect = effects.getFirst();
		if (InkPowered.hasAvailableInk(player, new InkCost(effect.getInkCost().color(), adjustFinalCostFor(effect))))
			return 1F;
		
		if (player == null)
			return 0F;
		
		var time = player.getWorld().getTime();
		return (float) (Math.sin((time + tickDelta) / 30F) * 0.3F + 0.3);
	}
}

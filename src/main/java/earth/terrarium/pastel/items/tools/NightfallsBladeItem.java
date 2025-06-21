package earth.terrarium.pastel.items.tools;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.InkPoweredStatusEffectInstance;
import earth.terrarium.pastel.api.item.InkPoweredPotionFillable;
import earth.terrarium.pastel.api.render.SlotBackgroundEffectProvider;
import earth.terrarium.pastel.helpers.ColorHelper;
import earth.terrarium.pastel.particle.effect.DynamicParticleEffect;
import earth.terrarium.pastel.registries.PastelEntityAttributes;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NightfallsBladeItem extends TieredItem implements InkPoweredPotionFillable, SlotBackgroundEffectProvider {
	
	private static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("unlocks/equipment/nightfalls_blade");
	
	public NightfallsBladeItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
		super(material, settings.attributes(ItemAttributeModifiers.builder()
				.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage + material.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(PastelEntityAttributes.REACH_MODIFIER_ID, -1.5F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
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
		var mod = PastelStatusEffects.isStrongSleepEffect(instance) ? 1 : 0;
		return Math.round(Math.pow(instance.getInkCost().cost(), 1.75 + instance.getStatusEffectInstance().getAmplifier() + mod));
	}
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if(target.isAlive() && attacker instanceof Player player) {
			if (AdvancementHelper.hasAdvancement(player, UNLOCK_IDENTIFIER)) {
				List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
				for(InkPoweredStatusEffectInstance instance : effects) {
					if(InkPowered.tryDrainEnergy(player, instance.getInkCost().color(), instance.getInkCost().cost())) {
						Level world = attacker.level();
						if (world.isClientSide) {
							world.addParticle(new DynamicParticleEffect(ParticleTypes.EFFECT, 0.1F, ColorHelper.colorIntToVec(instance.getStatusEffectInstance().getEffect().value().getColor()), 0.5F, 120, true, true),
									target.getRandomX(0.5D), target.getY(0.5D), target.getRandomZ(0.5D),
									world.random.nextFloat() - 0.5, world.random.nextFloat() - 0.5, world.random.nextFloat() - 0.5
							);
						} else {
							target.addEffect(instance.getStatusEffectInstance(), attacker);
						}
					}
				}
			}
		}
		return super.hurtEnemy(stack, target, attacker);
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return super.isFoil(stack) || !stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).hasEffects();
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		appendPotionFillableTooltip(stack, tooltip, Component.translatable("item.pastel.nightfalls_blade.when_struck"), true, context.tickRate());
	}
	
	@Override
	public SlotBackgroundEffectProvider.SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
		if (effects.isEmpty()) {
			return SlotBackgroundEffectProvider.SlotEffect.NONE;
		}
		
		var effect = effects.getFirst();
		var usable = InkPowered.hasAvailableInk(player, new InkCost(effect.getInkCost().color(), adjustFinalCostFor(effect)));
		return usable ? SlotBackgroundEffectProvider.SlotEffect.BORDER_FADE : SlotEffect.BORDER;
	}
	
	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
		if (effects.isEmpty())
			return 0x000000;
		
		return effects.getFirst().getColor();
	}
	
	@Override
	public float getEffectOpacity(@Nullable Player player, ItemStack stack, float tickDelta) {
		List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
		if (effects.isEmpty())
			return 0F;
		
		var effect = effects.getFirst();
		if (InkPowered.hasAvailableInk(player, new InkCost(effect.getInkCost().color(), adjustFinalCostFor(effect))))
			return 1F;
		
		if (player == null)
			return 0F;
		
		var time = player.level().getGameTime();
		return (float) (Math.sin((time + tickDelta) / 30F) * 0.3F + 0.3);
	}
}

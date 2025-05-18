package de.dafuqs.spectrum.items.armor;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.item.ArmorWithHitEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GemstoneArmorItem extends ArmorItem implements ArmorWithHitEffect {
	
	public GemstoneArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties settings) {
		super(material, type, settings);
	}
	
	@Override
	public void onHit(ItemStack itemStack, DamageSource source, LivingEntity targetEntity, float amount) {
		// While mostly useful against mobs, being able to trigger this effect for all kinds of damage
		// like fall damage seems like an awesome mechanic
		process(type, source, targetEntity);
		targetEntity.level().playSound(null, targetEntity.blockPosition(), SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
		targetEntity.level().playSound(null, targetEntity.blockPosition(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
		
		itemStack.hurtAndBreak(2, targetEntity, type.getSlot());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipType) {
		super.appendHoverText(stack, context, tooltip, tooltipType);
		addTooltip(tooltip, type);
	}
	
	private void process(@NotNull ArmorItem.Type type, DamageSource source, LivingEntity targetEntity) {
		switch (type) {
			case HELMET -> {
				if (source.getEntity() instanceof LivingEntity) {
					MobEffectInstance statusEffectInstance = new MobEffectInstance(MobEffects.WEAKNESS, 5 * 20, SpectrumCommon.CONFIG.GemstoneArmorWeaknessAmplifier);
					((LivingEntity) source.getEntity()).addEffect(statusEffectInstance);
					statusEffectInstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5 * 20, SpectrumCommon.CONFIG.GemstoneArmorSlownessAmplifier);
					((LivingEntity) source.getEntity()).addEffect(statusEffectInstance);
				}
			}
			case CHESTPLATE -> {
				MobEffectInstance statusEffectInstance = new MobEffectInstance(MobEffects.ABSORPTION, 5 * 20, SpectrumCommon.CONFIG.GemstoneArmorAbsorptionAmplifier);
				targetEntity.addEffect(statusEffectInstance);
				statusEffectInstance = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5 * 20, SpectrumCommon.CONFIG.GemstoneArmorResistanceAmplifier);
				targetEntity.addEffect(statusEffectInstance);
			}
			case LEGGINGS -> {
				MobEffectInstance statusEffectInstance = new MobEffectInstance(MobEffects.REGENERATION, 5 * 20, SpectrumCommon.CONFIG.GemstoneArmorRegenerationAmplifier);
				targetEntity.addEffect(statusEffectInstance);
			}
			case BOOTS -> {
				MobEffectInstance statusEffectInstance = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5 * 20, SpectrumCommon.CONFIG.GemstoneArmorSpeedAmplifier);
				targetEntity.addEffect(statusEffectInstance);
				statusEffectInstance = new MobEffectInstance(MobEffects.INVISIBILITY, 5 * 20, 0);
				targetEntity.addEffect(statusEffectInstance);
			}
		}
	}
	
	public void addTooltip(List<Component> tooltip, @NotNull ArmorItem.Type equipmentSlot) {
		switch (equipmentSlot) {
			case HELMET ->
					tooltip.add(Component.translatable("item.spectrum.fetchling_helmet.tooltip").withStyle(ChatFormatting.GRAY));
			case CHESTPLATE ->
					tooltip.add(Component.translatable("item.spectrum.ferocious_chestplate.tooltip").withStyle(ChatFormatting.GRAY));
			case LEGGINGS ->
					tooltip.add(Component.translatable("item.spectrum.sylph_leggings.tooltip").withStyle(ChatFormatting.GRAY));
			case BOOTS ->
					tooltip.add(Component.translatable("item.spectrum.oread_boots.tooltip").withStyle(ChatFormatting.GRAY));
		}
	}
	
}

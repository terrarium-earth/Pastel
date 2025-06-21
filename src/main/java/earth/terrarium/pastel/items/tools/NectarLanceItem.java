package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.render.SlotBackgroundEffectProvider;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import earth.terrarium.pastel.registries.PastelMobEffectTags;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.jetbrains.annotations.Nullable;

public class NectarLanceItem extends LightGreatswordItem implements SlotBackgroundEffectProvider {

	public NectarLanceItem(Tier material, int attackDamage, float attackSpeed, float crit, float reach, int barColor, Properties settings) {
		super(material, attackDamage, attackSpeed, crit, reach, barColor, settings);
	}

	@Override
	public float getBlockingMultiplier(DamageSource source, ItemStack stack, LivingEntity entity, int usedTime) {
		if (source.is(DamageTypeTags.IS_PROJECTILE)) {
			return 0;
		}
		if (canPerfectParry(stack, entity, usedTime)) {
			return 0.0F;
		}
		else if(canBluffParry(stack, entity, usedTime)) {
			return 0.1F;
		}
		else if (usedTime <= getMaxShieldingTime(entity, stack) / 2F) {
			return 0.25F;
		}
		return 0.6F;
	}

	@Override
	public float getLungeSpeed() {
		return 2F;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity user) {
		return 30;
	}

	@Override
	protected void applyLungeHitEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		DamageSource magicDamage = attacker.damageSources().magic();
		if (!(attacker.level() instanceof ServerLevel serverWorld))
			return;
		float base = EnchantmentHelper.modifyDamage(serverWorld, stack, target, magicDamage, (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
		if (target.hasEffect(MobEffects.POISON)) {
			var effect = target.getEffect(MobEffects.POISON);
			if (target.removeEffect(MobEffects.POISON)) {
				assert effect != null;
				applyDoTProc(magicDamage, base + 5F,0.8F, target, effect, false, true);
			}
		}
		else if (target.hasEffect(PastelStatusEffects.DEADLY_POISON)) {
			var effect = target.getEffect(PastelStatusEffects.DEADLY_POISON);
			if (target.removeEffect(PastelStatusEffects.DEADLY_POISON)) {
				assert effect != null;
				applyDoTProc(magicDamage, base + 10F,1.0F, target, effect, true, true);
			}
		}
		else if (target.hasEffect(MobEffects.WITHER)) {
			var effect = target.getEffect(MobEffects.WITHER);
			if (target.removeEffect(MobEffects.WITHER)) {
				assert effect != null;
				applyDoTProc(magicDamage, base + 5F,0.1F, target, effect, true, false);
			}
		}
		else if (PastelMobEffectTags.hasEffectWithTag(target, PastelMobEffectTags.SOPORIFIC)) {
			var scaling = SleepStatusEffect.getSleepScaling(target);
			if (scaling > 0) {
				target.hurt(PastelDamageTypes.sleep(target.level(),target), scaling);
				target.playSound(PastelSoundEvents.DEEP_CRYSTAL_RING, 0.5F, 0.8F + target.getRandom().nextFloat() * 0.4F);
			}
		}
		else {
			var stolenEffect = target.getActiveEffects()
					.stream()
					.filter(instance -> instance.getEffect().value().isBeneficial())
					.filter(instance -> !instance.isInfiniteDuration())
					.filter(instance -> !((MobEffectInstanceInjector) instance).isIncurable())
					.findFirst();

			if (stolenEffect.isEmpty() || !target.removeEffect(stolenEffect.get().getEffect()))
				return;

			var effect = stolenEffect.get();
			var duration = effect.getDuration();
			var amp = effect.getAmplifier();
			var takenDuration = (int) Math.ceil(duration / Math.log10(duration + 1));
			var takenAmp = 0;

			if (attacker.hasEffect(effect.getEffect()))
				takenAmp += attacker.getEffect(effect.getEffect()).getAmplifier();

			attacker.addEffect(new MobEffectInstance(effect.getEffect(), takenDuration, takenAmp));

			if (amp > 0)
				target.addEffect(new MobEffectInstance(effect.getEffect(), duration, amp - 1, effect.isAmbient(), effect.isVisible(), effect.showIcon()));

			target.playSound(PastelSoundEvents.SOFT_HUM, 0.275F, 0.8F + target.getRandom().nextFloat() * 0.4F);
		}
	}

	public static boolean sleepCrits(Player player, Entity target) {
		if (!(target instanceof LivingEntity livingEntity))
			return false;

		if (!player.getMainHandItem().is(PastelItems.NECTAR_LANCE.get()))
			return false;

		if (livingEntity.isSleeping())
			return true;

		var scaling = SleepStatusEffect.getSleepScaling(livingEntity);
		return scaling > 0 && livingEntity.getRandom().nextFloat() <= scaling / 3F;
	}

	private static void applyDoTProc(DamageSource type, float baseDamage, float damageScaling, LivingEntity target, MobEffectInstance effect, boolean canKill, boolean logScaling) {
		var duration = effect.getDuration() / 20F;
		var level = effect.getAmplifier() + 1;
		var scaling = level * damageScaling;
		var damage = scaling;

		if (logScaling) {
			damage = (float) (Math.log(duration) / Math.log(2) * scaling);

		}

		damage += baseDamage;
		if (!canKill) {
			damage = Math.min(target.getHealth() - 1, damage);
		}

		target.hurt(type, damage);
		target.playSound(PastelSoundEvents.DEEP_CRYSTAL_RING, 1.25F, 0.9F + target.getRandom().nextFloat() * 0.2F);
	}


	@Override
	public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		return SlotEffect.BORDER_FADE;
	}

	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		return InkColors.PURPLE_COLOR;
	}
}

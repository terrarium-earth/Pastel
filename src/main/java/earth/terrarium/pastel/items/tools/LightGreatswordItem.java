package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.SplitDamageItem;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.helpers.SpectrumEnchantmentHelper;
import earth.terrarium.pastel.registries.SpectrumDamageTypes;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LightGreatswordItem extends ParryingSwordItem implements SplitDamageItem {

	private final int barColor;

	public LightGreatswordItem(Tier material, int attackDamage, float attackSpeed, float crit, float reach, int barColor, Properties settings) {
		super(material, attackDamage, attackSpeed, crit, reach, settings);
		this.barColor = barColor;
	}

	@Override
	public float getBlockingMultiplier(DamageSource source, ItemStack stack, LivingEntity entity, int usedTime) {
		if (source.is(DamageTypeTags.IS_PROJECTILE))
			return 0;

		if (canPerfectParry(stack, entity, usedTime)) {
			return 0.05F;
		}
		else if(canBluffParry(stack, entity, usedTime)) {
			return 0.2F;
		}
		else if (usedTime <= getMaxShieldingTime(entity, stack) / 2F) {
			return 0.5F;
		}

		return 0.75F;
	}

	@Override
	public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
		super.releaseUsing(stack, world, user, remainingUseTicks);

		if (!(user instanceof Player player))
			return;

		var maxShieldTime = getMaxShieldingTime(user, stack);
		if (!player.onGround() && maxShieldTime - remainingUseTicks > 5) {

			var chargeDir = Vec3.directionFromRotation(player.getXRot(), player.getYRot());
			float chargeStrength = Math.min((float) (maxShieldTime - remainingUseTicks) / maxShieldTime + 0.2F, 1F);

			player.push(chargeDir.normalize().scale(getLungeSpeed() * chargeStrength));
			player.playSound(SpectrumSoundEvents.LUNGE, 2F, 0.8F + player.getRandom().nextFloat() * 0.2F);
			MiscPlayerData.get(player).initiateLungeState();
		}
	}

	public float getLungeSpeed() {
		return 1F;
	}

	@Override
	public int getBarColor() {
		return barColor;
	}

	protected void applyLungeHitEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.getType() == EntityType.ENDERMAN)
			return;

		var effect = target.isInvertedHealAndHarm() ? MobEffects.REGENERATION : MobEffects.POISON;
		int sharpness = SpectrumEnchantmentHelper.getLevel(target.level().registryAccess(), Enchantments.SHARPNESS, stack);
		target.addEffect(new MobEffectInstance(effect, 20 * (5 + sharpness), 1));
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (attacker instanceof Player player) {
			if (MiscPlayerData.get(player).isLunging()) {
				MiscPlayerData.get(player).endLunge();
				target.playSound(SpectrumSoundEvents.LUNGE_CRIT, 1F, 0.9F + target.getRandom().nextFloat() * 0.2F);
				applyLungeHitEffects(stack, target, attacker);
			}
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public DamageComposition getDamageComposition(LivingEntity attacker, LivingEntity target, ItemStack stack, float damage) {
		var composition = new DamageComposition();
		var source = composition.getPlayerOrEntity(attacker);

		if (attacker instanceof Player player && MiscPlayerData.get(player).isLunging()) {
			source = SpectrumDamageTypes.impaling(player.level(), player);
		}

		composition.add(source, damage);
		return composition;
	}
}

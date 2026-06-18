package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;

public class VerdigrisLashItem extends WhipItem {
    public VerdigrisLashItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, 0.5f, 16, 1, 5, 0.2f, properties); // 16 hits to fully charge up your fervor, seems reasonable; might need to tune it down actually because it can proc multiple times with big sweeps
    }

    // debuff set: pretty standard, 5-8 gives slowness 1, 8-12 adds poison 1, 12-16 bumps slow to 2 and adds somno
    // and the duration scales with fervor spent
    // also, since this has somno at high levels and the fox-o-nine-tails doesn't, you can. technically. whipstack?
    // it's not _good_ but like you can!
    @Override
    protected void applyDebuffs(LivingEntity attacker, LivingEntity target, int fervorExpended) {
        if (fervorExpended < 8) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, fervorExpended * 20, 0), attacker);
        } else if (fervorExpended < 12) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, fervorExpended * 20, 0), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.POISON, fervorExpended * 20, 0), attacker);
        } else {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, fervorExpended * 20, 1), attacker);
            target.addEffect(new MobEffectInstance(MobEffects.POISON, fervorExpended * 20, 0), attacker);
            target.addEffect(new MobEffectInstance(PastelMobEffects.SOMNOLENCE, fervorExpended * 20, 0), attacker);
        }
    }
}

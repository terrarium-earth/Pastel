package earth.terrarium.pastel.status_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class GravityStatusEffect extends MobEffect {

    protected final float gravityPerLevel;

    public GravityStatusEffect(MobEffectCategory statusEffectCategory, int color, float gravityPerLevel) {
        super(statusEffectCategory, color);
        this.gravityPerLevel = gravityPerLevel;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        applyGravityEffect(entity, gravityPerLevel * (amplifier + 1));
        return super.applyEffectTick(entity, amplifier);
    }

    public static void applyGravityEffect(Entity entity, double additionalYVelocity) {
        // don't affect creative/spectators/... players or immune boss mobs
        if (entity.isPushable() && !(entity).isSpectator()) {
            if (!(entity instanceof Player playerEntity && playerEntity.isCreative())) {
                entity.push(0, additionalYVelocity, 0);
                // maybe add fall distance, when not touching the ground?
            }
        }
    }

}

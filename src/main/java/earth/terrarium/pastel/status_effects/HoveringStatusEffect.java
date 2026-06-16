package earth.terrarium.pastel.status_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HoveringStatusEffect extends MobEffect {

    public boolean hadGravity = true;
    public HoveringStatusEffect(MobEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        super.onEffectAdded(livingEntity, amplifier);
        if(livingEntity.isNoGravity()) {
            this.hadGravity = false;
        }
        livingEntity.setNoGravity(true);
        var movement = livingEntity.getDeltaMovement();
        livingEntity.setDeltaMovement(movement.x(), 0, movement.z());
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        entity.setNoGravity(!hadGravity);
        return super.applyEffectTick(entity, amplifier);
    }

}

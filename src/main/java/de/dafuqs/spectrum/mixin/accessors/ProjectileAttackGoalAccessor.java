package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RangedAttackGoal.class)
public interface ProjectileAttackGoalAccessor {

    @Accessor("target")
    LivingEntity getProjectileAttackTarget();

}
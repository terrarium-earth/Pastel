package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Accessor
	DamageSource getLastDamageSource();
	
	@Accessor
	void setLastDamageSource(DamageSource damageSource);
	
}

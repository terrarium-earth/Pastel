package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Stack;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Accessor
	DamageSource getLastDamageSource();
	
	@Accessor
	void setLastDamageSource(DamageSource damageSource);

	@Accessor
	Stack<DamageContainer> getDamageContainers();

	@Invoker
	int callCalculateFallDamage(float fallDistance, float damageMultiplier);
}

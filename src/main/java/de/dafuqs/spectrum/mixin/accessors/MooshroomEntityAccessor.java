package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MushroomCow.class)
public interface MooshroomEntityAccessor {
	
	@Accessor
	SuspiciousStewEffects getStewEffects();
	
	@Accessor
	void setStewEffects(SuspiciousStewEffects stewEffects);
	
}

package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Mob.class)
public interface MobEntityAccessor {
	
	@Invoker
	float invokeGetEquipmentDropChance(EquipmentSlot slot);
	
}
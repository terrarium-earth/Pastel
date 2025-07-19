package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ThrownTrident.class)
public interface TridentEntityAccessor {
	
	@Accessor("ID_LOYALTY")
	static EntityDataAccessor<Byte> getLoyalty() {
		return null;
	}
	
	@Accessor("ID_FOIL")
	static EntityDataAccessor<Boolean> getEnchanted() {
		return null;
	}
	
	@Accessor("dealtDamage")
	void setDealtDamage(boolean dealtDamage);
	
}
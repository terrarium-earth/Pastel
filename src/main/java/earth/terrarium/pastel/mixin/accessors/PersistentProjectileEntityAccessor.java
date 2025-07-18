package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractArrow.class)
public interface PersistentProjectileEntityAccessor {

    @Accessor
    int getLife();

    @Accessor
    void setLife(int life);

}

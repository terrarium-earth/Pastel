package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(
    Explosion.class
)
public interface ExplosionAccessor {

    @Accessor
    float getRadius();

}

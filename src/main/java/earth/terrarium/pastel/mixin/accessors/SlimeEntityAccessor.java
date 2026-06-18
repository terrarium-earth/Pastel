package earth.terrarium.pastel.mixin.accessors;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(
    Slime.class
)
public interface SlimeEntityAccessor {

    @Invoker
    void invokeSetSize(int newSize, boolean heal);

    @Invoker
    ParticleOptions invokeGetParticleType();

    @Invoker
    SoundEvent invokeGetSquishSound();

    @Invoker
    float invokeGetSoundVolume();

}

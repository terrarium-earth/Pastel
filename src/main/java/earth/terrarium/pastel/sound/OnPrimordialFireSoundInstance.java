package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class OnPrimordialFireSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {

    private final Player player;

    private int fadeInTicks;

    public OnPrimordialFireSoundInstance(Player player) {
        super(PastelSounds.PRIMORDIAL_FIRE_DOT, SoundSource.PLAYERS, player.getRandom());
        this.looping = true;
        this.delay = 0;
        this.volume = 0.05F;
        this.player = player;
        this.relative = true;
    }

    @Override
    public void tick() {
        if (player != null) {
            this.pitch = (float) (1 + Math.sin(player.tickCount % 240000 / (Math.E * 100)) / 5);
        }
        this.volume = Mth.clampedLerp(0.05F, 0.92F, fadeInTicks / 20F);
        fadeInTicks++;
    }

    @Override
    public boolean isStopped() {
        return player == null || player.isRemoved() || !PrimordialFireData.isOnPrimordialFire(player);
    }
}

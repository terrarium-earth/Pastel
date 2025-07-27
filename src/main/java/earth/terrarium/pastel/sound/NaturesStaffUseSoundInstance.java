package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NaturesStaffUseSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {

    private final Player player;
    private boolean done;

    public NaturesStaffUseSoundInstance(Player player) {
        super(PastelSounds.NATURES_STAFF_USE, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
        this.player = player;
        this.x = player.getX();
        this.y = player.getY();
        this.z = player.getZ();
    }

    @Override
    public boolean isStopped() {
        return this.done;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public void tick() {
        if (volume < 1.3)
            volume += 0.05F;

        if (player == null || !player.isUsingItem() || !player.getUseItem()
                                                              .is(PastelItems.NATURES_STAFF.get())) {
            this.setDone();
        } else {
            this.x = ((float) this.player.getX());
            this.y = ((float) this.player.getY());
            this.z = ((float) this.player.getZ());
        }
    }

    protected final void setDone() {
        this.done = true;
        this.looping = false;
    }
}

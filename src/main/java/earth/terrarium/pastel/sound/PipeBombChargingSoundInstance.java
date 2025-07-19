package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@OnlyIn(Dist.CLIENT)
public class PipeBombChargingSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {

    private final Player player;
    private boolean done;

    public PipeBombChargingSoundInstance(Player player) {
        super(PastelSoundEvents.INCANDESCENT_CHARGE, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.looping = true;
        this.delay = 0;
        this.volume = 1F;
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
        if (player == null || player.getUseItemRemainingTicks() <= 0 || player.getTicksUsingItem() > 54 ||
            !player.getUseItem()
                   .is(PastelItems.PIPE_BOMB.get())) {
            this.setDone();
        } else {
            this.x = this.player.getX();
            this.y = this.player.getY();
            this.z = this.player.getZ();

            showParticles();
        }
    }

    private void showParticles() {
        Level world = player.getCommandSenderWorld();
        Vec3 pos = player.position();
        RandomSource random = world.random;

        for (int i = 0; i < 2; i++) {
            player.getCommandSenderWorld()
                  .addParticle(
                      PastelParticleTypes.PRIMORDIAL_FLAME,
                      pos.x,
                      pos.y + 1,
                      pos.z,
                      random.nextDouble() - 0.5D,
                      random.nextDouble() - 0.5D,
                      random.nextDouble() - 0.5D
                  );
        }
    }

    protected final void setDone() {
        this.done = true;
        this.looping = false;
    }
}

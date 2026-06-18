package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(
    Dist.CLIENT
)
public class EnderSpliceChargingSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {

    private final Player player;

    private boolean done;

    public EnderSpliceChargingSoundInstance(Player player) {
        super(PastelSounds.ENDER_SPLICE_CHARGES, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.looping = true;
        this.delay = 0;
        this.volume = 0.6F;
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
        if (player == null || player.getUseItemRemainingTicks() <= 0 || player.getTicksUsingItem() > 47 || !player
            .getUseItem()
            .is(PastelItems.ENDER_SPLICE.get())) {
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

        for (
            int i = 0;
            i < 10;
            i++
        ) {
            player
                .getCommandSenderWorld()
                .addParticle(
                    ParticleTypes.PORTAL,
                    pos.x,
                    pos.y + 1,
                    pos.z,
                    random.nextDouble() * 1.6D - 0.8D,
                    random.nextDouble() * 1.6D - 0.8D,
                    random.nextDouble() * 1.6D - 0.8D
                );
        }
    }

    protected final void setDone() {
        this.done = true;
        this.looping = false;
    }
}

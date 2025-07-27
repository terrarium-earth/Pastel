package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.entity.entity.MagicProjectileEntity;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class MagicProjectileSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {

    private final ResourceKey<Level> worldKey;
    private final MagicProjectileEntity projectile;
    private final int maxDurationTicks = 280;

    private int ticksPlayed = 0;
    private boolean done;
    private boolean playedExplosion;
    private float pitchMod;

    protected MagicProjectileSoundInstance(ResourceKey<Level> worldKey, MagicProjectileEntity projectile) {
        super(PastelSounds.INK_PROJECTILE_LAUNCH, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());

        this.worldKey = worldKey;
        this.projectile = projectile;

        this.attenuation = Attenuation.NONE;
        this.x = this.projectile.getX();
        this.y = this.projectile.getY();
        this.z = this.projectile.getZ();

        this.looping = false;
        this.delay = 0;
        this.volume = 1.0F;
        pitchMod = Support.varFloatCentered(random, 0.1F);
    }

    @OnlyIn(Dist.CLIENT)
    public static void startSoundInstance(MagicProjectileEntity projectile) {
        Minecraft client = Minecraft.getInstance();
        MagicProjectileSoundInstance newInstance = new MagicProjectileSoundInstance(
            client.level.dimension(), projectile);
        Minecraft.getInstance()
                 .getSoundManager()
                 .play(newInstance);
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
        Minecraft client = Minecraft.getInstance();
        this.ticksPlayed++;

        this.x = this.projectile.getX();
        this.y = this.projectile.getY();
        this.z = this.projectile.getZ();

        var proximity = 1F - (float) projectile.position().distanceTo(client.cameraEntity.position()) / 48F;
        volume =  Math.clamp(proximity, 0, 1);
        pitch = 1 - (1F - proximity) / 100;
        pitch *= pitchMod;

        if (ticksPlayed > maxDurationTicks
            || !Objects.equals(this.worldKey, Minecraft.getInstance().level.dimension())
            || projectile.isRemoved()) {

            this.setDone();
        }
    }

    protected final void setDone() {
        this.ticksPlayed = this.maxDurationTicks;
        this.done = true;
        this.looping = false;

        if (projectile.isRemoved() && !playedExplosion) {
            projectile.spawnImpactParticles();
            playedExplosion = true;
        }
    }

}

package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.registries.PastelBiomes;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

public class BiomeSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {

    @Nullable
    public static BiomeSoundInstance WIND_HIGH = null;
    @Nullable
    public static BiomeSoundInstance WIND_LOW = null;
    @Nullable
    public static BiomeSoundInstance SHOWER = null;
    @Nullable
    public static BiomeSoundInstance LAMENTS = null;
    private static boolean clear = true;
    private final Minecraft client = Minecraft.getInstance();
    private static final int MAX_DURATION = 80;
    private final ResourceKey<Biome> biome;
    private final float volumeMod;
    private int biomeTicks = 1;
    private final boolean deepPitch;
    private boolean finished;

    protected BiomeSoundInstance(ResourceKey<Biome> biome, SoundEvent sound, float volumeMod, boolean altMod) {
        super(sound, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        looping = true;
        delay = 0;
        this.biome = biome;
        this.volumeMod = volumeMod;
        this.deepPitch = altMod;
        this.relative = false;

        updateVolumeAndPitch();
    }

    @Override
    public void tick() {
        var camera = client.getCameraEntity();
        if (camera == null) {
            finished = true;
            return;
        }

        var world = camera.level();

        if (world.getBiome(camera.blockPosition())
                 .is(biome) && biomeTicks < MAX_DURATION) {
            biomeTicks++;
        } else if (biomeTicks > 0) {
            biomeTicks--;
        }

        updateVolumeAndPitch();
        updatePosition(camera);
    }

    private void updatePosition(Entity camera) {
        var data = WorldAttenuation.getData();
        this.x = camera.getX() + data.x() * 3.5;
        this.y = camera.getY() + 1 + data.y() * 2;
        this.z = camera.getZ() + data.z() * 3.5;
    }

    private void updateVolumeAndPitch() {
        var data = WorldAttenuation.getData();
        float volMod = data.volume();
        float pitchMod = data.pitch();

        if (deepPitch) {
            volMod = volMod * 0.75F + 0.05F;
            pitchMod = pitchMod * 0.9F + 0.1F;
        } else {
            volMod = Math.max(0.02F, volMod);
            pitchMod = Math.max(0.4F, pitchMod * 1.1F);
        }

        volMod = Math.clamp(volMod * 2, 0, 1);

        this.volume = Math.max(0, ((float) biomeTicks) / MAX_DURATION) * volumeMod * volMod;

        this.pitch = pitchMod;
    }

    @Override
    public boolean isStopped() {
        return biomeTicks == 0 || finished;
    }

    public static void update(Holder<Biome> biome) {
        var client = Minecraft.getInstance();
        clear = false;

        if (WIND_HIGH != null && WIND_HIGH.isStopped()) {
            WIND_HIGH = null;
        }

        if (WIND_LOW != null && WIND_LOW.isStopped()) {
            WIND_LOW = null;
        }

        if (SHOWER != null && SHOWER.isStopped()) {
            SHOWER = null;
        }

        if (LAMENTS != null && LAMENTS.isStopped()) {
            LAMENTS = null;
        }

        if (biome.is(PastelBiomes.HOWLING_SPIRES)) {
            if (WIND_HIGH == null) {
                WIND_HIGH = new BiomeSoundInstance(
                    PastelBiomes.HOWLING_SPIRES, PastelSoundEvents.HOWLING_WIND_HIGH, 0.45F, false);
                client.getSoundManager()
                      .play(WIND_HIGH);
            }

            if (WIND_LOW == null) {
                WIND_LOW = new BiomeSoundInstance(
                    PastelBiomes.HOWLING_SPIRES, PastelSoundEvents.HOWLING_WIND_LOW, 1.3F, true);
                client.getSoundManager()
                      .play(WIND_LOW);
            }
        } else if (biome.is(PastelBiomes.DEEP_DRIPSTONE_CAVES)) {
            if (SHOWER == null) {
                SHOWER = new BiomeSoundInstance(
                    PastelBiomes.DEEP_DRIPSTONE_CAVES, PastelSoundEvents.SHOWER, 0.425F, false);
                client.getSoundManager()
                      .play(SHOWER);
            }
        } else if (biome.is(PastelBiomes.DRAGONROT_SWAMP)) {
            if (LAMENTS == null) {
                LAMENTS = new BiomeSoundInstance(PastelBiomes.DRAGONROT_SWAMP, PastelSoundEvents.LAMENTS, 1F, true);
                client.getSoundManager()
                      .play(LAMENTS);
            }
            if (SHOWER == null) {
                SHOWER = new BiomeSoundInstance(PastelBiomes.DRAGONROT_SWAMP, PastelSoundEvents.SHOWER, 1F, false);
                client.getSoundManager()
                      .play(SHOWER);
            }
        }
    }

    public static void clear() {
        if (clear)
            return;

        if (WIND_HIGH != null) {
            WIND_HIGH.finished = true;
            WIND_HIGH = null;
        }

        if (WIND_LOW != null) {
            WIND_LOW.finished = true;
            WIND_LOW = null;
        }

        if (SHOWER != null) {
            SHOWER.finished = true;
            SHOWER = null;
        }

        if (LAMENTS != null) {
            LAMENTS.finished = true;
            LAMENTS = null;
        }

        clear = true;
    }
}

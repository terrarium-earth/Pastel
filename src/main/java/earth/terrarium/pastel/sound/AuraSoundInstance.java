package earth.terrarium.pastel.sound;

import com.google.common.collect.HashMultimap;
import earth.terrarium.pastel.deeper_down.InterpMemory;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.render.ParticleHelper;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class AuraSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {

    private static final Map<ResourceKey<Level>, HashMultimap<AuraSoundInstance, BlockPos>> LINKS = new HashMap<>();

    private static final float MIN_VOLUME = 0.005F;

    private final Level level;
    private final AuraData data;
    private final InterpMemory<Float> size = new InterpMemory<>();
    private Vec3 posMemory;

    private boolean discarded;

    private AuraSoundInstance(AuraData data, Level level) {
        super(data.sound(), SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
        this.volume = MIN_VOLUME;
        this.looping = true;
        this.relative = true;
        this.level = level;
        this.data = data;
    }

    @Override
    public void tick() {
        var client = Minecraft.getInstance();

        var loop = level.getGameTime() % 10;
        var delta = client.getTimer()
                          .getGameTimeDeltaPartialTick(true);
        var camera = client.cameraEntity;

        if (loop == 0)
            updateMembers();

        if (discarded)
            return;

        var scaling = Mth.clampedLerp(size.last(), size.current(), (loop + delta) / 10F);
        spawnParticles(scaling);

        if (camera == null) {
            discard();
            return;
        }

        if (data.pitchShift())
            pitchShift(camera);

        var proximity = proximity(camera);

        if (proximity < 0.03F) {
            discard();
            return;
        }

        volume = (float) Mth.clamp(
            scaling * proximity,
            0, 1 - MIN_VOLUME
        ) * data.volMult();
    }

    private void pitchShift(Entity camera) {
        var mod = Mth.clamp((Math.abs(camera.getEyeY() - posMemory.y) - 6F) / 196F, 0, 0.225F);
        if (camera.getEyeY() < posMemory.y)
            mod *= -1;
        pitch = (float) (mod + 1);
    }

    private void spawnParticles(float scaling) {
        if (scaling > MIN_VOLUME * 2) {
            float chance = scaling / 2F;
            ParticleHelper.playTriangulatedParticle(
                level, PastelParticleTypes.AZURE_AURA, Support.chanceRound(chance * 2.25, random), true,
                new Vec3(24, 8, 24), -4, true, posMemory, new Vec3(0, 0.04D + random.nextDouble() * 0.06, 0)
            );
            ParticleHelper.playTriangulatedParticle(
                level, PastelParticleTypes.AZURE_MOTE_SMALL, Support.chanceRound(chance * 2, random), false,
                new Vec3(16, 8, 16), -6, false, posMemory, Vec3.ZERO
            );
            ParticleHelper.playTriangulatedParticle(
                level, PastelParticleTypes.AZURE_MOTE, Support.chanceRound(chance * 2, random), true,
                new Vec3(16, 6, 16), -4, false, posMemory, Vec3.ZERO
            );
        }
    }

    private double proximity(Entity camera) {
        return Math.clamp(
            1 - (camera.position()
                       .distanceTo(posMemory) / data.maxDistance()), 0, 1
        );
    }

    private void updateMembers() {
        var auras = getLevelLinks(level);
        var aura = auras.get(this);

        if (aura.isEmpty()) {
            discarded = true;
            return;
        }

        var origin = new ArrayList<>(aura).get(random.nextInt(aura.size()));
        var checked = new ArrayList<BlockPos>();
        floodTest(origin, checked, false);

        for (BlockPos proposal : checked) {
            updateOwnership(proposal, auras, aura);
        }

        aura.removeIf(b -> !checked.contains(b));
        if (aura.size() < data.min()) {
            discard();
            return;
        }

        updateScaling(aura);
    }

    private void updateScaling(Collection<BlockPos> newBlocks) {
        size.accept(Math.max((float) (newBlocks.size() - data.min()) / data.scaling(), MIN_VOLUME));

        var x = 0.5;
        var y = 0.5;
        var z = 0.5;
        for (BlockPos pos : newBlocks) {
            x += pos.getX();
            y += pos.getY();
            z += pos.getZ();
        }

        posMemory = new Vec3(
            x / newBlocks.size(),
            y / newBlocks.size(),
            z / newBlocks.size()
        );
    }

    private void updateOwnership(
        BlockPos proposal, HashMultimap<AuraSoundInstance, BlockPos> auras, Set<BlockPos> aura) {
        var parent = getOwner(auras, proposal);

        if (parent.filter(a -> a == this)
                  .isPresent() && data.filter()
                                      .test(proposal, level))
            return;

        if (parent.isEmpty()) {
            aura.add(proposal);
            return;
        }


        aura.remove(proposal);
    }

    private void floodTest(BlockPos current, List<BlockPos> out, boolean sanitize) {
        BlockPos.betweenClosedStream(
                    current.getX() - 1, current.getY() - 1, current.getZ() - 1,
                    current.getX() + 1, current.getY() + 1, current.getZ() + 1
                )
                .map(BlockPos::immutable)
                .filter(b -> !out.contains(b))
                .filter(b -> !sanitize || getOwner(getLevelLinks(level), b).isEmpty())
                .filter(b -> data.filter()
                                 .test(b, level))
                .peek(out::add)
                .forEach(b -> floodTest(b, out, sanitize));
    }

    @Override
    public boolean isStopped() {
        return discarded;
    }

    private void discard() {
        getLevelLinks(level).removeAll(this);
        discarded = true;
    }

    public static void getOrCreateInstance(AuraData data, Level level, BlockPos pos) {
        var links = getLevelLinks(level);
        var check = getOwner(links, pos);

        if (check.isPresent()) {
            return;
        }

        var aura = new AuraSoundInstance(data, level);
        var proposed = new ArrayList<BlockPos>();

        aura.floodTest(pos, proposed, true);
        if (proposed.size() <= data.min())
            return;

        proposed.forEach(b -> links.put(aura, b));
        aura.updateScaling(proposed);
        Minecraft.getInstance()
                 .getSoundManager()
                 .play(aura);
    }

    private static @NotNull HashMultimap<AuraSoundInstance, BlockPos> getLevelLinks(Level level) {
        return LINKS.computeIfAbsent(level.dimension(), l -> HashMultimap.create());
    }

    private static Optional<AuraSoundInstance> getOwner(
        HashMultimap<AuraSoundInstance, BlockPos> levelAuras, BlockPos pos) {
        for (Map.Entry<AuraSoundInstance, BlockPos> entry : levelAuras.entries()) {
            if (entry.getValue()
                     .equals(pos))
                return Optional.ofNullable(entry.getKey());
        }

        return Optional.empty();
    }

    public static void clear() {
        for (Map.Entry<ResourceKey<Level>, HashMultimap<AuraSoundInstance, BlockPos>> entry : LINKS.entrySet()) {
            for (AuraSoundInstance aura : entry.getValue()
                                               .keySet()) {
                aura.discarded = true;
            }
        }
        LINKS.clear();
    }
}

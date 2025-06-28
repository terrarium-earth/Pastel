package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.helpers.ParticleHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@OnlyIn(Dist.CLIENT)
public class BlockAuraSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
	
	public static final List<BlockAuraSoundInstance> INSTANCES = new ArrayList<>();
	private static final int MAX_DISTANCE = 48;
	private static final int SPACING = 16;
	private static final float MIN_VOLUME = 0.01F;
	private static final float MAX_VOLUME = 1.0F;
	private static final float VOLUME_EASING_STEPS = 0.01F;
	
	private final static List<BlockPos> toRemove = new ArrayList<>();
	
	private int age = 0;
	private double absX, absY, absZ;
	private final Queue<BlockPos> sources = new LinkedList<>();
	private final Level world;
	private float volumeHold;
	
	private BlockAuraSoundInstance(SoundEvent sound, Level world, BlockPos source) {
		super(sound, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
		this.volume = MIN_VOLUME;
		this.volumeHold = MIN_VOLUME;
		this.looping = true;
		this.delay = 0;
		this.relative = true;
		this.world = world;
		this.sources.add(source);
		
		updatePositionAndCount();
	}
	
	@Override
	public void tick() {
		age++;
		if (age % 100 == 0) {
			sources.poll();
		}
		if (age % 10 == 0) {
			updatePositionAndCount();
		}
		
		float targetVolume = (float) Mth.clamp((sources.size() * 0.05 - 0.5)
				, MIN_VOLUME, MAX_VOLUME);
		
		if (this.volumeHold < targetVolume) {
			this.volumeHold += VOLUME_EASING_STEPS;
		} else if (this.volumeHold > targetVolume) {
			this.volumeHold -= VOLUME_EASING_STEPS;
		}
		
		var distance = Mth.clampedLerp(1, 0, Math.sqrt(Minecraft.getInstance().getCameraEntity().distanceToSqr(absX, absY, absZ)) / MAX_DISTANCE);
		this.volume = (float) (volumeHold * distance * 0.8);
		
		double cameraEntityEyeY = Minecraft.getInstance().getCameraEntity().getEyeY();
		var pitchMod = Mth.clamp((Math.abs(cameraEntityEyeY - this.absY) - 4F) / 196F, 0, 0.225F);
		if (cameraEntityEyeY < this.absY) {
			pitchMod *= -1;
		}
		this.pitch = (float) (1 + pitchMod);
		
		if (volumeHold > 0.25) {
			Vec3 pos = new Vec3(this.absX, this.absY, this.absZ);
			float chance = volumeHold / 2F;
			ParticleHelper.playTriangulatedParticle(world, PastelParticleTypes.AZURE_AURA, Support.chanceRound(chance * 3, random), true, new Vec3(24, 8, 24), -4, true, pos, new Vec3(0, 0.04D + random.nextDouble() * 0.06, 0));
			ParticleHelper.playTriangulatedParticle(world, PastelParticleTypes.AZURE_MOTE_SMALL, Support.chanceRound(chance, random), false, new Vec3(16, 8, 16), -6, false, pos, Vec3.ZERO);
			ParticleHelper.playTriangulatedParticle(world, PastelParticleTypes.AZURE_MOTE, Support.chanceRound(chance, random), true, new Vec3(16, 6, 16), -4, false, pos, Vec3.ZERO);
		}
	}
	
	private void updatePositionAndCount() {
		int x = 0;
		int y = 0;
		int z = 0;
		for (BlockPos source : sources) {
			if (!world.hasChunkAt(source) || !world.getBlockState(source).is(PastelBlockTags.AZURITE_ORES)) { // tag is hardcoded for now. But should we have more blocks like that, we can easily split it
				toRemove.add(source);
			} else {
				x += source.getX();
				y += source.getY();
				z += source.getZ();
			}
		}
		for (BlockPos source : toRemove) {
			sources.remove(source);
		}
		toRemove.clear();
		
		int count = sources.size();
		if (count > 0) {
			this.absX = (double) x / count;
			this.absY = (double) y / count;
			this.absZ = (double) z / count;
		}
	}
	
	@Override
	public boolean isStopped() {
		boolean done;
		
		if (volume <= 0) {
			done = true;
		} else {
			Entity cameraEntity = Minecraft.getInstance().getCameraEntity();
			done = cameraEntity == null || cameraEntity.position().distanceToSqr(absX, absY, absZ) > MAX_DISTANCE * MAX_DISTANCE;
		}
		
		if (done) {
			INSTANCES.remove(this);
		}
		return done;
	}
	
	public static void addToExistingInstanceOrCreateNewOne(Level world, BlockPos pos) {
		double nearestDistance = Double.MAX_VALUE;
		@Nullable BlockAuraSoundInstance nearest = null;
		for (BlockAuraSoundInstance instance : INSTANCES) {
			double squaredDistance = pos.distToLowCornerSqr(instance.absX, instance.absY, instance.absZ);
			if (squaredDistance < nearestDistance) {
				nearestDistance = squaredDistance;
				nearest = instance;
			}
		}
		
		if (nearestDistance < SPACING * SPACING * SPACING) {
			if (nearest.sources.contains(pos)) {
				return;
			}
			nearest.sources.add(pos.immutable());
		} else {
			BlockAuraSoundInstance newInstance = new BlockAuraSoundInstance(PastelSoundEvents.OST_AZURE, world, pos.immutable());
			INSTANCES.add(newInstance);
			Minecraft.getInstance().getSoundManager().play(newInstance);
		}
	}
	
	public static void clear() {
		INSTANCES.forEach(i -> i.volume = Integer.MIN_VALUE);
		INSTANCES.clear();
	}
}
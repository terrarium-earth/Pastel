package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.items.trinkets.TakeOffBeltItem;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@OnlyIn(Dist.CLIENT)
public class TakeOffBeltSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
	
	private final long lastParticleTick;
	private boolean done;
	
	public TakeOffBeltSoundInstance() {
		super(SpectrumSoundEvents.AIR_LAUNCH_BELT_CHARGING, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
		Minecraft client = Minecraft.getInstance();
		Player player = client.player;
		this.looping = false;
		this.delay = 0;
		this.volume = 0.4F;
		this.lastParticleTick = player.level().getGameTime() + TakeOffBeltItem.CHARGE_TIME_TICKS * TakeOffBeltItem.MAX_CHARGES;
		this.x = player.getX();
		this.y = player.getY();
		this.z = player.getZ();
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void startSoundInstance() {
		TakeOffBeltSoundInstance soundInstance = new TakeOffBeltSoundInstance();
		if (!Minecraft.getInstance().getSoundManager().isActive(soundInstance)) {
			Minecraft.getInstance().getSoundManager().play(soundInstance);
		}
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
		Player player = client.player;
		if (player == null || !player.isShiftKeyDown() || !player.onGround()) {
			this.setDone();
		} else {
			this.x = ((float) player.getX());
			this.y = ((float) player.getY());
			this.z = ((float) player.getZ());
			
			if (player.level() != null && player.level().getGameTime() < lastParticleTick) {
				spawnParticles(player);
			} else {
				this.volume = 0.0F;
			}
		}
	}
	
	private void spawnParticles(Player player) {
		Level world = player.getCommandSenderWorld();
		RandomSource random = world.random;
		
		Vec3 pos = player.position();
		player.getCommandSenderWorld().addParticle(ColoredCraftingParticleEffect.LIGHT_BLUE,
				pos.x + random.nextDouble() * 0.8 - 0.4,
				pos.y,
				pos.z + random.nextDouble() * 0.8 - 0.4,
				0,
				random.nextDouble() * 0.5,
				0);
	}
	
	protected final void setDone() {
		this.done = true;
		this.looping = false;
	}
}

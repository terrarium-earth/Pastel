package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

@OnlyIn(Dist.CLIENT)
public class HintRevelationSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
	
	private final Player player;
	private boolean done;
	
	public HintRevelationSoundInstance(Player player) {
		super(SpectrumSoundEvents.TEXT_REVEALED, SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
		this.looping = true;
		this.delay = 0;
		this.volume = 1.0F;
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
		return false;
	}
	
	@Override
	public void tick() {
		if (this.player != null) {
			this.x = player.getX();
			this.y = player.getY();
			this.z = player.getZ();
		}
		
		if (player == null || !player.getMainHandItem().is(SpectrumItems.GUIDEBOOK.get())) {
			this.setDone();
		}
	}
	
	public final void setDone() {
		this.done = true;
		this.looping = false;
	}
}
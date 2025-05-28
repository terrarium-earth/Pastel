package earth.terrarium.pastel.blocks.spirit_sallow;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import net.minecraft.world.phys.Vec3;

public class WindStrength {
	
	private static final SimplexNoise SAMPLER = new SimplexNoise(new LegacyRandomSource(0));
	
	private static long cachedTick;
	
	public Vec3 cachedValue;
	
	public Vec3 getWindStrength(Level world) {
		long tick = world.getGameTime();
		if (tick != cachedTick) {
			float tickDelta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
			cachedValue = new Vec3(
					SAMPLER.getValue((tick + tickDelta) / 512D, 0, 0),
					SAMPLER.getValue(0, (tick + tickDelta) / 512D, 0),
					SAMPLER.getValue(0, 0, (tick + tickDelta) / 512D)
			);
			cachedTick = tick;
		}
		
		return cachedValue;
	}
	
}

package earth.terrarium.pastel.deeper_down;

import earth.terrarium.pastel.data_loaders.dimension.ColorGradingLoader;
import earth.terrarium.pastel.data_loaders.dimension.EnvDataLoader;
import earth.terrarium.pastel.registries.PastelBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;

public class Environmental {

	private static final InterpolationQueue<float[]> GRADING_QUEUE = new InterpolationQueue<>();
	private static final InterpolationQueue<float[]> ENV_QUEUE = new InterpolationQueue<>();

	private static final Minecraft client = Minecraft.getInstance();
	private static float delta;

	public static void tick(Entity entity) {
		var blending = client.options.biomeBlendRadius().get();
		delta = client.getTimer().getGameTimeDeltaPartialTick(false) + (client.level.getGameTime() % 3) / 3F;

		if (client.level.getGameTime() % 3 == 0)
			updateBiomeData(entity.blockPosition(), blending);

		if (GRADING_QUEUE.ready())
			ColorGrading.update(GRADING_QUEUE.last(), GRADING_QUEUE.current(), delta);
	}

	private static void updateBiomeData(BlockPos center, int blendingRadius) {
		if (blendingRadius <= 0) {
			var biome = findBiome(center);
			processAndAcceptEnv(center, EnvDataLoader.DATA.getOrDefault(biome, EnvironmentalData.NOOP).asArray());
			GRADING_QUEUE.accept(ColorGradingLoader.DATA.getOrDefault(biome, ColorGrading.DEFAULT).asArray());
			return;
		}

		var envStack = new InterpolationStack(4);
		var gradingStack = new InterpolationStack(5);

		var cursor = new Cursor3D(center.getX() - blendingRadius, center.getY() - blendingRadius,
						center.getZ() - blendingRadius, center.getX() + blendingRadius,
						center.getY() + blendingRadius, center.getZ() + blendingRadius);

		var test = new BlockPos.MutableBlockPos();
		while (cursor.advance()) {
			test.set(cursor.nextX(), cursor.nextY(), cursor.nextZ());

			var biome = findBiome(test);
			envStack.insert(EnvDataLoader.DATA.getOrDefault(biome, EnvironmentalData.NOOP).asArray());
			gradingStack.insert(ColorGradingLoader.DATA.getOrDefault(biome, ColorGrading.DEFAULT).asArray());
		}

		processAndAcceptEnv(center, envStack.get());
		GRADING_QUEUE.accept(gradingStack.get());
	}

	private static void processAndAcceptEnv(BlockPos ref, float[] env) {
		assert client.level != null;

		// Black langasts with depth darkening become actually just black
		if (findBiome(ref).equals(PastelBiomes.BLACK_LANGAST)) {
			ENV_QUEUE.accept(env);
			return;
		}

		var depthDarkening = 0F;
		var topSpace = client.level.getMaxBuildHeight() - ref.getY();
		depthDarkening += Mth.clampedLerp(0.334F, 0F, topSpace / 48F);

		var bottomSpace = ref.getY() - client.level.getMinBuildHeight();
		depthDarkening += Mth.clampedLerp(0.667F, 0F, bottomSpace / 64F);
		var depthFog = Mth.clampedLerp(0.337F, 1F, bottomSpace / 48F);

		env[0] = Math.clamp(env[0] + depthDarkening / 2F, 0, 1);
		env[1] = Math.clamp(env[1] - depthDarkening / 3F, 0.01F, 1);
		env[2] -= (1 - depthFog) * 2;
		env[3] *= depthFog;

		env[2] = Math.min(env[2], env[3]);

		ENV_QUEUE.accept(env);
	}

	public static EnvironmentalData getEnvData() {
		if (!ENV_QUEUE.ready())
			return EnvironmentalData.NOOP;

		var interpolated = new float[4];
		for (int i = 0; i < interpolated.length; i++) {
			interpolated[i] = Mth.lerp(delta, ENV_QUEUE.last()[i], ENV_QUEUE.current()[i]);
		}

		return EnvironmentalData.fromArray(interpolated);
	}
	
	public static float getNear(float original) {
		return getEnvData().fogNear() * original;
	}
	
	public static float getFar(float original) {
		return getEnvData().fogFar() * original;
	}

	private static ResourceKey<Biome> findBiome(BlockPos pos) {
        assert client.level!=null;
        return client.level.getBiome(pos).getKey();
	}

	private static class InterpolationStack {

		private float[] stack;
		private int insets;

		public InterpolationStack(int size) {
			stack = new float[size];
		}

		public void insert(float[] inset) {
			for (int i = 0; i < inset.length; i++) {
				stack[i] += inset[i];
			}

			insets++;
		}

		public void purge() {
			insets = 0;
			stack = new float[stack.length];
		}

		public float[] get() {
			var result = new float[stack.length];

			for (int i = 0; i < stack.length; i++) {
				result[i] = stack[i] / insets;
			}

			return result;
		}
	}

}

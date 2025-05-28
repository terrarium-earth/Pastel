package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.deeper_down.DeeperDownDimensionEffects;
import earth.terrarium.pastel.mixin.accessors.DimensionEffectsAccessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class SpectrumDimensions {
	
	public static final ResourceLocation EFFECTS_ID = SpectrumCommon.locate("deeper_down");
	public static final ResourceLocation DIMENSION_ID = SpectrumCommon.locate("deeper_down");
	public static final ResourceKey<Level> DIMENSION_KEY = ResourceKey.create(Registries.DIMENSION, DIMENSION_ID);
	
	public static void register() {}
	
	public static void registerClient() {
		DimensionEffectsAccessor.getEFFECTS().put(EFFECTS_ID, new DeeperDownDimensionEffects());
	}
	
}

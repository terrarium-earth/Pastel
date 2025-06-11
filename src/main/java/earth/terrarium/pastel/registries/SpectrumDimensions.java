package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.SpectrumCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class SpectrumDimensions {
	public static final ResourceLocation DIMENSION_ID = SpectrumCommon.locate("deeper_down");
	public static final ResourceKey<Level> DIMENSION_KEY = ResourceKey.create(Registries.DIMENSION, DIMENSION_ID);
	
	public static void register() {}
}

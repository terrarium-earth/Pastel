package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.deeper_down.DeeperDownDimensionEffects;
import de.dafuqs.spectrum.mixin.accessors.DimensionEffectsAccessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class SpectrumDimensions {
	
	public static final ResourceLocation EFFECTS_ID = SpectrumCommon.locate("deeper_down");
	public static final ResourceLocation DIMENSION_ID = SpectrumCommon.locate("deeper_down");
	public static final ResourceKey<Level> DIMENSION_KEY = ResourceKey.create(Registries.DIMENSION, DIMENSION_ID);
	
	public static void register() {
	
	}
	
	public static void registerClient() {
		DimensionEffectsAccessor.getEFFECTS().put(EFFECTS_ID, new DeeperDownDimensionEffects());
	}
	
}

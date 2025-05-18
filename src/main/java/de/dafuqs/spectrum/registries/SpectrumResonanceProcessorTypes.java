package de.dafuqs.spectrum.registries;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.interaction.ResonanceProcessor;
import de.dafuqs.spectrum.data_loaders.resonance_processors.DropSelfResonanceProcessor;
import de.dafuqs.spectrum.data_loaders.resonance_processors.ModifyDropsResonanceProcessor;
import net.minecraft.core.Registry;

public class SpectrumResonanceProcessorTypes {
	
	public static void register(String id, MapCodec<? extends ResonanceProcessor> target) {
		Registry.register(SpectrumRegistries.RESONANCE_PROCESSOR_TYPE, SpectrumCommon.locate(id), target);
	}
	
	public static void register() {
		register("drop_self", DropSelfResonanceProcessor.CODEC);
		register("modify_drops", ModifyDropsResonanceProcessor.CODEC);
	}
	
}

package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.data_loaders.resonance_processors.*;
import net.minecraft.registry.*;

public class SpectrumResonanceProcessorTypes {
	
	public static void register(String id, MapCodec<? extends ResonanceProcessor> target) {
		Registry.register(SpectrumRegistries.RESONANCE_PROCESSOR_TYPES, SpectrumCommon.locate(id), target);
	}
	
	public static void register() {
		register("drop_self", DropSelfResonanceProcessor.CODEC);
		register("modify_drops", ModifyDropsResonanceProcessor.CODEC);
	}
	
}

package de.dafuqs.spectrum.registries;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.data_loaders.resonance_processors.*;
import net.minecraft.registry.*;

public class SpectrumResonanceProcessors {
	
	public static void register(String id, MapCodec<? extends ResonanceDropProcessor> target) {
		Registry.register(SpectrumRegistries.RESONANCE_DROP_PROCESSOR_TYPE, SpectrumCommon.locate(id), target);
	}
	
	public static void register() {
		register("drop_self", DropSelfResonanceProcessor.CODEC);
		register("modify_drops", ModifyDropsResonanceProcessor.CODEC);
	}
	
}

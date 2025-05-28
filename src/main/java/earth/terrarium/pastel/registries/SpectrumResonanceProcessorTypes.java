package earth.terrarium.pastel.registries;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.data_loaders.resonance_processors.DropSelfResonanceProcessor;
import earth.terrarium.pastel.data_loaders.resonance_processors.ModifyDropsResonanceProcessor;
import net.minecraft.core.Registry;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumResonanceProcessorTypes {

	private static final DeferredRegister<MapCodec<? extends ResonanceProcessor>> REGISTER = DeferredRegister.create(SpectrumRegistries.RESONANCE_PROCESSOR_TYPE, SpectrumCommon.MOD_ID);

	public static void register(String id, MapCodec<? extends ResonanceProcessor> target) {
		REGISTER.register(id, () -> target);
	}
	
	public static void register(IEventBus bus) {
		register("drop_self", DropSelfResonanceProcessor.CODEC);
		register("modify_drops", ModifyDropsResonanceProcessor.CODEC);
		REGISTER.register(bus);
	}
	
}

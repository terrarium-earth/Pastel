package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumPotions {
	
	public static Holder<Potion> PIGMENT_POTION;
	
	public static void register(IEventBus bus) {
		var registry = DeferredRegister.create(Registries.POTION, SpectrumCommon.MOD_ID);

		PIGMENT_POTION = registry.register("pigment_potion", () -> new Potion());

		registry.register(bus);
	}
	
}

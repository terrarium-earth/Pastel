package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.alchemy.Potion;

public class SpectrumPotions {
	
	public static Holder<Potion> PIGMENT_POTION;
	
	private static Holder<Potion> register(String name, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, SpectrumCommon.locate(name), potion);
	}
	
	public static void register() {
		PIGMENT_POTION = register("pigment_potion", new Potion());
	}
	
}

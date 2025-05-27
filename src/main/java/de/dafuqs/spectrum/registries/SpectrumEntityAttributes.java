package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumEntityAttributes {
	
	public static final ResourceLocation CRIT_MODIFIER_ID = SpectrumCommon.locate("crit_modifier");
	public static final ResourceLocation REACH_MODIFIER_ID = SpectrumCommon.locate("reach_modifier");

	private static final DeferredRegister<Attribute> REGISTER = DeferredRegister.create(Registries.ATTRIBUTE, SpectrumCommon.MOD_ID);

	/**
	 * How vulnerable the entity is to sleep effects. The sleep effects use this value as a multiplier
	 * <1 means it is more resistant than the default, getting weaker effects
	 * >1 means it is more vulnerable
	 */
	public static final Holder<Attribute> MENTAL_PRESENCE = REGISTER.register("mental_presence", () -> new RangedAttribute("attribute.name.pastel.mental_presence", 1.0, 0, 1024));
	
	public static void register(IEventBus bus) {
		REGISTER.register(bus);
	}
	
}

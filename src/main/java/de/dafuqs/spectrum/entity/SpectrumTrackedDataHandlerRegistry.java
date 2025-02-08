package de.dafuqs.spectrum.entity;

import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.entity.variants.*;
import de.dafuqs.spectrum.items.tools.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.data.*;
import net.minecraft.network.codec.*;

public class SpectrumTrackedDataHandlerRegistry {
	
	public static final TrackedDataHandler<InkColor> INK_COLOR = TrackedDataHandler.create(PacketCodecs.registryValue(SpectrumRegistries.INK_COLOR.getKey()));
	public static final TrackedDataHandler<GlassArrowVariant> GLASS_ARROW_VARIANT = TrackedDataHandler.create(PacketCodecs.registryValue(SpectrumRegistries.GLASS_ARROW_VARIANT.getKey()));
	
	public static final TrackedDataHandler<LizardFrillVariant> LIZARD_FRILL_VARIANT = TrackedDataHandler.create(PacketCodecs.registryValue(SpectrumRegistries.LIZARD_FRILL_VARIANT.getKey()));
	public static final TrackedDataHandler<LizardHornVariant> LIZARD_HORN_VARIANT = TrackedDataHandler.create(PacketCodecs.registryValue(SpectrumRegistries.LIZARD_HORN_VARIANT.getKey()));
	public static final TrackedDataHandler<KindlingVariant> KINDLING_VARIANT = TrackedDataHandler.create(PacketCodecs.registryValue(SpectrumRegistries.KINDLING_VARIANT.getKey()));

	public static void register() {
		TrackedDataHandlerRegistry.register(INK_COLOR);
		TrackedDataHandlerRegistry.register(GLASS_ARROW_VARIANT);
		
		TrackedDataHandlerRegistry.register(LIZARD_FRILL_VARIANT);
		TrackedDataHandlerRegistry.register(LIZARD_HORN_VARIANT);
		TrackedDataHandlerRegistry.register(KINDLING_VARIANT);
	}
	
}

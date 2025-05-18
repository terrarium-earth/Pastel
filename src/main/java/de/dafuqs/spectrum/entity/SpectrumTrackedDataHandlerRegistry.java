package de.dafuqs.spectrum.entity;

import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.entity.variants.KindlingVariant;
import de.dafuqs.spectrum.entity.variants.LizardFrillVariant;
import de.dafuqs.spectrum.entity.variants.LizardHornVariant;
import de.dafuqs.spectrum.items.tools.GlassArrowVariant;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class SpectrumTrackedDataHandlerRegistry {
	
	public static final EntityDataSerializer<InkColor> INK_COLOR = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.INK_COLOR.key()));
	public static final EntityDataSerializer<GlassArrowVariant> GLASS_ARROW_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.GLASS_ARROW_VARIANT.key()));
	
	public static final EntityDataSerializer<LizardFrillVariant> LIZARD_FRILL_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.LIZARD_FRILL_VARIANT.key()));
	public static final EntityDataSerializer<LizardHornVariant> LIZARD_HORN_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.LIZARD_HORN_VARIANT.key()));
	public static final EntityDataSerializer<KindlingVariant> KINDLING_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.KINDLING_VARIANT.key()));

	public static void register() {
		EntityDataSerializers.registerSerializer(INK_COLOR);
		EntityDataSerializers.registerSerializer(GLASS_ARROW_VARIANT);
		
		EntityDataSerializers.registerSerializer(LIZARD_FRILL_VARIANT);
		EntityDataSerializers.registerSerializer(LIZARD_HORN_VARIANT);
		EntityDataSerializers.registerSerializer(KINDLING_VARIANT);
	}
	
}

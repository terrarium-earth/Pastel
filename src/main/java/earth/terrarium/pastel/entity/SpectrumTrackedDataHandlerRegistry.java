package earth.terrarium.pastel.entity;

import earth.terrarium.pastel.*;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.entity.variants.KindlingVariant;
import earth.terrarium.pastel.entity.variants.LizardFrillVariant;
import earth.terrarium.pastel.entity.variants.LizardHornVariant;
import earth.terrarium.pastel.items.tools.GlassArrowVariant;
import earth.terrarium.pastel.registries.SpectrumRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumTrackedDataHandlerRegistry {

	private static final DeferredRegister<EntityDataSerializer<?>> REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, SpectrumCommon.MOD_ID);

	public static final EntityDataSerializer<InkColor> INK_COLOR = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.INK_COLOR.key()));
	public static final EntityDataSerializer<GlassArrowVariant> GLASS_ARROW_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.GLASS_ARROW_VARIANT.key()));
	
	public static final EntityDataSerializer<LizardFrillVariant> LIZARD_FRILL_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.LIZARD_FRILL_VARIANT.key()));
	public static final EntityDataSerializer<LizardHornVariant> LIZARD_HORN_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.LIZARD_HORN_VARIANT.key()));
	public static final EntityDataSerializer<KindlingVariant> KINDLING_VARIANT = EntityDataSerializer.forValueType(ByteBufCodecs.registry(SpectrumRegistries.KINDLING_VARIANT.key()));

	public static void register(IEventBus bus) {
		REGISTER.register("ink_color", () -> INK_COLOR);
		REGISTER.register("glass_arrow_variant", () -> GLASS_ARROW_VARIANT);
		REGISTER.register("lizard_frill_variant", () -> LIZARD_FRILL_VARIANT);
		REGISTER.register("lizard_horn_variant", () -> LIZARD_HORN_VARIANT);
		REGISTER.register("kindling_variant", () -> KINDLING_VARIANT);
		REGISTER.register(bus);
	}
	
}

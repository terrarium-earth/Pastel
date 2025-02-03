package de.dafuqs.spectrum.entity.variants;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

public enum LizardFrillVariant implements StringIdentifiable {
	
	SIMPLE("simple", "textures/entity/lizard/frills_simple.png"),
	FANCY("fancy", "textures/entity/lizard/frills_fancy.png"),
	RUFFLED("ruffled", "textures/entity/lizard/frills_ruffled.png"),
	MODEST("modest", "textures/entity/lizard/frills_modest.png"),
	NONE("none", "textures/entity/lizard/frills_none.png");
	
	public static Codec<LizardFrillVariant> CODEC = StringIdentifiable.createCodec(LizardFrillVariant::values);
	
	private final String name;
	private final Identifier id;
	private final Identifier texture;
	
	LizardFrillVariant(String name, String texture) {
		this.name = name;
		this.id = SpectrumCommon.locate(name);
		this.texture = SpectrumCommon.locate(texture);
		Registry.register(SpectrumRegistries.LIZARD_FRILL_VARIANT, id, this);
	}
	
	public TagKey<LizardFrillVariant> getReference() {
		return TagKey.of(SpectrumRegistries.LIZARD_FRILL_VARIANT.getKey(), id);
	}
	
	public Identifier getTexture() {
		return texture;
	}
	
	@Override
	public String asString() {
		return name;
	}
	
}

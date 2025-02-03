package de.dafuqs.spectrum.entity.variants;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

public enum LizardHornVariant implements StringIdentifiable {
	
	HORNY("horny", "textures/entity/lizard/horns_horny.png"),
	STRAIGHT("straight", "textures/entity/lizard/horns_straight.png"),
	FLEXIBLE("flexible", "textures/entity/lizard/horns_flexible.png"),
	QUEER("queer", "textures/entity/lizard/horns_queer.png"),
	POLY("poly", "textures/entity/lizard/horns_poly.png"),
	ONLY_LIKES_YOU_AS_A_FRIEND("friendzoned", "textures/entity/lizard/horns_friendzoned.png");
	
	public static Codec<LizardHornVariant> CODEC = StringIdentifiable.createCodec(LizardHornVariant::values);
	
	private final String name;
	private final Identifier id;
	private final Identifier texture;
	
	LizardHornVariant(String name, String texture) {
		this.name = name;
		this.id = SpectrumCommon.locate(name);
		this.texture = SpectrumCommon.locate(texture);
		Registry.register(SpectrumRegistries.LIZARD_HORN_VARIANT, id, this);
	}
	
	public TagKey<LizardHornVariant> getReference() {
		return TagKey.of(SpectrumRegistries.LIZARD_HORN_VARIANT.getKey(), id);
	}
	
	public Identifier getTexture() {
		return texture;
	}
	
	@Override
	public String asString() {
		return name;
	}
	
}

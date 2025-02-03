package de.dafuqs.spectrum.entity.variants;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.loot.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

public enum KindlingVariant implements StringIdentifiable {
	
	DEFAULT("default", "textures/entity/kindling/kindling.png", "textures/entity/kindling/kindling_blink.png", "textures/entity/kindling/kindling_angry.png", "textures/entity/kindling/kindling_clipped.png", "textures/entity/kindling/kindling_blink_clipped.png", "textures/entity/kindling/kindling_angry_clipped.png", SpectrumLootTables.KINDLING_CLIPPING);
	
	public static Codec<KindlingVariant> CODEC = StringIdentifiable.createCodec(KindlingVariant::values);
	
	private final String name;
	private final Identifier id;
	private final Identifier defaultTexture;
	private final Identifier blinkingTexture;
	private final Identifier angryTexture;
	private final Identifier clippedTexture;
	private final Identifier blinkingClippedTexture;
	private final Identifier angryClippedTexture;
	private final RegistryKey<LootTable> clippingLootTable;
	
	KindlingVariant(String name, String defaultTexture, String blinkingTexture, String angryTexture, String clippedTexture, String blinkingClippedTexture, String angryClippedTexture, RegistryKey<LootTable> clippingLootTable) {
		this.name = name;
		this.id = SpectrumCommon.locate(name);
		this.defaultTexture = SpectrumCommon.locate(defaultTexture);
		this.blinkingTexture = SpectrumCommon.locate(blinkingTexture);
		this.angryTexture = SpectrumCommon.locate(angryTexture);
		this.clippedTexture = SpectrumCommon.locate(clippedTexture);
		this.blinkingClippedTexture = SpectrumCommon.locate(blinkingClippedTexture);
		this.angryClippedTexture = SpectrumCommon.locate(angryClippedTexture);
		this.clippingLootTable = clippingLootTable;
		Registry.register(SpectrumRegistries.KINDLING_VARIANT, id, this);
	}
	
	public TagKey<KindlingVariant> getReference() {
		return TagKey.of(SpectrumRegistries.KINDLING_VARIANT.getKey(), id);
	}
	
	public Identifier getDefaultTexture() {
		return defaultTexture;
	}
	
	public Identifier getBlinkingTexture() {
		return blinkingTexture;
	}
	
	public Identifier getAngryTexture() {
		return angryTexture;
	}
	
	public Identifier getClippedTexture() {
		return clippedTexture;
	}
	
	public Identifier getBlinkingClippedTexture() {
		return blinkingClippedTexture;
	}
	
	public Identifier getAngryClippedTexture() {
		return angryClippedTexture;
	}
	
	public RegistryKey<LootTable> getClippingLootTable() {
		return clippingLootTable;
	}
	
	@Override
	public String asString() {
		return name;
	}
}

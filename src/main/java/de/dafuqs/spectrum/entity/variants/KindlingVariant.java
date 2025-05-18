package de.dafuqs.spectrum.entity.variants;

import com.mojang.serialization.Codec;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumLootTables;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.storage.loot.LootTable;

public enum KindlingVariant implements StringRepresentable {
	
	DEFAULT("default", "textures/entity/kindling/kindling.png", "textures/entity/kindling/kindling_blink.png", "textures/entity/kindling/kindling_angry.png", "textures/entity/kindling/kindling_clipped.png", "textures/entity/kindling/kindling_blink_clipped.png", "textures/entity/kindling/kindling_angry_clipped.png", SpectrumLootTables.KINDLING_CLIPPING);
	
	public static Codec<KindlingVariant> CODEC = StringRepresentable.fromEnum(KindlingVariant::values);
	
	private final String name;
	private final ResourceLocation id;
	private final ResourceLocation defaultTexture;
	private final ResourceLocation blinkingTexture;
	private final ResourceLocation angryTexture;
	private final ResourceLocation clippedTexture;
	private final ResourceLocation blinkingClippedTexture;
	private final ResourceLocation angryClippedTexture;
	private final ResourceKey<LootTable> clippingLootTable;
	
	KindlingVariant(String name, String defaultTexture, String blinkingTexture, String angryTexture, String clippedTexture, String blinkingClippedTexture, String angryClippedTexture, ResourceKey<LootTable> clippingLootTable) {
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
		return TagKey.create(SpectrumRegistries.KINDLING_VARIANT.key(), id);
	}
	
	public ResourceLocation getDefaultTexture() {
		return defaultTexture;
	}
	
	public ResourceLocation getBlinkingTexture() {
		return blinkingTexture;
	}
	
	public ResourceLocation getAngryTexture() {
		return angryTexture;
	}
	
	public ResourceLocation getClippedTexture() {
		return clippedTexture;
	}
	
	public ResourceLocation getBlinkingClippedTexture() {
		return blinkingClippedTexture;
	}
	
	public ResourceLocation getAngryClippedTexture() {
		return angryClippedTexture;
	}
	
	public ResourceKey<LootTable> getClippingLootTable() {
		return clippingLootTable;
	}
	
	@Override
	public String getSerializedName() {
		return name;
	}
}

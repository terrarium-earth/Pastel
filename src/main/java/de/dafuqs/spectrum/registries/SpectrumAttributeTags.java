package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class SpectrumAttributeTags {

	public static final TagKey<Attribute> INEXORABLE_ARMOR_EFFECTIVE = TagKey.create(BuiltInRegistries.ATTRIBUTE.key(), SpectrumCommon.locate("inexorable_armor_effective"));
	public static final TagKey<Attribute> INEXORABLE_HANDHELD_EFFECTIVE = TagKey.create(BuiltInRegistries.ATTRIBUTE.key(), SpectrumCommon.locate("inexorable_handheld_effective"));

}

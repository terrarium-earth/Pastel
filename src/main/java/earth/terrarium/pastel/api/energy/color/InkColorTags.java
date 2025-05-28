package earth.terrarium.pastel.api.energy.color;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.registries.SpectrumRegistries;
import net.minecraft.tags.TagKey;

public class InkColorTags {
	
	public static final TagKey<InkColor> ELEMENTAL_COLORS = getReference("elementals");
	public static final TagKey<InkColor> COMPOUND_COLORS = getReference("compounds");
	
	private static TagKey<InkColor> getReference(String name) {
		return TagKey.create(SpectrumRegistries.INK_COLOR.key(), SpectrumCommon.locate(name));
	}
	
}

package earth.terrarium.pastel.api.energy.color;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.tags.TagKey;

public class InkColorTags {
	
	public static final TagKey<InkColor> ELEMENTAL_COLORS = getReference("elementals");
	public static final TagKey<InkColor> COMPOUND_COLORS = getReference("compounds");
	
	private static TagKey<InkColor> getReference(String name) {
		return TagKey.create(PastelRegistries.INK_COLOR.key(), PastelCommon.locate(name));
	}
	
}

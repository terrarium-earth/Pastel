package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class SpectrumPointOfInterestTypeTags {
	
	public static final TagKey<PoiType> LIZARD_DENS = of("lizard_dens");
	
	private static TagKey<PoiType> of(String id) {
		return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, SpectrumCommon.locate(id));
	}
	
}

package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Optional;

public class SpectrumStructureTags {
	
	public static final TagKey<Structure> MYSTERIOUS_COMPASS_LOCATED = of("mysterious_compass_located");
	public static final TagKey<Structure> UNLOCATABLE = of("unlocatable");
	
	private static TagKey<Structure> of(String id) {
		return TagKey.create(Registries.STRUCTURE, SpectrumCommon.locate(id));
	}
	
	public static Optional<HolderSet.Named<Structure>> entriesOf(Level world, TagKey<Structure> tag) {
		Registry<Structure> registry = world.registryAccess().registryOrThrow(Registries.STRUCTURE);
		return registry.getTag(tag);
	}
	
	public static boolean isIn(Level world, ResourceLocation id, TagKey<Structure> tag) {
		Registry<Structure> registry = world.registryAccess().registryOrThrow(Registries.STRUCTURE);
		Structure structure = registry.get(id);
		Optional<HolderSet.Named<Structure>> tagEntries = entriesOf(world, tag);
		
		if (tagEntries.isPresent()) {
			for (Holder<Structure> entry : tagEntries.get()) {
				if (entry.value().equals(structure)) {
					return true;
				}
			}
		}
		return false;
	}
	
}

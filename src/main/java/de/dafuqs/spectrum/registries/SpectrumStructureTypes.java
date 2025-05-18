package de.dafuqs.spectrum.registries;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.worldgen.structures.UndergroundJigsawStructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class SpectrumStructureTypes {
	
	public static StructureType<UndergroundJigsawStructure> UNDERGROUND_JIGSAW;
	
	public static void register() {
		UNDERGROUND_JIGSAW = register("underground_jigsaw", UndergroundJigsawStructure.CODEC);
	}
	
	private static <S extends Structure> StructureType<S> register(String id, MapCodec<S> codec) {
		return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, SpectrumCommon.locate(id), () -> codec);
	}
	
}

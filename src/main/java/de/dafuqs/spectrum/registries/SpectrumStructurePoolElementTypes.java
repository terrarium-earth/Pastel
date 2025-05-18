package de.dafuqs.spectrum.registries;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.worldgen.structure_pool_elements.SingleBlockPoolElement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;

public class SpectrumStructurePoolElementTypes {
	
	/**
	 * WeightedPool element that replaces the jigsaw with a single block
	 * that block supports state tags and block entity nbt
	 */
	public static final StructurePoolElementType<SingleBlockPoolElement> SINGLE_BLOCK_ELEMENT = registerType("single_block_element", SingleBlockPoolElement.CODEC);

	static <P extends StructurePoolElement> StructurePoolElementType<P> registerType(String id, MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.STRUCTURE_POOL_ELEMENT, SpectrumCommon.locate(id), () -> codec);
	}
	
	public static void register() {
	
	}
	
}

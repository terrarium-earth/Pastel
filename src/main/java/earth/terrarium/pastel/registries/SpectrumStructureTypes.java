package earth.terrarium.pastel.registries;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.worldgen.structures.UndergroundJigsawStructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumStructureTypes {
	
	public static StructureType<UndergroundJigsawStructure> UNDERGROUND_JIGSAW = () -> UndergroundJigsawStructure.CODEC;
	
	public static void register(IEventBus bus) {
		var register = DeferredRegister.create(Registries.STRUCTURE_TYPE, SpectrumCommon.MOD_ID);
		register.register("underground_jigsaw", () -> UNDERGROUND_JIGSAW);
		register.register(bus);
	}
}

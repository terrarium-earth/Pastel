package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.worldgen.structures.UndergroundJigsawStructure;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class PastelStructureTypes {

    public static StructureType<UndergroundJigsawStructure> UNDERGROUND_JIGSAW = () -> UndergroundJigsawStructure.CODEC;

    public static void register(IEventBus bus) {
        var register = DeferredRegister.create(Registries.STRUCTURE_TYPE, PastelCommon.MOD_ID);
        register.register("underground_jigsaw", () -> UNDERGROUND_JIGSAW);
        register.register(bus);
    }
}

package earth.terrarium.pastel.registries;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.worldgen.structure_pool_elements.SingleBlockPoolElement;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

public class PastelStructurePoolElementTypes {

    private static final DeferredRegister<StructurePoolElementType<?>> REGISTER = DeferredRegister.create(
        Registries.STRUCTURE_POOL_ELEMENT, PastelCommon.MOD_ID);

    /**
     * WeightedPool element that replaces the jigsaw with a single block
     * that block supports state tags and block entity nbt
     */
    public static final StructurePoolElementType<SingleBlockPoolElement> SINGLE_BLOCK_ELEMENT = registerType(
        "single_block_element", SingleBlockPoolElement.CODEC);

    static <P extends StructurePoolElement> StructurePoolElementType<P> registerType(String id, MapCodec<P> codec) {
        StructurePoolElementType<P> type = () -> codec;
        REGISTER.register(id, () -> type);
        return type;
    }

    public static void register(IEventBus bus) {
        REGISTER.register(bus);
    }

}

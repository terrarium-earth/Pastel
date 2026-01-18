package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;

public class FluidBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.LIQUID_CRYSTAL, PastelTexturedModels.particle(b -> b, "_still"));
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.HUMUS, PastelTexturedModels.particle(b -> b, "_still"));
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.MIDNIGHT_SOLUTION, PastelTexturedModels.particle(b -> b, "_still"));
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.DRAGONROT, PastelTexturedModels.particle(b -> b, "_still"));
    }
}

package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.blocks.fluid.DragonrotFluidBlock;
import earth.terrarium.pastel.blocks.fluid.HumusFluidBlock;
import earth.terrarium.pastel.blocks.fluid.LiquidCrystalFluidBlock;
import earth.terrarium.pastel.blocks.fluid.MidnightSolutionFluidBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFluids;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import static net.minecraft.world.level.block.Blocks.BLACKSTONE;
import static net.minecraft.world.level.block.Blocks.MUD;

public class FluidBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.singleton(
            generators, PastelBlocks.LIQUID_CRYSTAL, PastelTexturedModels.particle(b -> b, "_still"));
        PastelModelHelper.singleton(generators, PastelBlocks.HUMUS, PastelTexturedModels.particle(b -> b, "_still"));
        PastelModelHelper.singleton(
            generators, PastelBlocks.MIDNIGHT_SOLUTION, PastelTexturedModels.particle(b -> b, "_still"));
        PastelModelHelper.singleton(
            generators, PastelBlocks.DRAGONROT, PastelTexturedModels.particle(b -> b, "_still"));
    }
}

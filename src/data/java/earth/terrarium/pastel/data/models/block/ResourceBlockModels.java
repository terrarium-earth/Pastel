package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.data.models.block.resource.CompactBlockModels;
import earth.terrarium.pastel.data.models.block.resource.GemstoneBlockModels;
import earth.terrarium.pastel.data.models.block.resource.OreBlockModels;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.TexturedModel;

public class ResourceBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        CompactBlockModels.generateBlockModels(generators);
        GemstoneBlockModels.generateBlockModels(generators);
        OreBlockModels.generateBlockModels(generators);

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.ROCK_CRYSTAL);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.PYRITE, TexturedModel.COLUMN);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.DRAGONBONE, TexturedModel.COLUMN);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.CRACKED_DRAGONBONE, TexturedModel.COLUMN);

        PastelModelHelper.BLOCK.defaultWestHorizontalFacing(
            generators, PastelBlocks.STUCK_STORM_STONE,
            ModelLocationUtils::getModelLocation
        );

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.FROSTBITE_CRYSTAL);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.BLAZING_CRYSTAL);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.RADIATING_ENDER);
    }
}

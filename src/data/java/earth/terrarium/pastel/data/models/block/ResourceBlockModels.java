package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.conditional.RadiatingEnderBlock;
import earth.terrarium.pastel.blocks.deeper_down.DragonboneBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.data.models.block.resource.CompactBlockModels;
import earth.terrarium.pastel.data.models.block.resource.GemstoneBlockModels;
import earth.terrarium.pastel.data.models.block.resource.OreBlockModels;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

import static net.minecraft.world.level.block.Blocks.*;

public class ResourceBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators){
        CompactBlockModels.generateBlockModels(generators);
        GemstoneBlockModels.generateBlockModels(generators);
        OreBlockModels.generateBlockModels(generators);

        PastelModelHelper.simple(generators, PastelBlocks.ROCK_CRYSTAL);
        PastelModelHelper.axisRotated(generators,PastelBlocks.PYRITE,TexturedModel.COLUMN);
        PastelModelHelper.axisRotated(generators,PastelBlocks.DRAGONBONE,TexturedModel.COLUMN);
        PastelModelHelper.axisRotated(generators,PastelBlocks.CRACKED_DRAGONBONE,TexturedModel.COLUMN);

        PastelModelHelper.simple(generators,PastelBlocks.FROSTBITE_CRYSTAL);
        PastelModelHelper.simple(generators,PastelBlocks.BLAZING_CRYSTAL);
        PastelModelHelper.simple(generators,PastelBlocks.RADIATING_ENDER);
    }
}

package earth.terrarium.pastel.data.models.block.resource;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.BismuthBudBlock;
import earth.terrarium.pastel.blocks.BlockWithTooltip;
import earth.terrarium.pastel.blocks.crystallarieum.PastelClusterBlock;
import earth.terrarium.pastel.blocks.decoration.PastelFacingBlock;
import earth.terrarium.pastel.blocks.gemstone.PastelBuddingBlock;
import earth.terrarium.pastel.blocks.gemstone.PastelGemstoneBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlockSoundGroups;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

import static net.minecraft.world.level.block.Blocks.*;
import static net.minecraft.world.level.block.Blocks.ANCIENT_DEBRIS;
import static net.minecraft.world.level.block.Blocks.COPPER_BLOCK;
import static net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK;
import static net.minecraft.world.level.block.Blocks.EMERALD_BLOCK;
import static net.minecraft.world.level.block.Blocks.GLOWSTONE;
import static net.minecraft.world.level.block.Blocks.GOLD_BLOCK;
import static net.minecraft.world.level.block.Blocks.LAPIS_BLOCK;
import static net.minecraft.world.level.block.Blocks.QUARTZ_BLOCK;
import static net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK;
import static net.minecraft.world.level.block.Blocks.SCULK;

public class GemstoneBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators){
        PastelModelHelper.cluster(generators,PastelBlocks.TOPAZ_CLUSTER,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_TOPAZ_BUD,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.MEDIUM_TOPAZ_BUD,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_TOPAZ_BUD,ModelTemplates.CROSS);
        PastelModelHelper.simple(generators,PastelBlocks.BUDDING_TOPAZ);
        generators.blockStateOutput.accept(PastelModelHelper.simpleMirroredBlockModel(generators,PastelBlocks.TOPAZ_BLOCK.get()));

        PastelModelHelper.cluster(generators,PastelBlocks.CITRINE_CLUSTER,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_CITRINE_BUD,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.MEDIUM_CITRINE_BUD,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_CITRINE_BUD,ModelTemplates.CROSS);
        PastelModelHelper.simple(generators,PastelBlocks.BUDDING_CITRINE);
        generators.blockStateOutput.accept(PastelModelHelper.simpleMirroredBlockModel(generators,PastelBlocks.CITRINE_BLOCK.get()));
        
        PastelModelHelper.cluster(generators,PastelBlocks.ONYX_CLUSTER,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_ONYX_BUD,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.MEDIUM_ONYX_BUD,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_ONYX_BUD,ModelTemplates.CROSS);
        PastelModelHelper.simple(generators,PastelBlocks.BUDDING_ONYX);
        generators.blockStateOutput.accept(PastelModelHelper.simpleMirroredBlockModel(generators,PastelBlocks.ONYX_BLOCK.get()));
        
        PastelModelHelper.cluster(generators,PastelBlocks.MOONSTONE_CLUSTER,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_MOONSTONE_BUD,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.MEDIUM_MOONSTONE_BUD,ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_MOONSTONE_BUD,ModelTemplates.CROSS);
        PastelModelHelper.simple(generators,PastelBlocks.BUDDING_MOONSTONE);
        generators.blockStateOutput.accept(PastelModelHelper.simpleMirroredBlockModel(generators,PastelBlocks.MOONSTONE_BLOCK.get()));
        
        PastelModelHelper.simple(generators,PastelBlocks.TOPAZ_POWDER_BLOCK);
        PastelModelHelper.simple(generators,PastelBlocks.AMETHYST_POWDER_BLOCK);
        PastelModelHelper.simple(generators,PastelBlocks.CITRINE_POWDER_BLOCK);
        PastelModelHelper.simple(generators,PastelBlocks.ONYX_POWDER_BLOCK);
        PastelModelHelper.simple(generators,PastelBlocks.MOONSTONE_POWDER_BLOCK);

        PastelModelHelper.cluster(generators,PastelBlocks.BISMUTH_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_BISMUTH_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_BISMUTH_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.simple(generators,PastelBlocks.BISMUTH_BLOCK);

        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_COAL_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_COAL_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.COAL_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_IRON_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_IRON_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.IRON_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_GOLD_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_GOLD_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.GOLD_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_DIAMOND_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_DIAMOND_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.DIAMOND_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_EMERALD_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_EMERALD_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.EMERALD_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_REDSTONE_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_REDSTONE_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.REDSTONE_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_LAPIS_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_LAPIS_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LAPIS_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_COPPER_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_COPPER_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.COPPER_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_QUARTZ_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_QUARTZ_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.QUARTZ_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_NETHERITE_SCRAP_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_NETHERITE_SCRAP_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.NETHERITE_SCRAP_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_ECHO_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_ECHO_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.ECHO_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_GLOWSTONE_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_GLOWSTONE_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.GLOWSTONE_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.SMALL_PRISMARINE_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.LARGE_PRISMARINE_BUD,PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators,PastelBlocks.PRISMARINE_CLUSTER,PastelModels.CRYSTALLARIEUM_FARMABLE);

        PastelModelHelper.simple(generators,PastelBlocks.AZURITE_CLUSTER);
        PastelModelHelper.simple(generators,PastelBlocks.LARGE_AZURITE_BUD);
        PastelModelHelper.simple(generators,PastelBlocks.SMALL_AZURITE_BUD);
        PastelModelHelper.simple(generators,PastelBlocks.MALACHITE_CLUSTER);
        PastelModelHelper.simple(generators,PastelBlocks.LARGE_MALACHITE_BUD);
        PastelModelHelper.simple(generators,PastelBlocks.SMALL_MALACHITE_BUD);
        PastelModelHelper.simple(generators,PastelBlocks.BLOODSTONE_CLUSTER);
        PastelModelHelper.simple(generators,PastelBlocks.LARGE_BLOODSTONE_BUD);
        PastelModelHelper.simple(generators,PastelBlocks.SMALL_BLOODSTONE_BUD);
    }
}

package earth.terrarium.pastel.data.models.block.resource;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.conditional.CloakedOreBlock;
import earth.terrarium.pastel.blocks.conditional.GemstoneOreBlock;
import earth.terrarium.pastel.blocks.crystallarieum.PastelClusterBlock;
import earth.terrarium.pastel.blocks.decoration.PastelFacingBlock;
import earth.terrarium.pastel.blocks.decoration.ShimmerstoneBlock;
import earth.terrarium.pastel.blocks.geology.AzuriteOreBlock;
import earth.terrarium.pastel.blocks.geology.ShimmerstoneOreBlock;
import earth.terrarium.pastel.blocks.gravity.FloatBlockItem;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.registries.PastelBlockSoundGroups;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import static net.minecraft.world.level.block.Blocks.LAPIS_BLOCK;
import static net.minecraft.world.level.block.Blocks.litBlockEmission;

public class OreBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators){
        PastelModelHelper.simple(generators,PastelBlocks.SHIMMERSTONE_ORE);
        PastelModelHelper.simple(generators, PastelBlocks.DEEPSLATE_SHIMMERSTONE_ORE);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_SHIMMERSTONE_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.simple(generators,PastelBlocks.SHIMMERSTONE_BLOCK);

        PastelModelHelper.simple(generators,PastelBlocks.AZURITE_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.DEEPSLATE_AZURITE_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.BLACKSLAG_AZURITE_ORE);

        PastelModelHelper.simple(generators,PastelBlocks.MALACHITE_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.DEEPSLATE_MALACHITE_ORE);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_MALACHITE_ORE,TexturedModel.COLUMN_ALT);


        PastelModelHelper.simple(generators,PastelBlocks.STRATINE_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.PALTAERIA_ORE);

        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_COAL_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_COPPER_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_IRON_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_GOLD_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_LAPIS_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_DIAMOND_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_REDSTONE_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_EMERALD_ORE,TexturedModel.COLUMN_ALT);

        PastelModelHelper.simple(generators,PastelBlocks.TOPAZ_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.AMETHYST_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.CITRINE_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.ONYX_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.MOONSTONE_ORE);

        PastelModelHelper.simple(generators,PastelBlocks.DEEPSLATE_TOPAZ_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.DEEPSLATE_AMETHYST_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.DEEPSLATE_CITRINE_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.DEEPSLATE_ONYX_ORE);
        PastelModelHelper.simple(generators,PastelBlocks.DEEPSLATE_MOONSTONE_ORE);

        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_TOPAZ_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_AMETHYST_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_CITRINE_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_ONYX_ORE,TexturedModel.COLUMN_ALT);
        PastelModelHelper.singleton(generators,PastelBlocks.BLACKSLAG_MOONSTONE_ORE,TexturedModel.COLUMN_ALT);
    }
}

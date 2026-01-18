package earth.terrarium.pastel.data.models.block.resource;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.BlockWithTooltip;
import earth.terrarium.pastel.blocks.PureRedstoneBlock;
import earth.terrarium.pastel.blocks.decoration.PastelFacingBlock;
import earth.terrarium.pastel.blocks.gemstone.PastelGemstoneBlock;
import earth.terrarium.pastel.blocks.gravity.FloatBlock;
import earth.terrarium.pastel.blocks.gravity.FloatBlockItem;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.data.models.PastelBlockModels;
import earth.terrarium.pastel.registries.PastelBlockSoundGroups;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
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
import static net.minecraft.world.level.block.Blocks.LAPIS_BLOCK;
import static net.minecraft.world.level.block.Blocks.PRISMARINE;
import static net.minecraft.world.level.block.Blocks.QUARTZ_BLOCK;
import static net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK;

public class CompactBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.translucent(PastelBlocks.VEGETAL_BLOCK);
        PastelModelHelper.singleton(
            generators, PastelBlocks.VEGETAL_BLOCK, TexturedModel.createDefault(
                TextureMapping::defaultTexture,
                PastelModels.TRANSLUCENT_OUTER1
            )
        );
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(
                                     PastelBlocks.NEOLITH_BLOCK.get(), PastelModelHelper.createModelVariant(
                                         TexturedModel.CUBE_TOP_BOTTOM.create(PastelBlocks.NEOLITH_BLOCK.get(),
                                                                              generators.modelOutput))
                                 )
                                 .with(PastelModelHelper.createUpDefaultFacingVariantMap()));
        PastelModelHelper.simple(generators, PastelBlocks.BEDROCK_DUST_BLOCK);
        PastelModelHelper.singleton(
            generators, PastelBlocks.PALTAERIA_FLOATBLOCK,
            PastelTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top", b -> b, "_bottom")
        );
        PastelModelHelper.singleton(
            generators, PastelBlocks.STRATINE_FLOATBLOCK,
            PastelTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top", b -> b, "_bottom")
        );

        PastelModelHelper.singleton(generators, PastelBlocks.POLISHED_TOPAZ_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);
        PastelModelHelper.singleton(
            generators, PastelBlocks.POLISHED_AMETHYST_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);
        PastelModelHelper.singleton(
            generators, PastelBlocks.POLISHED_CITRINE_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);
        PastelModelHelper.singleton(generators, PastelBlocks.POLISHED_ONYX_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);
        PastelModelHelper.singleton(
            generators, PastelBlocks.POLISHED_MOONSTONE_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);

        PastelModelHelper.simple(
            generators, PastelBlocks.PURE_COAL_BLOCK, PastelBlocks.PURE_IRON_BLOCK, PastelBlocks.PURE_GOLD_BLOCK,
            PastelBlocks.PURE_DIAMOND_BLOCK, PastelBlocks.PURE_EMERALD_BLOCK, PastelBlocks.PURE_REDSTONE_BLOCK,
            PastelBlocks.PURE_LAPIS_BLOCK, PastelBlocks.PURE_COPPER_BLOCK, PastelBlocks.PURE_QUARTZ_BLOCK,
            PastelBlocks.PURE_GLOWSTONE_BLOCK, PastelBlocks.PURE_PRISMARINE_BLOCK,
            PastelBlocks.PURE_NETHERITE_SCRAP_BLOCK, PastelBlocks.PURE_ECHO_BLOCK, PastelBlocks.STARDUST_BLOCK
        );

        PastelModelHelper.defaultUpFacing(generators,PastelBlocks.AZURITE_BLOCK,TexturedModel.CUBE_TOP_BOTTOM);
        PastelModelHelper.defaultUpFacing(generators,PastelBlocks.MALACHITE_BLOCK,TexturedModel.COLUMN_ALT);
        PastelModelHelper.defaultUpFacing(generators,PastelBlocks.BLOODSTONE_BLOCK,TexturedModel.COLUMN);
    }
}

package earth.terrarium.pastel.data.models.block.resource;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;

public class CompactBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.BLOCK.singleton(
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
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.BEDROCK_DUST_BLOCK);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.PALTAERIA_FLOATBLOCK,
            PastelTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top", b -> b, "_bottom")
        );
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.STRATINE_FLOATBLOCK,
            PastelTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top", b -> b, "_bottom")
        );

        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.POLISHED_TOPAZ_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.POLISHED_AMETHYST_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.POLISHED_CITRINE_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.POLISHED_ONYX_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.POLISHED_MOONSTONE_BLOCK, TexturedModel.TOP_BOTTOM_WITH_WALL);

        PastelModelHelper.BLOCK.simple(
            generators, PastelBlocks.PURE_COAL_BLOCK, PastelBlocks.PURE_IRON_BLOCK, PastelBlocks.PURE_GOLD_BLOCK,
            PastelBlocks.PURE_DIAMOND_BLOCK, PastelBlocks.PURE_EMERALD_BLOCK, PastelBlocks.PURE_REDSTONE_BLOCK,
            PastelBlocks.PURE_LAPIS_BLOCK, PastelBlocks.PURE_COPPER_BLOCK, PastelBlocks.PURE_QUARTZ_BLOCK,
            PastelBlocks.PURE_GLOWSTONE_BLOCK, PastelBlocks.PURE_PRISMARINE_BLOCK,
            PastelBlocks.PURE_NETHERITE_SCRAP_BLOCK, PastelBlocks.PURE_ECHO_BLOCK, PastelBlocks.STARDUST_BLOCK
        );

        PastelModelHelper.BLOCK.defaultUpFacing(generators,PastelBlocks.AZURITE_BLOCK,TexturedModel.CUBE_TOP_BOTTOM);
        PastelModelHelper.BLOCK.defaultUpFacing(generators,PastelBlocks.MALACHITE_BLOCK,TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.defaultUpFacing(generators,PastelBlocks.BLOODSTONE_BLOCK,TexturedModel.COLUMN);
    }
}

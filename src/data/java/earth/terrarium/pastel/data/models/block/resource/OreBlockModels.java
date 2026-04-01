package earth.terrarium.pastel.data.models.block.resource;

import earth.terrarium.pastel.blocks.geology.AzureCrystalBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.model.TexturedModel;

public class OreBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.SHIMMERSTONE_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DEEPSLATE_SHIMMERSTONE_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.SHIMMERSTONE_BLOCK);

        // hell.
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.AZURE_CRYSTAL.get())
                                                                .with(PropertyDispatch.property(AzureCrystalBlock.WARDED)
                                                                                      .generate(
                                                                                          (warded) -> PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cubeAll(
                                                                                                                      b -> b,
                                                                                                                      warded ? "_warded"
                                                                                                                             : ""
                                                                                                                  )
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.AZURE_CRYSTAL.get(),
                                                                                                                      warded ? "_warded"
                                                                                                                             : "",
                                                                                                                      generators.modelOutput
                                                                                                                  )))));
        PastelModelHelper.BLOCK.cross(generators,PastelBlocks.AZURE_OUTCROP);
        PastelModelHelper.BLOCK.simple(generators,PastelBlocks.BUDDING_AZURITE);

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.VIRIDIAN_CRYSTAL);


        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.STRATINE_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.PALTAERIA_ORE);

        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_COAL_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_COPPER_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_IRON_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_GOLD_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_LAPIS_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_DIAMOND_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_REDSTONE_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_EMERALD_ORE, TexturedModel.COLUMN_ALT);

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.TOPAZ_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.AMETHYST_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.CITRINE_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.ONYX_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.MOONSTONE_ORE);

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DEEPSLATE_TOPAZ_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DEEPSLATE_AMETHYST_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DEEPSLATE_CITRINE_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DEEPSLATE_ONYX_ORE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DEEPSLATE_MOONSTONE_ORE);

        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_TOPAZ_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_AMETHYST_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_CITRINE_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_ONYX_ORE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.BLACKSLAG_MOONSTONE_ORE, TexturedModel.COLUMN_ALT);
    }
}

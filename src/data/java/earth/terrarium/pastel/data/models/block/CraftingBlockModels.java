package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.blocks.titration_barrel.TitrationBarrelBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.model.ModelLocationUtils;

public class CraftingBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.BLOCK.pedestal(generators, PastelBlocks.PEDESTAL_BASIC_TOPAZ);
        PastelModelHelper.BLOCK.pedestal(generators, PastelBlocks.PEDESTAL_BASIC_AMETHYST);
        PastelModelHelper.BLOCK.pedestal(generators, PastelBlocks.PEDESTAL_BASIC_CITRINE);
        PastelModelHelper.BLOCK.pedestal(generators, PastelBlocks.PEDESTAL_ALL_BASIC);
        PastelModelHelper.BLOCK.pedestal(generators, PastelBlocks.PEDESTAL_ONYX);
        PastelModelHelper.BLOCK.pedestal(generators, PastelBlocks.PEDESTAL_MOONSTONE);
        PastelModelHelper.BLOCK
            .singleton(generators, PastelBlocks.FUSION_SHRINE_BASALT, PastelTexturedModels.FUSION_SHRINE);
        PastelModelHelper.BLOCK
            .singleton(generators, PastelBlocks.FUSION_SHRINE_CALCITE, PastelTexturedModels.FUSION_SHRINE);
        PastelModelHelper.BLOCK
            .singletonWithSoup(generators, PastelBlocks.ENCHANTER, ModelLocationUtils::getModelLocation);

        PastelModelHelper.BLOCK
            .defaultNorthHorizontalFacing(
                generators,
                PastelBlocks.POTION_WORKSHOP,
                ModelLocationUtils::getModelLocation
            );
        PastelModelHelper.BLOCK
            .singletonWithSoup(
                generators,
                PastelBlocks.SPIRIT_INSTILLER,
                ModelLocationUtils::getModelLocation
            );
        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.SPIRIT_INSTILLER);
        PastelModelHelper.BLOCK
            .singletonWithSoup(
                generators,
                PastelBlocks.CRYSTALLARIEUM,
                ModelLocationUtils::getModelLocation
            );
        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.CRYSTALLARIEUM);

        PastelModelHelper.BLOCK
            .defaultNorthHorizontalFacing(
                generators,
                PastelBlocks.CINDERHEARTH,
                ModelLocationUtils::getModelLocation
            );
        PastelModelHelper.BLOCK
            .defaultWestHorizontalFacing(
                generators,
                PastelBlocks.COLOR_PICKER,
                ModelLocationUtils::getModelLocation
            );

        PastelModelHelper.BLOCK
            .singletonWithSoup(
                generators,
                PastelBlocks.CRYSTAL_APOTHECARY,
                ModelLocationUtils::getModelLocation
            );

        generators.blockStateOutput
            .accept(
                MultiVariantGenerator
                    .multiVariant(PastelBlocks.TITRATION_BARREL.get())
                    .with(
                        PastelModelHelper.createUpDefaultHorizontalFacingVariantMap()
                    )
                    .with(
                        PropertyDispatch
                            .property(
                                TitrationBarrelBlock.BARREL_STATE
                            )
                            .generate(
                                state -> PastelModelHelper
                                    .createModelVariant(
                                        PastelTexturedModels
                                            .cubeBottomTop(
                                                b -> b,
                                                "_side",
                                                b -> b,
                                                "_top_" + state.getSerializedName(),
                                                b -> b,
                                                "_bottom"
                                            )
                                            .createWithSuffix(
                                                PastelBlocks.TITRATION_BARREL.get(),
                                                state == TitrationBarrelBlock.BarrelState.EMPTY
                                                    ? ""
                                                    : "_" + state.getSerializedName(),
                                                generators.modelOutput
                                            )
                                    )
                            )
                    )
            );
    }
}

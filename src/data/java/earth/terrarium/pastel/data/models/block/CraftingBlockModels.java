package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.cinderhearth.CinderhearthBlock;
import earth.terrarium.pastel.blocks.crystallarieum.CrystallarieumBlock;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlock;
import earth.terrarium.pastel.blocks.energy.ColorPickerBlock;
import earth.terrarium.pastel.blocks.energy.CrystalApothecaryBlock;
import earth.terrarium.pastel.blocks.fusion_shrine.FusionShrineBlock;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlock;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockItem;
import earth.terrarium.pastel.blocks.pedestal.PedestalVariants;
import earth.terrarium.pastel.blocks.potion_workshop.PotionWorkshopBlock;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlock;
import earth.terrarium.pastel.blocks.titration_barrel.TitrationBarrelBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.data.models.PastelBlockModels;
import earth.terrarium.pastel.registries.PastelBlockSoundGroups;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;

public class CraftingBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.pedestal(generators, PastelBlocks.PEDESTAL_BASIC_TOPAZ);
        PastelModelHelper.pedestal(generators, PastelBlocks.PEDESTAL_BASIC_AMETHYST);
        PastelModelHelper.pedestal(generators, PastelBlocks.PEDESTAL_BASIC_CITRINE);
        PastelModelHelper.pedestal(generators, PastelBlocks.PEDESTAL_ALL_BASIC);
        PastelModelHelper.pedestal(generators, PastelBlocks.PEDESTAL_ONYX);
        PastelModelHelper.pedestal(generators, PastelBlocks.PEDESTAL_MOONSTONE);
        PastelModelHelper.singleton(generators, PastelBlocks.FUSION_SHRINE_BASALT, PastelTexturedModels.FUSION_SHRINE);
        PastelModelHelper.singleton(generators, PastelBlocks.FUSION_SHRINE_CALCITE, PastelTexturedModels.FUSION_SHRINE);
        PastelModelHelper.singletonWithSoup(generators, PastelBlocks.ENCHANTER, ModelLocationUtils::getModelLocation);

        PastelModelHelper.defaultNorthHorizontalFacing(
            generators, PastelBlocks.POTION_WORKSHOP, ModelLocationUtils::getModelLocation);
        PastelModelHelper.singletonWithSoup(
            generators, PastelBlocks.SPIRIT_INSTILLER, ModelLocationUtils::getModelLocation);
        PastelModelHelper.predefinedItemModel(generators, PastelBlocks.SPIRIT_INSTILLER);
        PastelModelHelper.singletonWithSoup(
            generators, PastelBlocks.CRYSTALLARIEUM, ModelLocationUtils::getModelLocation);
        PastelModelHelper.predefinedItemModel(generators, PastelBlocks.CRYSTALLARIEUM);

        PastelModelHelper.defaultNorthHorizontalFacing(
            generators, PastelBlocks.CINDERHEARTH, ModelLocationUtils::getModelLocation);
        PastelModelHelper.defaultWestHorizontalFacing(
            generators, PastelBlocks.COLOR_PICKER, ModelLocationUtils::getModelLocation);

        PastelModelHelper.singletonWithSoup(
            generators, PastelBlocks.CRYSTAL_APOTHECARY, ModelLocationUtils::getModelLocation);

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.TITRATION_BARREL.get())
                                                                .with(
                                                                    PastelModelHelper.createUpDefaultHorizontalFacingVariantMap())
                                                                .with(PropertyDispatch.property(
                                                                                          TitrationBarrelBlock.BARREL_STATE)
                                                                                      .generate(
                                                                                          state -> PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cubeBottomTop(
                                                                                                                      b -> b, "_side",
                                                                                                                      b -> b, "_top_" +
                                                                                                                              state.getSerializedName(),
                                                                                                                      b -> b, "_bottom"
                                                                                                                  )
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.TITRATION_BARREL.get(),
                                                                                                                      state ==
                                                                                                                      TitrationBarrelBlock.BarrelState.EMPTY
                                                                                                                      ? ""
                                                                                                                      :
                                                                                                                      "_" +
                                                                                                                      state.getSerializedName(),
                                                                                                                      generators.modelOutput
                                                                                                                  )))));
    }
}

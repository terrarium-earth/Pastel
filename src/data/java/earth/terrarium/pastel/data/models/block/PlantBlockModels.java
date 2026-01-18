package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.blocks.TallCropBlock;
import earth.terrarium.pastel.blocks.conditional.BloodOrchidBlock;
import earth.terrarium.pastel.blocks.deeper_down.flora.*;
import earth.terrarium.pastel.blocks.jade_vines.*;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTextureMaps;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import earth.terrarium.pastel.registries.client.PastelTextures;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;

public class PlantBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {

        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.SAG_LEAF);
        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.SAG_BUBBLE);
        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.SMALL_SAG_BUBBLE);

        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.GLISTERING_MELON, TexturedModel.COLUMN);


        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.GLISTERING_MELON_STEM);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.GLISTERING_MELON_STEM.get())
                                                                .with(PropertyDispatch.property(
                                                                                          BlockStateProperties.AGE_7)
                                                                                      .generate(
                                                                                          age -> PastelModelHelper.createModelVariant(
                                                                                              ModelTemplates.STEMS[age].create(
                                                                                                  PastelBlocks.GLISTERING_MELON_STEM.get(),
                                                                                                  TextureMapping.stem(
                                                                                                      PastelBlocks.GLISTERING_MELON_STEM.get()),
                                                                                                  generators.modelOutput
                                                                                              )))));
        generators.blockStateOutput.accept(PastelModelHelper.createVariantsSupplier(
                                                                PastelBlocks.ATTACHED_GLISTERING_MELON_STEM.get(),
                                                                ModelTemplates.ATTACHED_STEM.create(
                                                                    PastelBlocks.ATTACHED_GLISTERING_MELON_STEM.get()
                                                                    , TextureMapping.attachedStem(
                                                                        PastelBlocks.ATTACHED_GLISTERING_MELON_STEM.get(),
                                                                        PastelBlocks.ATTACHED_GLISTERING_MELON_STEM.get()
                                                                    ), generators.modelOutput
                                                                )
                                                            )
                                                            .with(
                                                                PastelModelHelper.createWestDefaultHorizontalFacingVariantMap()));


        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
            PastelBlocks.VARIA_SPROUT.get(), PastelModelHelper.createModelVariant(PastelTexturedModels.cross(b -> b, "")
                                                                                                      .createWithSuffix(
                                                                                                          PastelBlocks.VARIA_SPROUT.get(),
                                                                                                          "",
                                                                                                          generators.modelOutput
                                                                                                      )),
            PastelModelHelper.createModelVariant(PastelTexturedModels.cross(b -> b, "_2")
                                                                     .createWithSuffix(
                                                                         PastelBlocks.VARIA_SPROUT.get(), "_2",
                                                                         generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                PastelTexturedModels.cross(b -> b, "_3")
                                    .createWithSuffix(PastelBlocks.VARIA_SPROUT.get(), "_3", generators.modelOutput)),
            PastelModelHelper.createModelVariant(PastelTexturedModels.cross(b -> b, "_4")
                                                                     .createWithSuffix(
                                                                         PastelBlocks.VARIA_SPROUT.get(), "_4",
                                                                         generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                PastelTexturedModels.cross(b -> b, "_5")
                                    .createWithSuffix(PastelBlocks.VARIA_SPROUT.get(), "_5", generators.modelOutput)),
            PastelModelHelper.createModelVariant(PastelTexturedModels.cross(b -> b, "_6")
                                                                     .createWithSuffix(
                                                                         PastelBlocks.VARIA_SPROUT.get(), "_6",
                                                                         generators.modelOutput
                                                                     ))
        ));

        registerSmallDragonjagBlock(generators, PastelBlocks.SMALL_RED_DRAGONJAG);
        registerSmallDragonjagBlock(generators, PastelBlocks.SMALL_YELLOW_DRAGONJAG);
        registerSmallDragonjagBlock(generators, PastelBlocks.SMALL_PINK_DRAGONJAG);
        registerSmallDragonjagBlock(generators, PastelBlocks.SMALL_PURPLE_DRAGONJAG);
        registerSmallDragonjagBlock(generators, PastelBlocks.SMALL_BLACK_DRAGONJAG);

        registerTallDragonjagBlock(generators, PastelBlocks.TALL_YELLOW_DRAGONJAG);
        registerTallDragonjagBlock(generators, PastelBlocks.TALL_RED_DRAGONJAG);
        registerTallDragonjagBlock(generators, PastelBlocks.TALL_PINK_DRAGONJAG);
        registerTallDragonjagBlock(generators, PastelBlocks.TALL_PURPLE_DRAGONJAG);
        registerTallDragonjagBlock(generators, PastelBlocks.TALL_BLACK_DRAGONJAG);









        //Flora
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.ALOE.get())
                                                                .with(PropertyDispatch.property(
                                                                                          BlockStateProperties.AGE_4)
                                                                                      .generate(
                                                                                          age -> PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b,
                                                                                                                      age.toString()
                                                                                                                  )
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.ALOE.get(),
                                                                                                                      age.toString(),
                                                                                                                      generators.modelOutput
                                                                                                                  )))));

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.SAWBLADE_HOLLY_BUSH.get())
                                                                .with(PropertyDispatch.property(
                                                                                          BlockStateProperties.AGE_7)
                                                                                      .select(
                                                                                          0,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b, "0")
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                                                                                                                      "_stage0",
                                                                                                                      generators.modelOutput
                                                                                                                  ))
                                                                                      )
                                                                                      .select(
                                                                                          1,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b, "1")
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                                                                                                                      "_stage1",
                                                                                                                      generators.modelOutput
                                                                                                                  ))
                                                                                      )
                                                                                      .select(
                                                                                          2,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                                                                                              "_stage1"
                                                                                          )
                                                                                      )
                                                                                      .select(
                                                                                          3,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b, "2")
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                                                                                                                      "_stage2",
                                                                                                                      generators.modelOutput
                                                                                                                  ))
                                                                                      )
                                                                                      .select(
                                                                                          4,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                                                                                              "_stage2"
                                                                                          )
                                                                                      )
                                                                                      .select(
                                                                                          5,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                                                                                              "_stage2"
                                                                                          )
                                                                                      )
                                                                                      .select(
                                                                                          6,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                                                                                              "_stage2"
                                                                                          )
                                                                                      )
                                                                                      .select(
                                                                                          7,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b, "3")
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.SAWBLADE_HOLLY_BUSH.get(),
                                                                                                                      "_stage3",
                                                                                                                      generators.modelOutput
                                                                                                                  ))
                                                                                      )));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
            PastelBlocks.BRISTLE_SPROUTS.get(), PastelModelHelper.createModelVariant(
                PastelTexturedModels.cross(b -> b, "_1")
                                    .createWithSuffix(
                                        PastelBlocks.BRISTLE_SPROUTS.get(), "_1", generators.modelOutput)),
            PastelModelHelper.createModelVariant(PastelTexturedModels.cross(b -> b, "_2")
                                                                     .createWithSuffix(
                                                                         PastelBlocks.BRISTLE_SPROUTS.get(), "_2",
                                                                         generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                PastelTexturedModels.cross(b -> b, "_3")
                                    .createWithSuffix(
                                        PastelBlocks.BRISTLE_SPROUTS.get(), "_3", generators.modelOutput)),
            PastelModelHelper.createModelVariant(PastelTexturedModels.cross(b -> b, "_4")
                                                                     .createWithSuffix(
                                                                         PastelBlocks.BRISTLE_SPROUTS.get(), "_4",
                                                                         generators.modelOutput
                                                                     ))
        ));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.DOOMBLOOM.get())
                                                                .with(PropertyDispatch.property(
                                                                                          BlockStateProperties.AGE_4)
                                                                                      .generate(
                                                                                          age -> PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b,
                                                                                                                      age.toString()
                                                                                                                  )
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.DOOMBLOOM.get(),
                                                                                                                      age.toString(),
                                                                                                                      generators.modelOutput
                                                                                                                  )))));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.SNAPPING_IVY.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    SnappingIvyBlock.SNAPPED,
                                                                    ModelLocationUtils.getModelLocation(
                                                                        PastelBlocks.SNAPPING_IVY.get(), "_snapped"),
                                                                    ModelLocationUtils.getModelLocation(
                                                                        PastelBlocks.SNAPPING_IVY.get())
                                                                ))
                                                                .with(PropertyDispatch.property(
                                                                                          BlockStateProperties.HORIZONTAL_AXIS)
                                                                                      .select(
                                                                                          Direction.Axis.X,
                                                                                          net.minecraft.data.models.blockstates.Variant.variant()
                                                                                      )
                                                                                      .select(
                                                                                          Direction.Axis.Z,
                                                                                          net.minecraft.data.models.blockstates.Variant.variant()
                                                                                                                                       .with(
                                                                                                                                           VariantProperties.Y_ROT,
                                                                                                                                           VariantProperties.Rotation.R90
                                                                                                                                       )
                                                                                      )));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.ABYSSAL_VINES.get())
                                                                .with(PropertyDispatch.properties(
                                                                                          TriStateVineBlock.LIFE_STAGE,
                                                                                          AbyssalVineBlock.BERRIES
                                                                                      )
                                                                                      .generate((stage, berries) -> {
                                                                                          String suffix = (stage ==
                                                                                                           TriStateVineBlock.LifeStage.STALK
                                                                                                           ? ""
                                                                                                           : "_tip") +
                                                                                                          (berries
                                                                                                           ? "_fruiting"
                                                                                                           : "");
                                                                                          if (stage ==
                                                                                              TriStateVineBlock.LifeStage.MATURE)
                                                                                              return PastelModelHelper.createModelVariant(
                                                                                                  PastelBlocks.ABYSSAL_VINES.get(),
                                                                                                  suffix
                                                                                              );
                                                                                          return PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b, suffix)
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.ABYSSAL_VINES.get(),
                                                                                                                      suffix,
                                                                                                                      generators.modelOutput
                                                                                                                  ));
                                                                                      })));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.NIGHTDEW.get())
                                                                .with(PropertyDispatch.property(
                                                                                          TriStateVineBlock.LIFE_STAGE)
                                                                                      .generate(stage -> {
                                                                                          String suffix = stage ==
                                                                                                          TriStateVineBlock.LifeStage.STALK
                                                                                                          ? "" : "_tip";
                                                                                          if (stage ==
                                                                                              TriStateVineBlock.LifeStage.MATURE)
                                                                                              return PastelModelHelper.createModelVariant(
                                                                                                  PastelBlocks.NIGHTDEW.get(),
                                                                                                  suffix
                                                                                              );
                                                                                          return PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b, suffix)
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.NIGHTDEW.get(),
                                                                                                                      suffix,
                                                                                                                      generators.modelOutput
                                                                                                                  ));
                                                                                      })));

        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.SWEET_PEA);
        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.APRICOTTI);
        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.HUMMING_BELL);


        List<Variant> variants = new ArrayList<>(PastelModelHelper.createHorizontalRotationVariantList(
            ModelLocationUtils.getModelLocation(PastelBlocks.MOSS_BALL.get(), "_tuft")));
        variants.add(PastelModelHelper.createModelVariant(PastelBlocks.MOSS_BALL.get(), "")
                                      .with(VariantProperties.WEIGHT, 4));
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(PastelBlocks.MOSS_BALL.get(), variants.toArray(Variant[]::new)));
        generators.blockStateOutput.accept(PastelModelHelper.createVariantsSupplier(
            PastelBlocks.GIANT_MOSS_BALL.get(),
            ModelLocationUtils.getModelLocation(PastelBlocks.GIANT_MOSS_BALL.get())
        ));




        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.JADE_VINE_ROOTS.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    JadeVineBulbBlock.DEAD,
                                                                    PastelModels.JADE_VINE_ROOTS.createWithSuffix(
                                                                        PastelBlocks.JADE_VINE_ROOTS.get(), "_dead",
                                                                        PastelTextureMaps.flowerParticle(
                                                                            PastelTextures.JADE_VINE_PLANT_RIPE,
                                                                            PastelTextures.JADE_VINE_PLANT_RIPE_BREAKING
                                                                        ), generators.modelOutput
                                                                    ), PastelModels.JADE_VINE_ROOTS.create(
                                                                        PastelBlocks.JADE_VINE_ROOTS.get(),
                                                                        PastelTextureMaps.flowerParticle(
                                                                            PastelTextures.JADE_VINE_PLANT,
                                                                            PastelTextures.JADE_VINE_PLANT_BREAKING
                                                                        ), generators.modelOutput
                                                                    )
                                                                )));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.JADE_VINE_BULB.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    JadeVineBulbBlock.DEAD,
                                                                    PastelModels.JADE_VINE_BULB.createWithSuffix(
                                                                        PastelBlocks.JADE_VINE_BULB.get(), "_dead",
                                                                        PastelTextureMaps.flowerParticle(
                                                                            PastelTextures.JADE_VINE_PLANT_RIPE_BULB,
                                                                            PastelTextures.JADE_VINE_PLANT_RIPE_BREAKING
                                                                        ), generators.modelOutput
                                                                    ), PastelModels.JADE_VINE_BULB.create(
                                                                        PastelBlocks.JADE_VINE_BULB.get(),
                                                                        PastelTextureMaps.flowerParticle(
                                                                            PastelTextures.JADE_VINE_PLANT_BULB,
                                                                            PastelTextures.JADE_VINE_PLANT_BREAKING
                                                                        ), generators.modelOutput
                                                                    )
                                                                )));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.JADE_VINES.get())
                                                                .with(PropertyDispatch.properties(
                                                                                          BlockStateProperties.AGE_7,
                                                                                          JadeVinePlantBlock.PART)
                                                                                      .generate((age, part) -> {
                                                                                          ModelTemplate model
                                                                                              = PastelModels.jadeVines(
                                                                                              part);
                                                                                          String suffix = "_" +
                                                                                                          part.getSerializedName() +
                                                                                                          (age == 0
                                                                                                           ? "_dead"
                                                                                                           : age <= 2
                                                                                                             ? "_leaves"
                                                                                                             : age <= 6
                                                                                                               ?
                                                                                                               "_petals"
                                                                                                               :
                                                                                                               "_bloom");
                                                                                          if (age == 0)
                                                                                              return PastelModelHelper.createModelVariant(
                                                                                                  model.createWithSuffix(
                                                                                                      PastelBlocks.JADE_VINES.get(),
                                                                                                      suffix,
                                                                                                      PastelTextureMaps.flowerParticle(
                                                                                                          PastelTextures.JADE_VINE_PLANT_RIPE,
                                                                                                          PastelTextures.JADE_VINE_PLANT_RIPE_BREAKING
                                                                                                      ),
                                                                                                      generators.modelOutput
                                                                                                  ));
                                                                                          if (age == 1)
                                                                                              return PastelModelHelper.createModelVariant(
                                                                                                  model.createWithSuffix(
                                                                                                      PastelBlocks.JADE_VINES.get(),
                                                                                                      suffix,
                                                                                                      PastelTextureMaps.flowerParticle(
                                                                                                          PastelTextures.JADE_VINE_PLANT,
                                                                                                          PastelTextures.JADE_VINE_PLANT_BREAKING
                                                                                                      ),
                                                                                                      generators.modelOutput
                                                                                                  ));
                                                                                          if (age == 3)
                                                                                              return PastelModelHelper.createModelVariant(
                                                                                                  model.createWithSuffix(
                                                                                                      PastelBlocks.JADE_VINES.get(),
                                                                                                      suffix,
                                                                                                      PastelTextureMaps.flowerParticle(
                                                                                                          PastelTextures.JADE_VINE_PLANT_PETALS,
                                                                                                          PastelTextures.JADE_VINE_PLANT_BREAKING
                                                                                                      ),
                                                                                                      generators.modelOutput
                                                                                                  ));
                                                                                          if (age == 7)
                                                                                              return PastelModelHelper.createModelVariant(
                                                                                                  model.createWithSuffix(
                                                                                                      PastelBlocks.JADE_VINES.get(),
                                                                                                      suffix,
                                                                                                      PastelTextureMaps.flowerParticle(
                                                                                                          PastelTextures.JADE_VINE_PLANT_BLOOMING,
                                                                                                          PastelTextures.JADE_VINE_PLANT_BREAKING
                                                                                                      ),
                                                                                                      generators.modelOutput
                                                                                                  ));
                                                                                          return PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.JADE_VINES.get(),
                                                                                              suffix
                                                                                          );
                                                                                      })));





        generateNephriteBlossomStemModel(generators);
        generators.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(PastelBlocks.NEPHRITE_BLOSSOM_LEAVES.get())
                                 .with(PropertyDispatch.property(BlockStateProperties.AGE_2)
                                                       .generate(age -> {
                                                           String suffix = age == 0 ? "" : age == 1 ? "_flowering"
                                                                                                    : "_fruiting";
                                                           return PastelModelHelper.createModelVariant(
                                                               PastelTexturedModels.leaves(b -> b, suffix)
                                                                                   .createWithSuffix(
                                                                                       PastelBlocks.NEPHRITE_BLOSSOM_LEAVES.get(),
                                                                                       suffix, generators.modelOutput
                                                                                   ));
                                                       })));
        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.NEPHRITE_BLOSSOM_BULB);




        generateJadeiteLotusStemModel(generators);
        PastelModelHelper.BLOCK.defaultUpFacingGetter(
            generators, PastelBlocks.JADEITE_LOTUS_FLOWER, ModelLocationUtils::getModelLocation);
        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.JADEITE_LOTUS_BULB);
        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.QUITOXIC_REEDS);


        generateMermaidBrushModel(generators);

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.AMARANTH.get())
                                                                .with(PropertyDispatch.properties(
                                                                                          BlockStateProperties.AGE_7,
                                                                                          TallCropBlock.HALF)
                                                                                      .generate((age, half) -> {
                                                                                          String suffix;
                                                                                          if (half ==
                                                                                              DoubleBlockHalf.LOWER) {
                                                                                              suffix = "_stage" +
                                                                                                       (age + 1) / 2 +
                                                                                                       "_lower";
                                                                                              if (age > 0 &&
                                                                                                  age % 2 == 0)
                                                                                                  return PastelModelHelper.createModelVariant(
                                                                                                      PastelBlocks.AMARANTH.get(),
                                                                                                      suffix
                                                                                                  );
                                                                                          } else {
                                                                                              suffix = "_stage" +
                                                                                                       Math.max(
                                                                                                           2, (age +
                                                                                                               1) / 2
                                                                                                       ) + "_upper";
                                                                                              if (age < 4 || age == 6)
                                                                                                  return PastelModelHelper.createModelVariant(
                                                                                                      PastelBlocks.AMARANTH.get(),
                                                                                                      suffix
                                                                                                  );
                                                                                          }
                                                                                          return PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b, suffix)
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.AMARANTH.get(),
                                                                                                                      suffix,
                                                                                                                      generators.modelOutput
                                                                                                                  ));
                                                                                      })));



        PastelModelHelper.BLOCK.singletonWithSoup(generators, PastelBlocks.CLOVER, ModelLocationUtils::getModelLocation);
        PastelModelHelper.BLOCK.singletonWithSoup(
            generators, PastelBlocks.FOUR_LEAF_CLOVER, ModelLocationUtils::getModelLocation);

        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.AMARANTH_BUSHEL);
        PastelModelHelper.BLOCK.pottedPlant(generators, PastelBlocks.POTTED_AMARANTH_BUSHEL, false);

        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.RESONANT_LILY);
        PastelModelHelper.BLOCK.pottedPlant(generators, PastelBlocks.POTTED_RESONANT_LILY, false);



        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.BLOOD_ORCHID.get())
                                                                .with(PropertyDispatch.property(BloodOrchidBlock.AGE)
                                                                                      .generate(
                                                                                          stage -> PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cross(
                                                                                                                      b -> b,
                                                                                                                      stage.toString()
                                                                                                                  )
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.BLOOD_ORCHID.get(),
                                                                                                                      stage.toString(),
                                                                                                                      generators.modelOutput
                                                                                                                  )))));
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.POTTED_BLOOD_ORCHID,
            PastelTexturedModels.flowerPotCross(b -> PastelBlocks.BLOOD_ORCHID.get(), "5", false)
        );
    }

    public static void generateJadeiteLotusStemModel(BlockModelGenerators generators) {
        ResourceLocation bottom = PastelTexturedModels.cross(b -> b, "_bottom")
                                                      .createWithSuffix(
                                                          PastelBlocks.JADEITE_LOTUS_STEM.get(), "_bottom",
                                                          generators.modelOutput
                                                      );
        ResourceLocation top = PastelTexturedModels.cross(b -> b, "_top")
                                                   .createWithSuffix(
                                                       PastelBlocks.JADEITE_LOTUS_STEM.get(), "_top",
                                                       generators.modelOutput
                                                   );
        ResourceLocation base = ModelLocationUtils.getModelLocation(PastelBlocks.JADEITE_LOTUS_STEM.get(), "_base");
        generators.blockStateOutput.accept(MultiPartGenerator.multiPart(PastelBlocks.JADEITE_LOTUS_STEM.get())
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              JadeiteLotusStemBlock.STEM_PART,
                                                                              StemComponent.STEM
                                                                          ),
                                                                 PastelModelHelper.createModelVariant(bottom)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              JadeiteLotusStemBlock.STEM_PART,
                                                                              StemComponent.STEMALT
                                                                          ), PastelModelHelper.createModelVariant(top)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              JadeiteLotusStemBlock.STEM_PART,
                                                                              StemComponent.BASE
                                                                          ),
                                                                 PastelModelHelper.createModelVariant(bottom)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              JadeiteLotusStemBlock.STEM_PART,
                                                                              StemComponent.BASE
                                                                          )
                                                                          .term(BlockStateProperties.INVERTED, false),
                                                                 PastelModelHelper.createModelVariant(base)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              JadeiteLotusStemBlock.STEM_PART,
                                                                              StemComponent.BASE
                                                                          )
                                                                          .term(BlockStateProperties.INVERTED, true),
                                                                 PastelModelHelper.createModelVariant(base)
                                                                                  .with(
                                                                                      VariantProperties.X_ROT,
                                                                                      VariantProperties.Rotation.R180
                                                                                  )
                                                             ));
    }

    public static void generateNephriteBlossomStemModel(BlockModelGenerators generators) {
        ResourceLocation bottom = PastelTexturedModels.cross(b -> b, "_bottom")
                                                      .createWithSuffix(
                                                          PastelBlocks.NEPHRITE_BLOSSOM_STEM.get(), "_bottom",
                                                          generators.modelOutput
                                                      );
        ResourceLocation top = PastelTexturedModels.cross(b -> b, "_top")
                                                   .createWithSuffix(
                                                       PastelBlocks.NEPHRITE_BLOSSOM_STEM.get(), "_top",
                                                       generators.modelOutput
                                                   );
        ResourceLocation fronds = ModelLocationUtils.getModelLocation(
            PastelBlocks.NEPHRITE_BLOSSOM_STEM.get(), "_base");
        generators.blockStateOutput.accept(MultiPartGenerator.multiPart(PastelBlocks.NEPHRITE_BLOSSOM_STEM.get())
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              NephriteBlossomStemBlock.STEM_PART,
                                                                              StemComponent.STEM
                                                                          ),
                                                                 PastelModelHelper.createModelVariant(bottom)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              NephriteBlossomStemBlock.STEM_PART,
                                                                              StemComponent.STEMALT
                                                                          ), PastelModelHelper.createModelVariant(top)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              NephriteBlossomStemBlock.STEM_PART,
                                                                              StemComponent.BASE
                                                                          ),
                                                                 PastelModelHelper.createModelVariant(fronds)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              NephriteBlossomStemBlock.STEM_PART,
                                                                              StemComponent.BASE
                                                                          ),
                                                                 PastelModelHelper.createModelVariant(bottom)
                                                             ));
    }

    public static void generateMermaidBrushModel(BlockModelGenerators generators) {
        ResourceLocation none = PastelTexturedModels.cross(b -> b, "_none")
                                                    .createWithSuffix(
                                                        PastelBlocks.MERMAIDS_BRUSH.get(), "_none",
                                                        generators.modelOutput
                                                    );
        ResourceLocation some = PastelTexturedModels.cross(b -> b, "_some")
                                                    .createWithSuffix(
                                                        PastelBlocks.MERMAIDS_BRUSH.get(), "_some",
                                                        generators.modelOutput
                                                    );
        ResourceLocation full = PastelTexturedModels.cross(b -> b, "_full")
                                                    .createWithSuffix(
                                                        PastelBlocks.MERMAIDS_BRUSH.get(), "_full",
                                                        generators.modelOutput
                                                    );
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.MERMAIDS_BRUSH.get())
                                                                .with(PropertyDispatch.property(
                                                                                          BlockStateProperties.AGE_7)
                                                                                      .generate(
                                                                                          age -> PastelModelHelper.createModelVariant(
                                                                                              age < 3 ? none
                                                                                                      : age < 6 ? some
                                                                                                                :
                                                                                                        full))));
    }

    public static void registerSmallDragonjagBlock(BlockModelGenerators generators, DeferredBlock<Block> block) {
        PastelModelHelper.BLOCK.singleton(generators, block, PastelTexturedModels.doubleCross(b -> b, ""));
    }

    public static void registerTallDragonjagBlock(BlockModelGenerators generators, DeferredBlock<Block> block) {
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(PropertyDispatch.properties(
                                                                                          DoublePlantBlock.HALF,
                                                                                          TallDragonjagBlock.DEAD)
                                                                                      .generate((half, dead) -> {
                                                                                          String suffix = (half ==
                                                                                                           DoubleBlockHalf.UPPER
                                                                                                           ? "_top"
                                                                                                           : "_bottom"
                                                                                                          ) +
                                                                                                          (dead
                                                                                                           ? "_dead"
                                                                                                           : "");
                                                                                          return PastelModelHelper.createModelVariant(
                                                                                              (half ==
                                                                                               DoubleBlockHalf.UPPER
                                                                                               ?
                                                                                               PastelTexturedModels.cross(
                                                                                                  b -> b, suffix)
                                                                                               :
                                                                                               PastelTexturedModels.doubleCross(
                                                                                                   b -> b,
                                                                                                   suffix
                                                                                               )).createWithSuffix(
                                                                                                  block.get(), suffix,
                                                                                                  generators.modelOutput
                                                                                              ));
                                                                                      })));
    }

    public static void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.SMALL_RED_DRAGONJAG.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.SMALL_YELLOW_DRAGONJAG.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.SMALL_PINK_DRAGONJAG.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.SMALL_PURPLE_DRAGONJAG.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.SMALL_BLACK_DRAGONJAG.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.VARIA_SPROUT.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.BRISTLE_SPROUTS.get(), "_1");
        PastelModelHelper.BLOCK.simplePlant(generators, PastelBlocks.SWEET_PEA);
        PastelModelHelper.BLOCK.simplePlant(generators, PastelBlocks.APRICOTTI);
        PastelModelHelper.BLOCK.simplePlant(generators, PastelBlocks.HUMMING_BELL);
        PastelModelHelper.registerBlockTexturedItemModel(
            generators, PastelBlocks.NEPHRITE_BLOSSOM_STEM.get(), "_bottom");
        PastelModelHelper.registerItemModel(generators, PastelBlocks.NEPHRITE_BLOSSOM_BULB.asItem());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.JADEITE_LOTUS_STEM.get(), "_top");
        PastelModelHelper.registerItemModel(generators, PastelBlocks.JADEITE_LOTUS_BULB.asItem());
        PastelModelHelper.registerItemModel(generators, PastelBlocks.QUITOXIC_REEDS.asItem());
        PastelModelHelper.registerItemModel(generators, PastelBlocks.CLOVER.asItem());
        PastelModelHelper.registerItemModel(generators, PastelBlocks.FOUR_LEAF_CLOVER.asItem());
        PastelModelHelper.registerItemModel(generators, PastelBlocks.AMARANTH_BUSHEL.asItem());
        PastelModelHelper.BLOCK.simplePlant(generators, PastelBlocks.RESONANT_LILY);
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.BLOOD_ORCHID.get(), "5");
    }
}

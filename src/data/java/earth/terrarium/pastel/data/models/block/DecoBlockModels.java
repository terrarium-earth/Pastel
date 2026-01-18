package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.decoration.*;
import earth.terrarium.pastel.blocks.deeper_down.HummingstoneBlock;
import earth.terrarium.pastel.blocks.deeper_down.groundcover.AshBlock;
import earth.terrarium.pastel.blocks.deeper_down.groundcover.AshPileBlock;
import earth.terrarium.pastel.blocks.item_bowl.ItemBowlBlock;
import earth.terrarium.pastel.blocks.item_roundel.ItemRoundelBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeVinePetalBlock;
import earth.terrarium.pastel.blocks.shooting_star.ShootingStar;
import earth.terrarium.pastel.blocks.shooting_star.ShootingStarBlock;
import earth.terrarium.pastel.blocks.shooting_star.ShootingStarItem;
import earth.terrarium.pastel.blocks.statues.GrotesqueBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.data.models.PastelBlockModels;
import earth.terrarium.pastel.data.models.block.deco.BalciteBlockModels;
import earth.terrarium.pastel.data.models.block.deco.ColoredBlockModels;
import earth.terrarium.pastel.data.models.block.deco.StoneLikeBlockModels;
import earth.terrarium.pastel.data.models.block.deco.WoodLikeBlockModels;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.registries.PastelBlockSoundGroups;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTextureMaps;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import earth.terrarium.pastel.registries.client.PastelTextures;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.*;
import static net.minecraft.world.level.block.Blocks.AMETHYST_BLOCK;

public class DecoBlockModels {

    public static void registerShimmerstoneLight(BlockModelGenerators generators, DeferredBlock<Block> block,
                                                 Supplier<ResourceLocation> outerSupplier) {
            ResourceLocation outer = outerSupplier.get();
            ResourceLocation base = PastelModels.SHIMMERSTONE_LIGHT.create(block.get(), PastelTextureMaps.innerOuterParticle(PastelTextures.SHIMMERSTONE_LIGHT, outer, outer), generators.modelOutput);
            ResourceLocation mirrored = PastelModels.SHIMMERSTONE_LIGHT_MIRRORED.createWithSuffix(block.get(), "_mirrored", PastelTextureMaps.innerOuterParticle(PastelTextures.SHIMMERSTONE_LIGHT, outer, outer), generators.modelOutput);
            generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get()).with(PastelModelHelper.createNorthDefaultFacingVariantMap()).with(PastelModelHelper.createBooleanModelMap(BlockStateProperties.INVERTED, mirrored, base)));
    }

    public static void glassPane(BlockModelGenerators generators, DeferredBlock<Block> block,DeferredBlock<Block> glassBlock) {
        PastelModelHelper.translucent(block);
        generators.blockStateOutput.accept(PastelModelHelper.glassPaneBlockModel(generators, block.get(), glassBlock.get()));
    }

    public static void generateBlockModels(BlockModelGenerators generators){
        BalciteBlockModels.generateBlockModels(generators);
        ColoredBlockModels.generateBlockModels(generators);
        StoneLikeBlockModels.generateBlockModels(generators);
        WoodLikeBlockModels.generateBlockModels(generators);

        PastelModelHelper.cutout(PastelBlocks.ITEM_BOWL_BASALT);
        PastelModelHelper.singleton(generators,PastelBlocks.ITEM_BOWL_BASALT,PastelTexturedModels.BOWL);
        PastelModelHelper.cutout(PastelBlocks.ITEM_BOWL_CALCITE);
        PastelModelHelper.singleton(generators,PastelBlocks.ITEM_BOWL_CALCITE,PastelTexturedModels.BOWL);
        PastelModelHelper.singleton(generators,PastelBlocks.ITEM_ROUNDEL,PastelTexturedModels.ROUNDEL);

        PastelModelHelper.cutout(PastelBlocks.PRIMORDIAL_WALL_TORCH);
        PastelModelHelper.cutout(PastelBlocks.PRIMORDIAL_TORCH);
        PastelModelHelper.defaultEastHorizontalFacing(generators,PastelBlocks.PRIMORDIAL_WALL_TORCH,ModelLocationUtils::getModelLocation);
        PastelModelHelper.singletonWithSoup(generators,PastelBlocks.PRIMORDIAL_TORCH,ModelLocationUtils::getModelLocation);
        
        PastelModelHelper.translucent(PastelBlocks.TOPAZ_GLASS);
        PastelModelHelper.translucent(PastelBlocks.AMETHYST_GLASS);
        PastelModelHelper.translucent(PastelBlocks.CITRINE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.ONYX_GLASS);
        PastelModelHelper.translucent(PastelBlocks.MOONSTONE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.RADIANT_GLASS);
        PastelModelHelper.translucent(PastelBlocks.TOPAZ_GLASS_PANE);
        PastelModelHelper.translucent(PastelBlocks.AMETHYST_GLASS_PANE);
        PastelModelHelper.translucent(PastelBlocks.CITRINE_GLASS_PANE);
        PastelModelHelper.translucent(PastelBlocks.ONYX_GLASS_PANE);
        PastelModelHelper.translucent(PastelBlocks.MOONSTONE_GLASS_PANE);
        PastelModelHelper.translucent(PastelBlocks.RADIANT_GLASS_PANE);
        PastelModelHelper.translucent(PastelBlocks.TOPAZ_CHIME);
        PastelModelHelper.translucent(PastelBlocks.AMETHYST_CHIME);
        PastelModelHelper.translucent(PastelBlocks.CITRINE_CHIME);
        PastelModelHelper.translucent(PastelBlocks.ONYX_CHIME);
        PastelModelHelper.translucent(PastelBlocks.MOONSTONE_CHIME);
        PastelModelHelper.translucent(PastelBlocks.SEMI_PERMEABLE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.TOPAZ_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.AMETHYST_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.CITRINE_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.ONYX_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS);
        
        PastelModelHelper.simple(generators,PastelBlocks.TOPAZ_GLASS);
        PastelModelHelper.simple(generators,PastelBlocks.AMETHYST_GLASS);
        PastelModelHelper.simple(generators,PastelBlocks.CITRINE_GLASS);
        PastelModelHelper.simple(generators,PastelBlocks.ONYX_GLASS);
        PastelModelHelper.simple(generators,PastelBlocks.MOONSTONE_GLASS);
        PastelModelHelper.simple(generators,PastelBlocks.RADIANT_GLASS);
        glassPane(generators,PastelBlocks.TOPAZ_GLASS_PANE,PastelBlocks.TOPAZ_GLASS);
        glassPane(generators,PastelBlocks.AMETHYST_GLASS_PANE,PastelBlocks.AMETHYST_GLASS);
        glassPane(generators,PastelBlocks.CITRINE_GLASS_PANE,PastelBlocks.CITRINE_GLASS);
        glassPane(generators,PastelBlocks.ONYX_GLASS_PANE,PastelBlocks.ONYX_GLASS);
        glassPane(generators,PastelBlocks.MOONSTONE_GLASS_PANE,PastelBlocks.MOONSTONE_GLASS);
        glassPane(generators,PastelBlocks.RADIANT_GLASS_PANE,PastelBlocks.RADIANT_GLASS);
        PastelModelHelper.singleton(generators,PastelBlocks.TOPAZ_CHIME,PastelTexturedModels.CHIME);
        PastelModelHelper.singleton(generators,PastelBlocks.AMETHYST_CHIME,PastelTexturedModels.CHIME);
        PastelModelHelper.singleton(generators,PastelBlocks.CITRINE_CHIME,PastelTexturedModels.CHIME);
        PastelModelHelper.singleton(generators,PastelBlocks.ONYX_CHIME,PastelTexturedModels.CHIME);
        PastelModelHelper.singleton(generators,PastelBlocks.MOONSTONE_CHIME,PastelTexturedModels.CHIME);
        PastelModelHelper.pylon(generators,PastelBlocks.TOPAZ_PYLON);
        PastelModelHelper.pylon(generators,PastelBlocks.AMETHYST_PYLON);
        PastelModelHelper.pylon(generators,PastelBlocks.CITRINE_PYLON);
        PastelModelHelper.pylon(generators,PastelBlocks.ONYX_PYLON);
        PastelModelHelper.pylon(generators,PastelBlocks.MOONSTONE_PYLON);
        
        PastelModelHelper.parented(generators,PastelBlocks.SEMI_PERMEABLE_GLASS.get(),GLASS);
        PastelModelHelper.parented(generators,PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS.get(),TINTED_GLASS);
        PastelModelHelper.parented(generators,PastelBlocks.TOPAZ_SEMI_PERMEABLE_GLASS.get(),PastelBlocks.TOPAZ_GLASS.get());
        PastelModelHelper.parented(generators,PastelBlocks.AMETHYST_SEMI_PERMEABLE_GLASS.get(),PastelBlocks.AMETHYST_GLASS.get());
        PastelModelHelper.parented(generators,PastelBlocks.CITRINE_SEMI_PERMEABLE_GLASS.get(),PastelBlocks.CITRINE_GLASS.get());
        PastelModelHelper.parented(generators,PastelBlocks.ONYX_SEMI_PERMEABLE_GLASS.get(),PastelBlocks.ONYX_GLASS.get());
        PastelModelHelper.parented(generators,PastelBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS.get(),PastelBlocks.MOONSTONE_GLASS.get());
        PastelModelHelper.parented(generators,PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS.get(),PastelBlocks.RADIANT_GLASS.get());

        PastelModelHelper.translucent(PastelBlocks.HUMMINGSTONE_GLASS);
        PastelModelHelper.translucent(PastelBlocks.HUMMINGSTONE);
        PastelModelHelper.translucent(PastelBlocks.WAXED_HUMMINGSTONE);

        PastelModelHelper.simple(generators,PastelBlocks.HUMMINGSTONE_GLASS);
        glassPane(generators,PastelBlocks.HUMMINGSTONE_GLASS_PANE,PastelBlocks.HUMMINGSTONE_GLASS);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.HUMMINGSTONE.get()).with(PastelModelHelper.createBooleanModelMap(HummingstoneBlock.HUMMING, PastelTexturedModels.cubeAll(b -> b, "_humming").createWithSuffix(PastelBlocks.HUMMINGSTONE.get(), "_humming", generators.modelOutput), TexturedModel.CUBE.create(PastelBlocks.HUMMINGSTONE.get(), generators.modelOutput))));
        PastelModelHelper.parented(generators,PastelBlocks.WAXED_HUMMINGSTONE.get(),PastelBlocks.HUMMINGSTONE.get());

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.ASH.get(), PastelModelHelper.createModelVariant(PastelTexturedModels.cubeAll(b -> b, "").createWithSuffix(PastelBlocks.ASH.get(), "", generators.modelOutput)), PastelModelHelper.createModelVariant(PastelTexturedModels.cubeAll(b -> b, "2").createWithSuffix(PastelBlocks.ASH.get(), "2", generators.modelOutput)), PastelModelHelper.createModelVariant(PastelTexturedModels.cubeAll(b -> b, "3").createWithSuffix(PastelBlocks.ASH.get(), "3", generators.modelOutput)), PastelModelHelper.createModelVariant(PastelTexturedModels.cubeAll(b -> b, "4").createWithSuffix(PastelBlocks.ASH.get(), "4", generators.modelOutput))));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.ASH_PILE.get()).with(
            PropertyDispatch.property(BlockStateProperties.LAYERS).generateList(height -> {
                ResourceLocation ash = TextureMapping.getBlockTexture(PastelBlocks.ASH.get());
                ResourceLocation ash2 = TextureMapping.getBlockTexture(PastelBlocks.ASH.get(), "2");
                ResourceLocation ash3 = TextureMapping.getBlockTexture(PastelBlocks.ASH.get(), "3");
                ResourceLocation ash4 = TextureMapping.getBlockTexture(PastelBlocks.ASH.get(), "4");
                if (height == 8) return List.of(PastelModelHelper.createModelVariant(ash), PastelModelHelper.createModelVariant(ash2), PastelModelHelper.createModelVariant(ash3), PastelModelHelper.createModelVariant(ash4));
                ModelTemplate layerModel = new ModelTemplate(
                    Optional.of(ModelLocationUtils.getModelLocation(SNOW, "_height" + height * 2)), Optional.empty(), TextureSlot.PARTICLE, TextureSlot.TEXTURE);
                return List.of(
                    PastelModelHelper.createModelVariant(layerModel.create(
                        PastelCommon.locate("block/ash_pile_height" + height * 2), TextureMapping.cube(ash), generators.modelOutput)),
                    PastelModelHelper.createModelVariant(layerModel.create(PastelCommon.locate("block/ash2_pile_height" + height * 2), TextureMapping.cube(ash2), generators.modelOutput)),
                    PastelModelHelper.createModelVariant(layerModel.create(PastelCommon.locate("block/ash3_pile_height" + height * 2), TextureMapping.cube(ash3), generators.modelOutput)),
                    PastelModelHelper.createModelVariant(layerModel.create(PastelCommon.locate("block/ash4_pile_height" + height * 2), TextureMapping.cube(ash4), generators.modelOutput))
                );
            })));

        PastelModelHelper.cutout(PastelBlocks.LONGING_CHIMERA);
        PastelModelHelper.defaultNorthHorizontalFacing(generators,PastelBlocks.LONGING_CHIMERA,ModelLocationUtils::getModelLocation);
        PastelModelHelper.defaultUpFacing(generators,PastelBlocks.RESPLENDENT_BLOCK,TexturedModel.CUBE_TOP_BOTTOM);
        PastelModelHelper.singleton(generators,PastelBlocks.RESPLENDENT_CUSHION,PastelTexturedModels.CUSHION);
        PastelModelHelper.singleton(generators,PastelBlocks.RESPLENDENT_CARPET,TexturedModel.CARPET);
        PastelModelHelper.cutout(PastelBlocks.RESPLENDENT_BED);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.RESPLENDENT_BED.get()).with(PastelModelHelper.createSouthDefaultHorizontalFacingVariantMap()).with(PropertyDispatch.property(BedBlock.PART).select(
            BedPart.HEAD, PastelModelHelper.createModelVariant(PastelBlocks.RESPLENDENT_BED.get(), "_head")).select(BedPart.FOOT, PastelModelHelper.createModelVariant(PastelBlocks.RESPLENDENT_BED.get(), "_foot"))));
        PastelModelHelper.cutout(PastelBlocks.JADE_PETAL_BLOCK);
        PastelModelHelper.cutout(PastelBlocks.JADE_PETAL_CARPET);
        PastelModelHelper.cutout(PastelBlocks.JADEITE_PETAL_BLOCK);
        PastelModelHelper.cutout(PastelBlocks.JADEITE_PETAL_CARPET);
        PastelModelHelper.simple(generators,PastelBlocks.JADE_PETAL_BLOCK,PastelBlocks.JADEITE_PETAL_BLOCK);
        PastelModelHelper.singleton(generators,PastelBlocks.JADE_PETAL_CARPET,PastelTexturedModels.carpet(b -> PastelBlocks.JADE_PETAL_BLOCK.get(), ""));
        PastelModelHelper.singleton(generators,PastelBlocks.JADEITE_PETAL_CARPET,PastelTexturedModels.carpet(b -> PastelBlocks.JADEITE_PETAL_BLOCK.get(), ""));

        registerShimmerstoneLight(generators,PastelBlocks.STONE_SHIMMERSTONE_LIGHT,()->PastelTextures.STONE_FLAT_LIGHT);
        registerShimmerstoneLight(generators,PastelBlocks.BASALT_SHIMMERSTONE_LIGHT,()->PastelTextures.BASALT_FLAT_LIGHT);
        registerShimmerstoneLight(generators,PastelBlocks.CALCITE_SHIMMERSTONE_LIGHT,()->PastelTextures.CALCITE_FLAT_LIGHT);
        registerShimmerstoneLight(generators,PastelBlocks.DEEPSLATE_SHIMMERSTONE_LIGHT,()->PastelTextures.DEEPSLATE_FLAT_LIGHT);
        registerShimmerstoneLight(generators,PastelBlocks.BLACKSLAG_SHIMMERSTONE_LIGHT,()->PastelTextures.BLACKSLAG_FLAT_LIGHT);
        registerShimmerstoneLight(generators,PastelBlocks.GRANITE_SHIMMERSTONE_LIGHT,()->ModelLocationUtils.getModelLocation(POLISHED_GRANITE));
        registerShimmerstoneLight(generators,PastelBlocks.DIORITE_SHIMMERSTONE_LIGHT,()->ModelLocationUtils.getModelLocation(POLISHED_DIORITE));
        registerShimmerstoneLight(generators,PastelBlocks.ANDESITE_SHIMMERSTONE_LIGHT,()->ModelLocationUtils.getModelLocation(POLISHED_ANDESITE));

        PastelModelHelper.cutout(PastelBlocks.GLISTERING_SHOOTING_STAR);
        PastelModelHelper.cutout(PastelBlocks.FIERY_SHOOTING_STAR);
        PastelModelHelper.cutout(PastelBlocks.COLORFUL_SHOOTING_STAR);
        PastelModelHelper.cutout(PastelBlocks.PRISTINE_SHOOTING_STAR);
        PastelModelHelper.cutout(PastelBlocks.GEMSTONE_SHOOTING_STAR);
        PastelModelHelper.singleton(generators,PastelBlocks.GLISTERING_SHOOTING_STAR,PastelTexturedModels.SHOOTING_STAR);
        PastelModelHelper.singleton(generators,PastelBlocks.FIERY_SHOOTING_STAR,PastelTexturedModels.SHOOTING_STAR);
        PastelModelHelper.singleton(generators,PastelBlocks.COLORFUL_SHOOTING_STAR,PastelTexturedModels.SHOOTING_STAR);
        PastelModelHelper.singleton(generators,PastelBlocks.PRISTINE_SHOOTING_STAR,PastelTexturedModels.SHOOTING_STAR);
        PastelModelHelper.singleton(generators,PastelBlocks.GEMSTONE_SHOOTING_STAR,PastelTexturedModels.SHOOTING_STAR);

        for(var head:PastelBlocks.MOB_HEADS.values()){
            generators.blockStateOutput.accept(PastelModelHelper.createVariantsSupplier(head.get(), PastelModels.MOB_HEAD));
        }
        for(var head:PastelBlocks.WALL_HEADS.values()){
            generators.blockStateOutput.accept(PastelModelHelper.createVariantsSupplier(head.get(), PastelModels.MOB_HEAD));
        }
    }
    public static void generateItemModels(ItemModelGenerators generators){
        BalciteBlockModels.generateItemModels(generators);
        ColoredBlockModels.generateItemModels(generators);
        StoneLikeBlockModels.generateItemModels(generators);
        WoodLikeBlockModels.generateItemModels(generators);

        PastelModelHelper.registerItemModel(generators,PastelBlocks.PRIMORDIAL_TORCH.asItem());

        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.SEMI_PERMEABLE_GLASS,GLASS);
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS,TINTED_GLASS);
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.TOPAZ_SEMI_PERMEABLE_GLASS,PastelBlocks.TOPAZ_GLASS.get());
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.AMETHYST_SEMI_PERMEABLE_GLASS,PastelBlocks.AMETHYST_GLASS.get());
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.CITRINE_SEMI_PERMEABLE_GLASS,PastelBlocks.CITRINE_GLASS.get());
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.ONYX_SEMI_PERMEABLE_GLASS,PastelBlocks.ONYX_GLASS.get());
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS,PastelBlocks.MOONSTONE_GLASS.get());
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS,PastelBlocks.RADIANT_GLASS.get());
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.WAXED_HUMMINGSTONE,PastelBlocks.HUMMINGSTONE.get());

        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.ASH_PILE.get(), PastelBlocks.ASH_PILE.get(), "_height2");

        for(var head:PastelBlocks.MOB_HEADS.values()){
            PastelModelHelper.registerParentedItemModel(generators,head,PastelModels.SKULL_ITEM);
        }
    }
}

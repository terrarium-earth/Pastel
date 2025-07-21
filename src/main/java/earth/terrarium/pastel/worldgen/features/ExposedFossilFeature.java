package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;
import java.util.Objects;

/**
 * Similar to FossilFeature, but does not bury itself into the ground
 */
public class ExposedFossilFeature extends Feature<FossilFeatureConfiguration> {

    public ExposedFossilFeature(Codec<FossilFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    @SuppressWarnings("resource")
    public boolean place(FeaturePlaceContext<FossilFeatureConfiguration> context) {
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos origin = context.origin();
        Rotation blockRotation = Rotation.getRandom(random);
        FossilFeatureConfiguration fossilFeatureConfig = context.config();
        int fossilStructuresCount = random.nextInt(fossilFeatureConfig.fossilStructures.size());
        StructureTemplateManager structureTemplateManager = structureWorldAccess.getLevel()
                                                                                .getServer()
                                                                                .getStructureManager();
        StructureTemplate structureTemplate = structureTemplateManager.getOrCreate(
            fossilFeatureConfig.fossilStructures.get(fossilStructuresCount));
        StructureTemplate structureTemplate2 = structureTemplateManager.getOrCreate(
            fossilFeatureConfig.overlayStructures.get(fossilStructuresCount));
        ChunkPos originChunkPos = new ChunkPos(origin);
        BoundingBox blockBox = new BoundingBox(
            originChunkPos.getMinBlockX() - 16, structureWorldAccess.getMinBuildHeight(),
            originChunkPos.getMinBlockZ() - 16, originChunkPos.getMaxBlockX() + 16,
            structureWorldAccess.getMaxBuildHeight(), originChunkPos.getMaxBlockZ() + 16
        );
        StructurePlaceSettings structurePlacementData = (new StructurePlaceSettings()).setRotation(blockRotation)
                                                                                      .setBoundingBox(blockBox)
                                                                                      .setRandom(random);
        Vec3i rotatedSize = structureTemplate.getSize(blockRotation);
        BlockPos afterOffsetPos = origin.offset(-rotatedSize.getX() / 2, 0, -rotatedSize.getZ() / 2);

        BlockPos transformedPos = structureTemplate.getZeroPositionWithTransform(
            afterOffsetPos, Mirror.NONE, blockRotation);
        if (getEmptyCorners(
            structureWorldAccess, structureTemplate.getBoundingBox(structurePlacementData, transformedPos)) >
            fossilFeatureConfig.maxEmptyCornersAllowed) {
            return false;
        } else {
            structurePlacementData.clearProcessors();
            List<StructureProcessor> processors = (fossilFeatureConfig.fossilProcessors.value()).list();
            Objects.requireNonNull(structurePlacementData);
            processors.forEach(structurePlacementData::addProcessor);
            structureTemplate.placeInWorld(
                structureWorldAccess, transformedPos, transformedPos, structurePlacementData, random, 4);
            structurePlacementData.clearProcessors();
            processors = (fossilFeatureConfig.overlayProcessors.value()).list();
            Objects.requireNonNull(structurePlacementData);
            processors.forEach(structurePlacementData::addProcessor);
            structureTemplate2.placeInWorld(
                structureWorldAccess, transformedPos, transformedPos, structurePlacementData, random, 4);
            return true;
        }
    }

    private static int getEmptyCorners(WorldGenLevel world, BoundingBox box) {
        MutableInt mutableInt = new MutableInt(0);
        box.forAllCorners((pos) -> {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isAir() || blockState.getBlock() instanceof LiquidBlock) {
                mutableInt.add(1);
            }

        });
        return mutableInt.getValue();
    }

}

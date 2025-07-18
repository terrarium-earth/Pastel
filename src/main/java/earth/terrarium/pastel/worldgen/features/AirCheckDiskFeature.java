package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class AirCheckDiskFeature extends OreFeature {

    public AirCheckDiskFeature(Codec<OreConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();

        if (structureWorldAccess.getBlockState(blockPos)
                                .isAir()) {
            return false;
        }

        return super.place(context);
    }

}

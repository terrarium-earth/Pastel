package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

/*
 * Place a single blockstate, always exposed to air
 */
public class AirExposedStateFeature extends Feature<BlockStateFeatureConfig> {

    public AirExposedStateFeature(Codec<BlockStateFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateFeatureConfig> context) {
        var pos = context.origin();
        if (!context.level()
                    .ensureCanWrite(pos) || !isAdjacentToAir(context.level()::getBlockState, pos)) return false;
        context.level()
               .setBlock(
                   pos, context.config()
                               .blockState(), 2
               );
        return true;

    }
}

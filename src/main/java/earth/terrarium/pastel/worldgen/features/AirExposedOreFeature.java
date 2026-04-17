package earth.terrarium.pastel.worldgen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.*;

public class AirExposedOreFeature extends OreFeature {
    public AirExposedOreFeature(Codec<OreConfiguration> codec) {
        super(codec);
    }

    protected boolean doPlace(
        WorldGenLevel level,
        RandomSource random,
        OreConfiguration config,
        double minX,
        double maxX,
        double minZ,
        double maxZ,
        double minY,
        double maxY,
        int x,
        int y,
        int z,
        int width,
        int height
    ) {
        int $$14 = 0;
        BitSet $$15 = new BitSet(width * height * width);
        int $$17 = config.size;
        double[] $$18 = new double[$$17 * 4];

        for (int $$19 = 0; $$19 < $$17; $$19++) {
            float $$20 = (float)$$19 / (float)$$17;
            double $$21 = Mth.lerp((double)$$20, minX, maxX);
            double $$22 = Mth.lerp((double)$$20, minY, maxY);
            double $$23 = Mth.lerp((double)$$20, minZ, maxZ);
            double $$24 = random.nextDouble() * (double)$$17 / 16.0;
            double $$25 = ((double)(Mth.sin((float) Math.PI * $$20) + 1.0F) * $$24 + 1.0) / 2.0;
            $$18[$$19 * 4 + 0] = $$21;
            $$18[$$19 * 4 + 1] = $$22;
            $$18[$$19 * 4 + 2] = $$23;
            $$18[$$19 * 4 + 3] = $$25;
        }

        for (int $$26 = 0; $$26 < $$17 - 1; $$26++) {
            if (!($$18[$$26 * 4 + 3] <= 0.0)) {
                for (int $$27 = $$26 + 1; $$27 < $$17; $$27++) {
                    if (!($$18[$$27 * 4 + 3] <= 0.0)) {
                        double $$28 = $$18[$$26 * 4 + 0] - $$18[$$27 * 4 + 0];
                        double $$29 = $$18[$$26 * 4 + 1] - $$18[$$27 * 4 + 1];
                        double $$30 = $$18[$$26 * 4 + 2] - $$18[$$27 * 4 + 2];
                        double $$31 = $$18[$$26 * 4 + 3] - $$18[$$27 * 4 + 3];
                        if ($$31 * $$31 > $$28 * $$28 + $$29 * $$29 + $$30 * $$30) {
                            if ($$31 > 0.0) {
                                $$18[$$27 * 4 + 3] = -1.0;
                            } else {
                                $$18[$$26 * 4 + 3] = -1.0;
                            }
                        }
                    }
                }
            }
        }
        var toPlace = new HashSet<BlockPos>();
        try (BulkSectionAccess $$32 = new BulkSectionAccess(level)) {
            for (int $$33 = 0; $$33 < $$17; $$33++) {
                double $$34 = $$18[$$33 * 4 + 3];
                if (!($$34 < 0.0)) {
                    double $$35 = $$18[$$33 * 4 + 0];
                    double $$36 = $$18[$$33 * 4 + 1];
                    double $$37 = $$18[$$33 * 4 + 2];
                    int $$38 = Math.max(Mth.floor($$35 - $$34), x);
                    int $$39 = Math.max(Mth.floor($$36 - $$34), y);
                    int $$40 = Math.max(Mth.floor($$37 - $$34), z);
                    int $$41 = Math.max(Mth.floor($$35 + $$34), $$38);
                    int $$42 = Math.max(Mth.floor($$36 + $$34), $$39);
                    int $$43 = Math.max(Mth.floor($$37 + $$34), $$40);

                    for (int $$44 = $$38; $$44 <= $$41; $$44++) {
                        double $$45 = ((double)$$44 + 0.5 - $$35) / $$34;
                        if ($$45 * $$45 < 1.0) {
                            for (int $$46 = $$39; $$46 <= $$42; $$46++) {
                                double $$47 = ((double)$$46 + 0.5 - $$36) / $$34;
                                if ($$45 * $$45 + $$47 * $$47 < 1.0) {
                                    for (int $$48 = $$40; $$48 <= $$43; $$48++) {
                                        double $$49 = ((double)$$48 + 0.5 - $$37) / $$34;
                                        if ($$45 * $$45 + $$47 * $$47 + $$49 * $$49 < 1.0 && !level.isOutsideBuildHeight($$46)) {
                                            int $$50 = $$44 - x + ($$46 - y) * width + ($$48 - z) * width * height;
                                            if (!$$15.get($$50)) {
                                                $$15.set($$50);
                                                toPlace.add(new BlockPos($$44,$$46,$$48));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            boolean shouldPlace = false;
            for (var pos : toPlace) {
                shouldPlace|=isAdjacentToAir(level::getBlockState,pos);
            }
            if(!shouldPlace){
                return false;
            }
            for(var pos: toPlace){
                if (level.ensureCanWrite(pos)) {
                    LevelChunkSection $$51 = $$32.getSection(pos);
                    if ($$51 != null) {
                        int $$52 = SectionPos.sectionRelative(pos.getX());
                        int $$53 = SectionPos.sectionRelative(pos.getY());
                        int $$54 = SectionPos.sectionRelative(pos.getZ());
                        BlockState $$55 = $$51.getBlockState($$52, $$53, $$54);

                        for (OreConfiguration.TargetBlockState $$56 : config.targetStates) {
                            if ($$56.target.test($$55,random)) {
                                $$51.setBlockState($$52, $$53, $$54, $$56.state, false);
                                $$14++;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return $$14 > 0;
    }
}

package earth.terrarium.pastel.blocks.redstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class WeatherDetectorBlock extends DetectorBlock {

    public static final MapCodec<WeatherDetectorBlock> CODEC = simpleCodec(WeatherDetectorBlock::new);

    public WeatherDetectorBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends WeatherDetectorBlock> codec() {
        return CODEC;
    }

    @Override
    protected void updateState(BlockState state, Level world, BlockPos pos) {
        int power = 0;

        if (world.isThundering()) {
            Biome.Precipitation precipitation = world.getBiome(pos)
                                                     .value()
                                                     .getPrecipitationAt(pos);
            switch (precipitation) {
                case RAIN -> power = 15;
                case SNOW -> power = 8;
                case NONE -> power = 0;
            }
        } else if (world.isRaining()) {
            Biome.Precipitation precipitation = world.getBiome(pos)
                                                     .value()
                                                     .getPrecipitationAt(pos);
            switch (precipitation) {
                case RAIN, SNOW -> power = 8;
                case NONE -> power = 0;
            }
        }

        power = state.getValue(INVERTED) ? 15 - power : power;
        if (state.getValue(POWER) != power) {
            world.setBlock(pos, state.setValue(POWER, power), 3);
        }
    }

    @Override
    int getUpdateFrequencyTicks() {
        return 20;
    }

}

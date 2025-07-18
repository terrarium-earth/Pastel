package earth.terrarium.pastel.blocks.weathering;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringBlock extends Block implements Weathering {

    private final Weathering.WeatheringLevel weatheringLevel;

    public WeatheringBlock(Weathering.WeatheringLevel weatheringLevel, BlockBehaviour.Properties settings) {
        super(settings);
        this.weatheringLevel = weatheringLevel;
    }

    @Override
    public MapCodec<? extends WeatheringBlock> codec() {
        //TODO: Make the codec
        return null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (shouldTryWeather(world, pos)) {
            this.changeOverTime(state, world, pos, random);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Weathering.getIncreasedWeatheredBlock(state.getBlock())
                         .isPresent();
    }

    @Override
    public Weathering.WeatheringLevel getAge() {
        return this.weatheringLevel;
    }

}

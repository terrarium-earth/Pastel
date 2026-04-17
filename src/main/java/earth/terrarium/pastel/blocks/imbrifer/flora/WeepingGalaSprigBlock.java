package earth.terrarium.pastel.blocks.imbrifer.flora;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelSaplingGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeepingGalaSprigBlock extends SaplingBlock {

    public static final MapCodec<WeepingGalaSprigBlock> CODEC = simpleCodec(WeepingGalaSprigBlock::new);

    public WeepingGalaSprigBlock(BlockBehaviour.Properties settings) {
        super(PastelSaplingGenerators.WEEPING_GALA_SAPLING_GENERATOR, settings);
    }

    @Override
    public MapCodec<? extends WeepingGalaSprigBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(PastelBlockTags.ASH) || floor.is(PastelBlocks.ASHEN_BLACKSLAG.get()) || super.mayPlaceOn(
            floor, world, pos);
    }

}

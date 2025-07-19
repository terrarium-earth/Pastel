package earth.terrarium.pastel.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class SmallDragonjagBlock extends BushBlock implements Dragonjag, BonemealableBlock {

	public static final MapCodec<SmallDragonjagBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			propertiesCodec(),
			Dragonjag.Variant.CODEC.fieldOf("variant").forGetter(SmallDragonjagBlock::getVariant)
	).apply(i, SmallDragonjagBlock::new));

    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 8.0, 14.0);
    protected static final Map<Dragonjag.Variant, Block> VARIANTS = new HashMap<>();
    protected final Dragonjag.Variant variant;

    public SmallDragonjagBlock(Properties settings, Dragonjag.Variant variant) {
		super(settings);
		this.variant = variant;
		VARIANTS.put(variant, this);
	}

	@Override
	public MapCodec<? extends SmallDragonjagBlock> codec() {
		return CODEC;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
		return Dragonjag.canPlantOnTop(floor, world, pos);
	}
	
	@Override
	public Variant getVariant() {
		return variant;
	}
	
	public static Block getBlockForVariant(Variant variant) {
		return VARIANTS.get(variant);
    }

    @Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return true;
	}

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        TallDragonjagBlock tallVariant = TallDragonjagBlock.getBlockForVariant(variant);
        if (tallVariant.defaultBlockState().canSurvive(world, pos) && world.isEmptyBlock(pos.above())) {
            TallDragonjagBlock.placeAt(world, tallVariant.defaultBlockState(), pos, 2);
        }
    }

}
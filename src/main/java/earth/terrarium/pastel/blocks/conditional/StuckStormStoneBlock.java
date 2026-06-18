package earth.terrarium.pastel.blocks.conditional;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StuckStormStoneBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<StuckStormStoneBlock> CODEC = simpleCodec(StuckStormStoneBlock::new);

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 11.0D, 2.0D, 11.0D);

    public StuckStormStoneBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends StuckStormStoneBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).isRedstoneConductor(world, pos);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState updateShape(
        BlockState state,
        Direction direction,
        BlockState neighborState,
        LevelAccessor world,
        BlockPos pos,
        BlockPos neighborPos
    ) {
        return !state.canSurvive(world, pos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion explosion) {
        super.wasExploded(world, pos, explosion);

        if (world.canSeeSky(pos)) {
            LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
            if (lightningEntity != null) {
                lightningEntity.moveTo(Vec3.atBottomCenterOf(pos));
                world.addFreshEntity(lightningEntity);
            }
        }

        int power = 2;
        Biome biomeAtPos = world.getBiome(pos).value();
        if (!biomeAtPos.hasPrecipitation() && !biomeAtPos.coldEnoughToSnow(pos)) {
            // there is no rain in deserts or snow
            power = world.isThundering() ? 4 : world.isRaining() ? 3 : 2;
        }
        world.explode(null, pos.getX(), pos.getY(), pos.getZ(), power, Level.ExplosionInteraction.BLOCK);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    /**
     * If it gets ticked, there is a chance to vanish
     */
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.1) {
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return new ItemStack(PastelItems.STORM_STONE.get());
    }

}

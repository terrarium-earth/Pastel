package earth.terrarium.pastel.blocks.conditional.colored_tree;

import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SporeBlossomBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class ColoredSporeBlossomBlock extends SporeBlossomBlock {

    private static final Map<InkColor, ColoredSporeBlossomBlock> BLOSSOMS = new Object2ObjectArrayMap<>();

    protected final InkColor color;

    protected final ParticleOptions fallingParticleType;

    protected final ParticleOptions airParticleType;

    public ColoredSporeBlossomBlock(
        Properties settings,
        InkColor color,
        ParticleOptions fallingParticleType,
        ParticleOptions airParticleType
    ) {
        super(settings);
        this.color = color;
        this.fallingParticleType = fallingParticleType;
        this.airParticleType = airParticleType;
        BLOSSOMS.put(color, this);
    }

//	@Override
//	public MapCodec<? extends ColoredSporeBlossomBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

    public InkColor getColor() {
        return this.color;
    }

    public static ColoredSporeBlossomBlock byColor(InkColor color) {
        return BLOSSOMS.get(color);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d = (double) i + random.nextDouble();
        double e = (double) j + 0.7D;
        double f = (double) k + random.nextDouble();
        world.addParticle(this.fallingParticleType, d, e, f, 0.0D, 0.0D, 0.0D);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (
            int l = 0;
            l < 14;
            ++l
        ) {
            mutable.set(i + Mth.nextInt(random, -10, 10), j - random.nextInt(10), k + Mth.nextInt(random, -10, 10));
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.isCollisionShapeFullBlock(world, mutable)) {
                world
                    .addParticle(
                        this.airParticleType,
                        (double) mutable.getX() + random.nextDouble(),
                        (double) mutable.getY() + random.nextDouble(),
                        (double) mutable.getZ() + random.nextDouble(),
                        0.0D,
                        0.0D,
                        0.0D
                    );
            }
        }
    }

}

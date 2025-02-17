package de.dafuqs.spectrum.blocks.decoration;

import com.google.common.collect.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.block.*;
import net.minecraft.particle.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;

import java.util.*;

public class ColoredSporeBlossomBlock extends SporeBlossomBlock {
	
	private static final Map<DyeColor, ColoredSporeBlossomBlock> BLOSSOMS = Maps.newEnumMap(DyeColor.class);
	protected final DyeColor color;
	
	protected final ParticleEffect fallingParticleType;
	protected final ParticleEffect airParticleType;
	
	public ColoredSporeBlossomBlock(Settings settings, DyeColor color) {
		super(settings);
		this.color = color;
		BLOSSOMS.put(color, this);
		this.fallingParticleType = new DustParticleEffect(SpectrumColorHelper.getRGBVec(color), 1.0F);
		this.airParticleType = new DustParticleEffect(SpectrumColorHelper.getRGBVec(color), 1.0F);
	}

//	@Override
//	public MapCodec<? extends ColoredSporeBlossomBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static ColoredSporeBlossomBlock byColor(DyeColor color) {
		return BLOSSOMS.get(color);
	}
	
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		double d = (double) i + random.nextDouble();
		double e = (double) j + 0.7D;
		double f = (double) k + random.nextDouble();
		world.addParticle(this.fallingParticleType, d, e, f, 0.0D, 0.0D, 0.0D);
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		
		for (int l = 0; l < 14; ++l) {
			mutable.set(i + MathHelper.nextInt(random, -10, 10), j - random.nextInt(10), k + MathHelper.nextInt(random, -10, 10));
			BlockState blockState = world.getBlockState(mutable);
			if (!blockState.isFullCube(world, mutable)) {
				world.addParticle(this.airParticleType, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

}

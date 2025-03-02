package de.dafuqs.spectrum.blocks.conditional.colored_tree;

import de.dafuqs.spectrum.api.energy.color.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.block.*;
import net.minecraft.particle.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;

import java.util.*;

public class ColoredSporeBlossomBlock extends SporeBlossomBlock {
	
	private static final Map<InkColor, ColoredSporeBlossomBlock> BLOSSOMS = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	protected final ParticleEffect fallingParticleType;
	protected final ParticleEffect airParticleType;
	
	public ColoredSporeBlossomBlock(Settings settings, InkColor color, ParticleEffect fallingParticleType, ParticleEffect airParticleType) {
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

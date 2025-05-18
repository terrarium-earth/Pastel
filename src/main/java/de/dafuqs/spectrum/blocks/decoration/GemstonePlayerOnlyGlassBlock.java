package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.api.item.GemstoneColor;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GemstonePlayerOnlyGlassBlock extends GemstoneGlassBlock {

	public final MapCodec<GemstonePlayerOnlyGlassBlock> codec;

	public GemstonePlayerOnlyGlassBlock(Properties settings, GemstoneColor gemstoneColor) {
		super(settings, gemstoneColor);
		this.codec = RecordCodecBuilder.mapCodec(i -> i.group(
				propertiesCodec(),
				SpectrumRegistries.GEMSTONE_COLOR.byNameCodec().fieldOf("color").forGetter(b -> b.gemstoneColor)
		).apply(i, GemstonePlayerOnlyGlassBlock::new));
	}

	@Override
	public MapCodec<? extends GemstonePlayerOnlyGlassBlock> codec() {
		return codec;
	}

	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	@Override
	@Deprecated
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext entityShapeContext) {
			Entity entity = entityShapeContext.getEntity();
			if (entity instanceof Player) {
				return Shapes.empty();
			}
		}
		return state.getShape(world, pos);
	}
	
}

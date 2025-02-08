package de.dafuqs.spectrum.blocks.decoration;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class GemstonePlayerOnlyGlassBlock extends GemstoneGlassBlock {

	public final MapCodec<GemstonePlayerOnlyGlassBlock> codec;

	public GemstonePlayerOnlyGlassBlock(Settings settings, GemstoneColor gemstoneColor) {
		super(settings, gemstoneColor);
		this.codec = RecordCodecBuilder.mapCodec(i -> i.group(
				createSettingsCodec(),
				SpectrumRegistries.GEMSTONE_COLOR.getCodec().fieldOf("color").forGetter(b -> b.gemstoneColor)
		).apply(i, GemstonePlayerOnlyGlassBlock::new));
	}

	@Override
	public MapCodec<? extends GemstonePlayerOnlyGlassBlock> getCodec() {
		return codec;
	}

	@Override
	public boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	@Override
	@Deprecated
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (context instanceof EntityShapeContext entityShapeContext) {
			Entity entity = entityShapeContext.getEntity();
			if (entity instanceof PlayerEntity) {
				return VoxelShapes.empty();
			}
		}
		return state.getOutlineShape(world, pos);
	}
	
}

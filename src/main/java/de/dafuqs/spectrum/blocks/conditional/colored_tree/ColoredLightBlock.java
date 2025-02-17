package de.dafuqs.spectrum.blocks.conditional.colored_tree;

import de.dafuqs.spectrum.api.energy.color.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.block.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

import java.util.*;

public class ColoredLightBlock extends RedstoneLampBlock {
	
	private static final Map<InkColor, ColoredLightBlock> LIGHTS = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public ColoredLightBlock(Settings settings, InkColor color) {
		super(settings);
		this.color = color;
		LIGHTS.put(color, this);
	}

//	@Override
//	public MapCodec<? extends ColoredLightBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}
	
	public InkColor getColor() {
		return this.color;
	}
	
	public static ColoredLightBlock byColor(InkColor color) {
		return LIGHTS.get(color);
	}
	
	/**
	 * Disable culling for this block
	 * => the translucent outlines will be rendered
	 * even if the side is obstructed by a block
	 * (disabling culling is not nice for performance,
	 * but usually most sides will be visible either way)
	 */
	@Override
	public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.empty();
	}
	
}

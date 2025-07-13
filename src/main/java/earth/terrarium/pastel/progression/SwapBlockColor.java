package earth.terrarium.pastel.progression;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwapBlockColor implements BlockColor {
	
	protected final BlockColor base;
	protected final BlockColor alt;
	protected boolean shouldApply = true;

	public SwapBlockColor(@NotNull BlockColor base) {
		this.base = base;
		this.alt = (state, level, pos, tintIndex) -> 0xFFFFFFFF;
	}

	public SwapBlockColor(@NotNull BlockColor base, BlockColor alt) {
		this.base = base;
		this.alt = alt;
	}
	
	public void setShouldApply(boolean shouldApply) {
		this.shouldApply = shouldApply;
	}
	
	@Override
	public int getColor(BlockState state, @Nullable BlockAndTintGetter world, @Nullable BlockPos pos, int tintIndex) {
		if (shouldApply) {
			return base.getColor(state, world, pos, tintIndex);
		} else {
			return alt.getColor(state, world, pos, tintIndex);
		}
	}
	
}
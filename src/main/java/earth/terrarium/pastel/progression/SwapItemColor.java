package earth.terrarium.pastel.progression;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class SwapItemColor implements ItemColor {
	
	protected final ItemColor base;
	protected final ItemColor alt;
	protected boolean shouldApply = true;

	public SwapItemColor(ItemColor base, ItemColor alt) {
		this.base = base;
		this.alt = alt;
	}

	public SwapItemColor(ItemColor base) {
		this.base = base;
		this.alt = (stack, tintIndex) -> 0xFFFFFFFF;
	}
	
	public void setShouldApply(boolean shouldApply) {
		this.shouldApply = shouldApply;
	}
	
	@Override
	public int getColor(ItemStack stack, int tintIndex) {
		if (shouldApply) {
			return base.getColor(stack, tintIndex);
		} else {
			return alt.getColor(stack, tintIndex);
		}
	}
}

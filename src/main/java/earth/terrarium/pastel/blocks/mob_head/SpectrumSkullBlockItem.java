package earth.terrarium.pastel.blocks.mob_head;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;

public class SpectrumSkullBlockItem extends StandingAndWallBlockItem {
	
	protected final SpectrumSkullType type;
	
	public SpectrumSkullBlockItem(Block standingBlock, Block wallBlock, Item.Properties settings, SpectrumSkullType type) {
		super(standingBlock, wallBlock, settings, Direction.DOWN);
		this.type = type;
	}
	
}

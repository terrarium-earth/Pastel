package earth.terrarium.pastel.blocks.mob_head;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;

public class PastelSkullBlockItem extends StandingAndWallBlockItem {
	
	protected final PastelSkullType type;
	
	public PastelSkullBlockItem(Block standingBlock, Block wallBlock, Item.Properties settings, PastelSkullType type) {
		super(standingBlock, wallBlock, settings, Direction.DOWN);
		this.type = type;
	}
	
}

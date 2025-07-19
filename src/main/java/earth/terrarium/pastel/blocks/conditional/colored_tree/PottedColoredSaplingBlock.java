package earth.terrarium.pastel.blocks.conditional.colored_tree;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class PottedColoredSaplingBlock extends FlowerPotBlock implements ColoredTree {
	
	protected final InkColor color;
	
	private static final Map<InkColor, PottedColoredSaplingBlock> POTTED_SAPLINGS = new Object2ObjectArrayMap<>();
	
	public PottedColoredSaplingBlock(Block content, Properties settings, InkColor color) {
		super(content, settings);
		this.color = color;
		POTTED_SAPLINGS.put(color, this);
	}

//	@Override
//	public MapCodec<? extends PottedColoredSaplingBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}
	@Override
	public InkColor getColor() {
		return this.color;
	}
	
	public static PottedColoredSaplingBlock byColor(InkColor color) {
		return POTTED_SAPLINGS.get(color);
	}
	
	public static Collection<PottedColoredSaplingBlock> all() {
		return POTTED_SAPLINGS.values();
	}
}

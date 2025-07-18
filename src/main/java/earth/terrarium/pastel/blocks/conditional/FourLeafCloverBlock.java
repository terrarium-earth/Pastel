package earth.terrarium.pastel.blocks.conditional;

import com.mojang.serialization.MapCodec;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.blocks.decoration.CloverBlock;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Hashtable;
import java.util.Map;

public class FourLeafCloverBlock extends CloverBlock {

	public static final MapCodec<FourLeafCloverBlock> CODEC = simpleCodec(FourLeafCloverBlock::new);

	public FourLeafCloverBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends FourLeafCloverBlock> codec() {
		return CODEC;
	}
}

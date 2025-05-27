package de.dafuqs.spectrum.blocks.conditional;

import com.mojang.serialization.MapCodec;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import de.dafuqs.spectrum.blocks.decoration.CloverBlock;
import de.dafuqs.spectrum.registries.SpectrumAdvancements;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Hashtable;
import java.util.Map;

public class FourLeafCloverBlock extends CloverBlock implements RevelationAware {

	public static final MapCodec<FourLeafCloverBlock> CODEC = simpleCodec(FourLeafCloverBlock::new);

	public FourLeafCloverBlock(Properties settings) {
		super(settings);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends FourLeafCloverBlock> codec() {
		return CODEC;
	}
	
	@Override
	public ResourceLocation getCloakAdvancementIdentifier() {
		return SpectrumAdvancements.REVEAL_FOUR_LEAF_CLOVER;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		Map<BlockState, BlockState> map = new Hashtable<>();
		map.put(this.defaultBlockState(), SpectrumBlocks.CLOVER.get().defaultBlockState());
		return map;
	}
	
	@Override
	public Tuple<Item, Item> getItemCloak() {
		return new Tuple<>(this.asItem(), SpectrumBlocks.CLOVER.get().asItem());
	}
	
}

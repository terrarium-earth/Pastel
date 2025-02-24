package de.dafuqs.spectrum.blocks.spirit_sallow;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.recipe.pedestal.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.server.world.*;
import net.minecraft.state.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;

public class SpiritVinesPlantBlock extends AbstractPlantBlock implements SpiritVine {
	
	private final GemstoneColor gemstoneColor;
	
	public SpiritVinesPlantBlock(Settings settings, GemstoneColor gemstoneColor) {
		super(settings, Direction.DOWN, SHAPE, false);
		this.setDefaultState((this.stateManager.getDefaultState()).with(CRYSTALS, false));
		this.gemstoneColor = gemstoneColor;
	}

	@Override
	public MapCodec<? extends SpiritVinesPlantBlock> getCodec() {
		//TODO: Make the codec
		return null;
	}
	
	@Override
	protected AbstractPlantStemBlock getStem() {
		switch (gemstoneColor) {
			case BuiltinGemstoneColor.CYAN -> {
				return SpectrumBlocks.CYAN_SPIRIT_SALLOW_VINES;
			}
			case BuiltinGemstoneColor.MAGENTA -> {
				return SpectrumBlocks.MAGENTA_SPIRIT_SALLOW_VINES;
			}
			case BuiltinGemstoneColor.YELLOW -> {
				return SpectrumBlocks.YELLOW_SPIRIT_SALLOW_VINES;
			}
			case BuiltinGemstoneColor.BLACK -> {
				return SpectrumBlocks.BLACK_SPIRIT_SALLOW_VINES;
			}
			case BuiltinGemstoneColor.WHITE -> {
				return SpectrumBlocks.WHITE_SPIRIT_SALLOW_VINES;
			}
			default -> {
				return null;
			}
		}
	}
	
	@Override
	protected BlockState copyState(BlockState from, BlockState to) {
		return to.with(CRYSTALS, from.get(CRYSTALS));
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
		return new ItemStack(SpiritVine.getYieldItem(state));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		return SpiritVine.pick(state, world, pos);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(CRYSTALS);
	}
	
	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
		return false;
	}
	
	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state.with(CRYSTALS, false), 2);
	}
}

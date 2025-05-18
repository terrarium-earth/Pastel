package de.dafuqs.spectrum.blocks.spirit_sallow;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.item.GemstoneColor;
import de.dafuqs.spectrum.recipe.pedestal.BuiltinGemstoneColor;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class SpiritVinesPlantStemBlock extends GrowingPlantHeadBlock implements SpiritVine {
	
	private final GemstoneColor gemstoneColor;
	
	public SpiritVinesPlantStemBlock(Properties settings, GemstoneColor gemstoneColor) {
		super(settings, Direction.DOWN, SHAPE, false, 0.0D);
		this.registerDefaultState((this.stateDefinition.any()).setValue(CRYSTALS, false));
		this.gemstoneColor = gemstoneColor;
	}

	@Override
	public MapCodec<? extends SpiritVinesPlantStemBlock> codec() {
		//TODO: Make the codec
		return null;
	}
	
	@Override
	protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
		return 1;
	}
	
	@Override
	protected boolean canGrowInto(BlockState state) {
		return state.isAir();
	}
	
	@Override
	protected Block getBodyBlock() {
		switch (gemstoneColor) {
			case BuiltinGemstoneColor.CYAN -> {
				return SpectrumBlocks.CYAN_SPIRIT_SALLOW_VINES_PLANT;
			}
			case BuiltinGemstoneColor.MAGENTA -> {
				return SpectrumBlocks.MAGENTA_SPIRIT_SALLOW_VINES_PLANT;
			}
			case BuiltinGemstoneColor.YELLOW -> {
				return SpectrumBlocks.YELLOW_SPIRIT_SALLOW_VINES_PLANT;
			}
			case BuiltinGemstoneColor.BLACK -> {
				return SpectrumBlocks.BLACK_SPIRIT_SALLOW_VINES_PLANT;
			}
			case BuiltinGemstoneColor.WHITE -> {
				return SpectrumBlocks.WHITE_SPIRIT_SALLOW_VINES_PLANT;
			}
			default -> {
				return null;
			}
		}
	}
	
	@Override
	protected BlockState updateBodyAfterConvertedFromHead(BlockState from, BlockState to) {
		return to.setValue(CRYSTALS, from.getValue(CRYSTALS));
	}
	
	@Override
	protected BlockState getGrowIntoState(BlockState state, RandomSource random) {
		return super.getGrowIntoState(state, random).setValue(CRYSTALS, false);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
		return new ItemStack(SpiritVine.getYieldItem(state));
	}
	
	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		return SpiritVine.pick(state, world, pos);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(CRYSTALS);
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return false;
	}
	
	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		world.setBlock(pos, state.setValue(CRYSTALS, false), 2);
	}
}

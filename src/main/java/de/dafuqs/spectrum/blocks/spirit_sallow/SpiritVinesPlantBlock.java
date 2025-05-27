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
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class SpiritVinesPlantBlock extends GrowingPlantBodyBlock implements SpiritVine {
	
	private final GemstoneColor gemstoneColor;
	
	public SpiritVinesPlantBlock(Properties settings, GemstoneColor gemstoneColor) {
		super(settings, Direction.DOWN, SHAPE, false);
		this.registerDefaultState((this.stateDefinition.any()).setValue(CRYSTALS, false));
		this.gemstoneColor = gemstoneColor;
	}

	@Override
	public MapCodec<? extends SpiritVinesPlantBlock> codec() {
		//TODO: Make the codec
		return null;
	}
	
	@Override
	protected GrowingPlantHeadBlock getHeadBlock() {
		switch (gemstoneColor) {
			case BuiltinGemstoneColor.CYAN -> {
                return (GrowingPlantHeadBlock) SpectrumBlocks.CYAN_SPIRIT_SALLOW_VINES.get();
			}
			case BuiltinGemstoneColor.MAGENTA -> {
                return (GrowingPlantHeadBlock) SpectrumBlocks.MAGENTA_SPIRIT_SALLOW_VINES.get();
			}
			case BuiltinGemstoneColor.YELLOW -> {
                return (GrowingPlantHeadBlock) SpectrumBlocks.YELLOW_SPIRIT_SALLOW_VINES.get();
			}
			case BuiltinGemstoneColor.BLACK -> {
                return (GrowingPlantHeadBlock) SpectrumBlocks.BLACK_SPIRIT_SALLOW_VINES.get();
			}
			case BuiltinGemstoneColor.WHITE -> {
                return (GrowingPlantHeadBlock) SpectrumBlocks.WHITE_SPIRIT_SALLOW_VINES.get();
			}
			default -> {
				return null;
			}
		}
	}
	
	@Override
	protected BlockState updateHeadAfterConvertedFromBody(BlockState from, BlockState to) {
		return to.setValue(CRYSTALS, from.getValue(CRYSTALS));
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

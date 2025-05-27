package de.dafuqs.spectrum.blocks.decay;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.SpectrumBlockTags;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DecayAwayBlock extends Block {

	public static final MapCodec<DecayAwayBlock> CODEC = simpleCodec(DecayAwayBlock::new);

	private static final EnumProperty<TargetConversion> TARGET_CONVERSION = EnumProperty.create("target_conversion", TargetConversion.class);
	
	public DecayAwayBlock(Properties settings) {
		super(settings);
		registerDefaultState(getStateDefinition().any().setValue(TARGET_CONVERSION, TargetConversion.DEFAULT));
	}

	@Override
	protected MapCodec<? extends DecayAwayBlock> codec() {
		return CODEC;
	}
	
	@Override
	public void setPlacedBy(@NotNull Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (!world.isClientSide) {
			world.scheduleTick(pos, state.getBlock(), 4);
		}
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(TARGET_CONVERSION);
	}
	
	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		super.tick(state, world, pos, random);
		
		// convert all neighboring decay blocks to this
		for (BlockPos targetBlockPos : BlockPos.withinManhattan(pos, 1, 1, 1)) {
			BlockState currentBlockState = world.getBlockState(targetBlockPos);
			if (currentBlockState.is(SpectrumBlockTags.DECAY_AWAY_CURABLES)) {
				world.setBlockAndUpdate(targetBlockPos, getTargetStateForCurable(currentBlockState));
				world.scheduleTick(targetBlockPos, state.getBlock(), 8);
			} else if (currentBlockState.is(SpectrumBlockTags.DECAY_AWAY_REMOVABLES)) {
				world.setBlockAndUpdate(targetBlockPos, this.defaultBlockState().setValue(TARGET_CONVERSION, TargetConversion.AIR));
				world.scheduleTick(targetBlockPos, state.getBlock(), 8);
			}
		}
		
		// and turn this to the leftover block state
		BlockState currentState = world.getBlockState(pos);
		TargetConversion targetConversion = currentState.getValue(TARGET_CONVERSION);
		world.setBlock(pos, targetConversion.getTargetState(world), 3);
	}
	
	public BlockState getTargetStateForCurable(BlockState blockState) {
		if (blockState.getBlock() instanceof DecayBlock) {
			if (blockState.is(SpectrumBlocks.RUIN.get()) || blockState.is(SpectrumBlocks.FORFEITURE.get())) {
				if (blockState.getValue(ForfeitureBlock.CONVERSION) == DecayBlock.Conversion.DEFAULT) {
					return this.defaultBlockState().setValue(TARGET_CONVERSION, TargetConversion.BEDROCK);
				}
			} else if (blockState.is(SpectrumBlocks.FAILING.get())) {
				if (blockState.getValue(FailingBlock.CONVERSION) == DecayBlock.Conversion.DEFAULT) {
					return this.defaultBlockState().setValue(TARGET_CONVERSION, TargetConversion.OBSIDIAN);
				} else if (blockState.getValue(FailingBlock.CONVERSION) == DecayBlock.Conversion.SPECIAL) {
					return this.defaultBlockState().setValue(TARGET_CONVERSION, TargetConversion.CRYING_OBSIDIAN);
				}
			}
		}
		return this.defaultBlockState();
	}
	
	public enum TargetConversion implements StringRepresentable {
		DEFAULT("default", Blocks.DIRT.defaultBlockState()),
		BEDROCK("bedrock", Blocks.BEDROCK.defaultBlockState()),
		OBSIDIAN("obsidian", Blocks.OBSIDIAN.defaultBlockState()),
		CRYING_OBSIDIAN("crying_obsidian", Blocks.CRYING_OBSIDIAN.defaultBlockState()),
		AIR("air", Blocks.AIR.defaultBlockState());
		
		private final String name;
		private final BlockState targetState;
		
		TargetConversion(String name, BlockState targetState) {
			this.name = name;
			this.targetState = targetState;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		@Override
		public String getSerializedName() {
			return this.name;
		}
		
		public BlockState getTargetState(Level world) {
			if (this == DEFAULT) {
				ResourceLocation identifier = world.dimensionType().effectsLocation();
				if (BuiltinDimensionTypes.NETHER_EFFECTS.equals(identifier)) {
					return Blocks.NETHERRACK.defaultBlockState();
				} else if (BuiltinDimensionTypes.END_EFFECTS.equals(identifier)) {
					return Blocks.END_STONE.defaultBlockState();
				} else if (SpectrumDimensions.DIMENSION_ID.equals(identifier)) {
					return SpectrumBlocks.BLACKSLAG.get().defaultBlockState();
				}
				return Blocks.DIRT.defaultBlockState();
			}
			return this.targetState;
		}
	}
	
}

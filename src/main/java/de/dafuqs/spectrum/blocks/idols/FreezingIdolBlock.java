package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreezingIdolBlock extends IdolBlock {
	
	// Block: The Block to freeze
	// BlockState: The BlockState when Block is getting frozen
	// Float: The chance to freeze
	public static final Map<Block, Tuple<BlockState, Float>> FREEZING_MAP = new HashMap<>() {{
		put(Blocks.SNOW, new Tuple<>(Blocks.POWDER_SNOW.defaultBlockState(), 0.25F));
		put(Blocks.POWDER_SNOW, new Tuple<>(Blocks.SNOW_BLOCK.defaultBlockState(), 0.5F));
		put(Blocks.WATER, new Tuple<>(Blocks.ICE.defaultBlockState(), 1.0F));
		put(Blocks.ICE, new Tuple<>(Blocks.PACKED_ICE.defaultBlockState(), 0.25F));
		put(Blocks.PACKED_ICE, new Tuple<>(Blocks.BLUE_ICE.defaultBlockState(), 0.1F));
		put(Blocks.GRASS_BLOCK, new Tuple<>(Blocks.PODZOL.defaultBlockState(), 0.1F));
		put(Blocks.BASALT, new Tuple<>(Blocks.CALCITE.defaultBlockState(), 0.5F));
		put(SpectrumBlocks.BLAZING_CRYSTAL.get(), new Tuple<>(SpectrumBlocks.FROSTBITE_CRYSTAL.get().defaultBlockState(), 0.5F));
	}};
	public static final Map<BlockState, Tuple<BlockState, Float>> FREEZING_STATE_MAP = new HashMap<>() {{
		put(Blocks.LAVA.defaultBlockState(), new Tuple<>(Blocks.OBSIDIAN.defaultBlockState(), 1.0F)); // just full, not flowing
	}};
	
	public FreezingIdolBlock(Properties settings, ParticleOptions particleEffect) {
		super(settings, particleEffect);
	}

	@Override
	public MapCodec<? extends FreezingIdolBlock> codec() {
		//TODO: Make the codec
		return null;
	}
	
	public static void freeze(@NotNull ServerLevel world, BlockPos blockPos) {
		BlockState sourceState = world.getBlockState(blockPos);
		if (FREEZING_MAP.containsKey(sourceState.getBlock())) {
			Tuple<BlockState, Float> recipe = FREEZING_MAP.get(sourceState.getBlock());
			if (recipe.getB() >= 1.0F || world.random.nextFloat() < recipe.getB()) {
				// freeze
				world.setBlockAndUpdate(blockPos, recipe.getA());
				world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(recipe.getA())); // processed in WorldRenderer processGlobalEvent()
				return;
			}
		}
		if (FREEZING_STATE_MAP.containsKey(sourceState)) {
			Tuple<BlockState, Float> recipe = FREEZING_STATE_MAP.get(sourceState);
			if (recipe.getB() >= 1.0F || world.random.nextFloat() < recipe.getB()) {
				// freeze
				world.setBlockAndUpdate(blockPos, recipe.getA());
				world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(recipe.getA())); // processed in WorldRenderer processGlobalEvent()
			}
		}
		
	}
	
	@Override
	public boolean trigger(ServerLevel world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		for (Direction direction : Direction.values()) {
			freeze(world, blockPos.relative(direction));
		}
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.pastel.freezing_idol.tooltip"));
	}
	
}

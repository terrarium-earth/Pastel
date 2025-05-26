package de.dafuqs.spectrum.blocks.particle_spawner;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.block.RedstonePoweredBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class ParticleSpawnerBlock extends AbstractParticleSpawnerBlock implements RedstonePoweredBlock {
	
	public static final MapCodec<ParticleSpawnerBlock> CODEC = simpleCodec(ParticleSpawnerBlock::new);
	
	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);
	
	public ParticleSpawnerBlock(Properties settings) {
		super(settings);
		registerDefaultState(getStateDefinition().any().setValue(BlockStateProperties.POWERED, false));
	}
	
	@Override
	public MapCodec<? extends ParticleSpawnerBlock> codec() {
		return CODEC;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.pastel.particle_spawner.tooltip").withStyle(ChatFormatting.GRAY));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
		stateManager.add(BlockStateProperties.POWERED);
	}
	
	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (!world.isClientSide) {
			if (this.checkGettingPowered(world, pos)) {
				this.power(world, pos);
			} else {
				this.unPower(world, pos);
			}
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState placementState = this.defaultBlockState();
		if (ctx.getLevel().getBestNeighborSignal(ctx.getClickedPos()) > 0) {
			placementState = placementState.setValue(BlockStateProperties.POWERED, true);
		}
		return placementState;
	}
	
	@Override
	public boolean shouldSpawnParticles(Level world, BlockPos pos) {
		return world.getBlockState(pos).getValue(BlockStateProperties.POWERED).equals(true);
	}
	
}

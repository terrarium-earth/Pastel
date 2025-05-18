package de.dafuqs.spectrum.blocks;

import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public abstract class PlacedItemBlock extends BaseEntityBlock {
	
	public PlacedItemBlock(Properties settings) {
		super(settings);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PlacedItemBlockEntity(pos, state);
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof PlacedItemBlockEntity placedItemBlockEntity) {
			ItemStack placedStack = stack.copy();
			placedStack.setCount(1);
			placedItemBlockEntity.setStack(placedStack);
			if (placer instanceof Player playerPlacer) {
				placedItemBlockEntity.setOwner(playerPlacer);
			}
		}
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
		Optional<PlacedItemBlockEntity> blockEntity = world.getBlockEntity(pos, SpectrumBlockEntities.PLACED_ITEM);
		if (blockEntity.isPresent()) {
			return blockEntity.get().getStack().copy();
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		BlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY);
		if (blockEntity instanceof PlacedItemBlockEntity placedItemBlockEntity) {
			return List.of(placedItemBlockEntity.getStack());
		} else {
			return super.getDrops(state, builder);
		}
	}

}

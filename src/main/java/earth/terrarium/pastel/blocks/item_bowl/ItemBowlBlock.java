package earth.terrarium.pastel.blocks.item_bowl;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.blocks.InWorldInteractionBlock;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntity;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemBowlBlock extends InWorldInteractionBlock {

	public static final MapCodec<ItemBowlBlock> CODEC = simpleCodec(ItemBowlBlock::new);

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D);

	// Positions to check on place / destroy to upgrade those blocks upgrade counts
	private final List<Vec3i> possibleEnchanterOffsets = new ArrayList<>() {{
		add(new Vec3i(5, 0, 3));
		add(new Vec3i(-5, 0, -3));
		add(new Vec3i(-3, 0, 5));
		add(new Vec3i(-3, 0, -5));
		add(new Vec3i(3, 0, 5));
		add(new Vec3i(3, 0, -5));
		add(new Vec3i(5, 0, 3));
		add(new Vec3i(5, 0, -3));
	}};

	// Positions to check on place / destroy to upgrade those blocks upgrade counts
	private final List<Vec3i> possibleSpiritInstillerOffsets = new ArrayList<>() {{
		add(new Vec3i(0, -1, 2));
		add(new Vec3i(0, -1, -2));
		add(new Vec3i(2, -1, 0));
		add(new Vec3i(-2, -1, 0));
	}};

	public ItemBowlBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends ItemBowlBlock> codec() {
		return CODEC;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ItemBowlBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return world.isClientSide ? createTickerHelper(type, SpectrumBlockEntities.ITEM_BOWL.get(), ItemBowlBlockEntity::clientTick) : null;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		Containers.dropContentsOnDestroy(state, newState, world, pos);
		super.onRemove(state, world, pos, newState, moved);
		updateConnectedMultiBlocks(world, pos);
	}
	
	/**
	 * When placed or removed the item bowl searches for a valid block entity and triggers it to update its current recipe
	 */
	private void updateConnectedMultiBlocks(@NotNull Level world, @NotNull BlockPos pos) {
		for (Vec3i possibleUpgradeBlockOffset : possibleEnchanterOffsets) {
			BlockPos currentPos = pos.offset(possibleUpgradeBlockOffset);
			BlockEntity blockEntity = world.getBlockEntity(currentPos);
			if (blockEntity instanceof EnchanterBlockEntity enchanterBlockEntity) {
				enchanterBlockEntity.inventoryChanged();
				break;
			}
		}
		
		for (Vec3i possibleUpgradeBlockOffset : possibleSpiritInstillerOffsets) {
			BlockPos currentPos = pos.offset(possibleUpgradeBlockOffset);
			BlockEntity blockEntity = world.getBlockEntity(currentPos);
			if (blockEntity instanceof SpiritInstillerBlockEntity spiritInstillerBlockEntity) {
				spiritInstillerBlockEntity.inventoryChanged();
				break;
			}
		}
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack handStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ItemBowlBlockEntity itemBowlBlockEntity) {
			if (exchangeStack(world, pos, player, hand, handStack, itemBowlBlockEntity)) {
				updateConnectedMultiBlocks(world, pos);
			}
		}

		return ItemInteractionResult.sidedSuccess(world.isClientSide());
	}
	
}

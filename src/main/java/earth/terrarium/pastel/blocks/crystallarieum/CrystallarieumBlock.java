package earth.terrarium.pastel.blocks.crystallarieum;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.api.energy.InkStorageItem;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.render.SlotBackgroundEffectProvider;
import earth.terrarium.pastel.blocks.InWorldInteractionBlock;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import earth.terrarium.pastel.registries.SpectrumDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrystallarieumBlock extends InWorldInteractionBlock implements SlotBackgroundEffectProvider {

	public static final MapCodec<CrystallarieumBlock> CODEC = simpleCodec(CrystallarieumBlock::new);

	public CrystallarieumBlock(Properties settings) {
		super(settings);
		this.registerDefaultState((this.stateDefinition.any()));
	}

	@Override
	public MapCodec<? extends CrystallarieumBlock> codec() {
		return CODEC;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CrystallarieumBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, SpectrumBlockEntities.CRYSTALLARIEUM.get(), world.isClientSide ? CrystallarieumBlockEntity::clientTick : CrystallarieumBlockEntity::serverTick);
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (!world.isClientSide() && direction == Direction.UP) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CrystallarieumBlockEntity crystallarieumBlockEntity) {
				crystallarieumBlockEntity.onTopBlockChange(neighborState, null);
			}
		}
		return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
	}
	
	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!world.isClientSide && entity instanceof ItemEntity itemEntity) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CrystallarieumBlockEntity crystallarieumBlockEntity) {
				ItemStack stack = itemEntity.getItem();
				crystallarieumBlockEntity.acceptStack(stack, false, itemEntity.getOwner() != null ? itemEntity.getOwner().getUUID() : null);
			}
		} else {
			super.fallOn(world, state, pos, entity, fallDistance);
		}
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!world.isClientSide) {
			// if the structure is valid the player can put / retrieve blocks into the shrine
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CrystallarieumBlockEntity crystal) {
				
				if (player.isShiftKeyDown() || stack.isEmpty()) {
					// sneaking or empty hand: remove items
					if (retrieveStack(world, pos, player, hand, stack, crystal, 1) || retrieveStack(world, pos, player, hand, stack, crystal, 0)) {
						crystal.inventoryChanged();
						crystal.setOwner(player);
					}
					return ItemInteractionResult.CONSUME;
				} else {
					if (FluidUtil.interactWithFluidHandler(player, hand, crystal.tank)){
						crystal.updateInClientWorld();
						return ItemInteractionResult.CONSUME;
					}
					
					// hand is full and inventory is empty: add
					// hand is full and inventory already contains item: exchange them
					else if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
						if (inkStorageItem.getDrainability().canDrain(false) && exchangeStack(world, pos, player, hand, stack, crystal, CrystallarieumBlockEntity.INK_STORAGE_STACK_SLOT_ID)) {
							crystal.inventoryChanged();
							crystal.setOwner(player);
						}
					} else {
						if (exchangeStack(world, pos, player, hand, stack, crystal, CrystallarieumBlockEntity.CATALYST_SLOT_ID)) {
							crystal.inventoryChanged();
							crystal.setOwner(player);
						}
					}
				}
			}
			return ItemInteractionResult.CONSUME;
		}
		return ItemInteractionResult.SUCCESS;
	}
	
	public ItemStack asStackWithColor(InkColor color) {
		ItemStack stack = asItem().getDefaultInstance();
		stack.set(SpectrumDataComponentTypes.INK_COLOR, color);
		return stack;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		var color = stack.get(SpectrumDataComponentTypes.INK_COLOR);
		if (color != null)
			tooltip.add(color.getColoredInkName());
	}
	
	@Override
	public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		var color = stack.get(SpectrumDataComponentTypes.INK_COLOR);
		return color != null ? SlotEffect.BORDER_FADE : SlotEffect.NONE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		var color = stack.getOrDefault(SpectrumDataComponentTypes.INK_COLOR, InkColors.WHITE);
		return color.getColorInt();
	}
}

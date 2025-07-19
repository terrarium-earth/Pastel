package earth.terrarium.pastel.blocks.enchanter;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.capabilities.ExperienceHandler;
import earth.terrarium.pastel.blocks.InWorldInteractionBlock;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconHelper;
import earth.terrarium.pastel.progression.PastelAdvancementCriteria;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelMultiblocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EnchanterBlock extends InWorldInteractionBlock {

	public static final MapCodec<EnchanterBlock> CODEC = simpleCodec(EnchanterBlock::new);

	public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("midgame/build_enchanting_structure");

	public EnchanterBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends EnchanterBlock> codec() {
		return CODEC;
	}
	
	public static void clearCurrentlyRenderedMultiBlock(Level world) {
		if (world.isClientSide) {
			ModonomiconHelper.clearRenderedMultiblock(PastelMultiblocks.get(PastelMultiblocks.ENCHANTER));
		}
	}
	
	public static boolean verifyStructure(Level world, BlockPos blockPos, @Nullable ServerPlayer serverPlayerEntity) {
		Multiblock multiblock = PastelMultiblocks.get(PastelMultiblocks.ENCHANTER);
		boolean valid = multiblock.validate(world, blockPos.below(3), Rotation.NONE);
		
		if (valid) {
			if (serverPlayerEntity != null) {
				PastelAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger(serverPlayerEntity, multiblock);
			}
		} else {
			if (world.isClientSide) {
				ModonomiconHelper.renderMultiblock(PastelMultiblocks.get(PastelMultiblocks.ENCHANTER), PastelMultiblocks.ENCHANTER_TEXT, blockPos.below(4), Rotation.NONE);
			}
		}
		
		return valid;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EnchanterBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, PastelBlockEntities.ENCHANTER.get(), world.isClientSide ? EnchanterBlockEntity::clientTick : EnchanterBlockEntity::serverTick);
	}
	
	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		if (world.isClientSide()) {
			clearCurrentlyRenderedMultiBlock((Level) world);
		}
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack handStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (level.isClientSide) {
			verifyStructure(level, pos, null);
			return ItemInteractionResult.SUCCESS;
		} else {
			if (verifyStructure(level, pos, (ServerPlayer) player)) {
				
				// if the structure is valid the player can put / retrieve blocks into the shrine
				BlockEntity blockEntity = level.getBlockEntity(pos);
				if (blockEntity instanceof EnchanterBlockEntity enchanterBlockEntity) {

					if (player.isShiftKeyDown() || handStack.isEmpty()) {
						// sneaking or empty hand: remove items
						for (int i = 0; i < EnchanterBlockEntity.XP_STORAGE + 1; i++) {
							if (retrieveStack(level, pos, player, hand, handStack, enchanterBlockEntity, i)) {
								enchanterBlockEntity.setItemFacingDirection(player.getDirection());
								enchanterBlockEntity.setOwner(player);
								enchanterBlockEntity.inventoryChanged();
								break;
							}
						}
						return ItemInteractionResult.CONSUME;
					} else {
						// hand is full and inventory is empty: add
						// hand is full and inventory already contains item: exchange them
						int targetSlot = getTargetSlot(handStack, level.registryAccess());
						if (exchangeStack(level, pos, player, hand, handStack, enchanterBlockEntity, targetSlot)) {
							enchanterBlockEntity.setItemFacingDirection(player.getDirection());
							enchanterBlockEntity.setOwner(player);
							enchanterBlockEntity.inventoryChanged();
						}
					}
				}
			}
			return ItemInteractionResult.CONSUME;
		}
	}

	private int getTargetSlot(ItemStack hand, HolderLookup.Provider lookup) {
		if (hand.isEmpty())
			return EnchanterBlockEntity.CENTER;

		var storage = hand.getCapability(PastelCapabilities.Misc.XP, lookup);
		if (storage != null)
			return EnchanterBlockEntity.XP_STORAGE;

		return EnchanterBlockEntity.CENTER;
	}
	
}

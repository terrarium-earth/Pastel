package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.api.energy.InkPowered;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.api.item.PrioritizedBlockInteraction;
import de.dafuqs.spectrum.compat.claims.GenericClaimModsCompat;
import de.dafuqs.spectrum.helpers.BuildingHelper;
import de.dafuqs.spectrum.registries.SpectrumBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Triplet;

public abstract class BuildingStaffItem extends Item implements PrioritizedBlockInteraction, InkPowered {
	
	public static final InkColor USED_COLOR = InkColors.CYAN;
	
	public BuildingStaffItem(Properties settings) {
		super(settings);
	}
	
	public boolean canInteractWith(BlockState state, BlockGetter world, BlockPos pos, Player player) {
		if (state.getBlock().asItem() == Items.AIR) {
			return false;
		}
		if (player == null || world.getBlockEntity(pos) != null || state.is(SpectrumBlockTags.BUILDING_STAFFS_BLACKLISTED)) {
			return false;
		}
		if (player.isCreative()) {
			return true;
		}
		
		float hardness = state.getDestroySpeed(world, pos);
		return hardness >= 0 && GenericClaimModsCompat.canInteract(player.level(), pos, player);
	}
	
	/**
	 * @return The block to place, the blockItem to consume, the amount
	 */
	protected static Triplet<Block, Item, Integer> countSuitableReplacementItems(@NotNull Player player, @NotNull Block targetBlock, boolean single, int inkCostPerBlock) {
		if (player.isCreative()) {
			return new Triplet<>(targetBlock, targetBlock.asItem(), single ? 1 : Integer.MAX_VALUE);
		}
		
		long blocksToPlace;
		if (single) {
			blocksToPlace = InkPowered.getAvailableInk(player, USED_COLOR) >= inkCostPerBlock ? 1 : 0;
		} else {
			blocksToPlace = InkPowered.getAvailableInk(player, USED_COLOR) / inkCostPerBlock;
		}
		blocksToPlace = Math.min(1024, blocksToPlace); // to not yeet performance out the window
		
		return BuildingHelper.getBuildingItemCountInInventoryIncludingSimilars(player, targetBlock, blocksToPlace);
	}
	
	public static class BuildingStaffPlacementContext extends BlockPlaceContext {
		
		public BuildingStaffPlacementContext(Level world, @Nullable Player playerEntity, BlockHitResult blockHitResult) {
			super(world, playerEntity, InteractionHand.MAIN_HAND, ItemStack.EMPTY, blockHitResult);
		}
		
	}
	
}

package earth.terrarium.pastel.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A block that drops additional items from a loot table when stripped
 * Call checkAndDropStrippedLoot() in the blocks onStateReplaced()
 */
public interface StrippableDrop {
	
	Block getStrippedBlock();
	
	ResourceKey<LootTable> getStrippingLootTableKey();
	
	default boolean checkAndDropStrippedLoot(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		if (!moved && newState.is(getStrippedBlock())) {
			// we sadly don't have the entity or hand stack here, but oh well
			List<ItemStack> harvestedStacks = getStrippedStacks(state, (ServerLevel) world, pos, world.getBlockEntity(pos), null, ItemStack.EMPTY, getStrippingLootTableKey());
			for (ItemStack harvestedStack : harvestedStacks) {
				Containers.dropItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, harvestedStack);
			}
			return true;
		}
		return false;
	}
	
	static List<ItemStack> getStrippedStacks(BlockState state, ServerLevel world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack, ResourceKey<LootTable> lootTableKey) {
		var builder = (new LootParams.Builder(world))
				.withParameter(LootContextParams.BLOCK_STATE, state)
				.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
				.withParameter(LootContextParams.TOOL, stack)
				.withOptionalParameter(LootContextParams.THIS_ENTITY, entity)
				.withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity);
		
		LootTable lootTable = world.getServer().reloadableRegistries().getLootTable(lootTableKey);
		return lootTable.getRandomItems(builder.create(LootContextParamSets.BLOCK));
	}
	
}

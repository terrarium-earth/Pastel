package earth.terrarium.pastel.blocks.deeper_down;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.api.block.StrippableDrop;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.*;
import org.jetbrains.annotations.*;

public class StrippingLootPillarBlock extends RotatedPillarBlock implements StrippableDrop {

    private final Block sourceBlock;
    private final ResourceKey<LootTable> strippingLootTableKey;

    public StrippingLootPillarBlock(
        Properties settings, Block sourceBlock, ResourceKey<LootTable> strippingLootTableKey) {
        super(settings);
        this.sourceBlock = sourceBlock;
        this.strippingLootTableKey = strippingLootTableKey;
    }

    @Override
    public MapCodec<? extends StrippingLootPillarBlock> codec() {
        //TODO: Make the codec
        return null;
    }

    @Override
    public Block getStrippedBlock() {
        return sourceBlock;
    }

    @Override
    public ResourceKey<LootTable> getStrippingLootTableKey() {
        return strippingLootTableKey;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        checkAndDropStrippedLoot(state, world, pos, newState, moved);
        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(
        BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        ItemStack itemStack = context.getItemInHand();
        if (!itemStack.canPerformAction(itemAbility)) {
            return null;
        } else if (ItemAbilities.AXE_STRIP == itemAbility) {
            return sourceBlock.defaultBlockState()
                              .setValue(AXIS, state.getValue(AXIS));
        }
        return null;
    }
}

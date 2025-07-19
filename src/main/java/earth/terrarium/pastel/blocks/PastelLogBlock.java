package earth.terrarium.pastel.blocks;

import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.common.*;
import org.jetbrains.annotations.*;

public class PastelLogBlock extends RotatedPillarBlock {

    private final Block sourceBlock;

    public PastelLogBlock(Properties properties, Block sourceBlock) {
        super(properties);
        this.sourceBlock = sourceBlock;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        ItemStack itemStack = context.getItemInHand();
        if (!itemStack.canPerformAction(itemAbility)) {
            return null;
        } else if (ItemAbilities.AXE_STRIP == itemAbility) {
            return sourceBlock.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }
        return null;
    }
}

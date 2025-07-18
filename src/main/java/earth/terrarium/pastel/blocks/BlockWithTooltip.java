package earth.terrarium.pastel.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class BlockWithTooltip extends Block {

    protected final Component tooltipText;

    public BlockWithTooltip(Properties settings, Component tooltipText) {
        super(settings);
        this.tooltipText = tooltipText;
    }

    @Override
    public MapCodec<? extends BlockWithTooltip> codec() {
        //TODO: Make the codec
        return null;
    }

    @Override
    public void appendHoverText(
        ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(tooltipText);
    }
}

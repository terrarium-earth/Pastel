package earth.terrarium.pastel.items.food;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SlushslideItem extends DrinkItem {

    public SlushslideItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip
            .add(
                Component
                    .translatable("item.pastel.slushslide.tooltip")
                    .withStyle(ChatFormatting.GRAY)
            );
        super.appendHoverText(stack, context, tooltip, type);
    }
}

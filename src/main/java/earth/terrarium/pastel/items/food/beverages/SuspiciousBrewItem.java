package earth.terrarium.pastel.items.food.beverages;

import earth.terrarium.pastel.api.item.FermentedItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.List;

public class SuspiciousBrewItem extends BeverageItem {

    //TODO should this use the SuspiciousStewContents component instead?

    public SuspiciousBrewItem(Properties settings) {
        super(settings.component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (FermentedItem.isPreviewStack(stack)) {
            tooltip.add(Component.translatable("item.pastel.suspicious_brew.tooltip.preview")
                                 .withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("item.pastel.suspicious_brew.tooltip.preview2")
                                 .withStyle(ChatFormatting.GRAY));
        }
    }

}

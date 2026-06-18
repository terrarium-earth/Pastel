package earth.terrarium.pastel.items.food.beverages;

import earth.terrarium.pastel.api.item.FermentedItem;
import earth.terrarium.pastel.components.JadeWineComponent;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.List;

public class JadeWineItem extends BeverageItem {

    public JadeWineItem(Properties settings) {
        super(
            settings
                .component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                .component(PastelDataComponentTypes.JADE_WINE, JadeWineComponent.DEFAULT)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (FermentedItem.isPreviewStack(stack)) {
            String translationKey = getDescriptionId();
            tooltip
                .add(
                    Component
                        .translatable(translationKey + ".tooltip.preview")
                        .withStyle(ChatFormatting.GRAY)
                );
            tooltip
                .add(
                    Component
                        .translatable(translationKey + ".tooltip.preview2")
                        .withStyle(ChatFormatting.GRAY)
                );
            tooltip
                .add(
                    Component
                        .translatable("item.pastel.tooltip.could_use_some_sweetener")
                        .withStyle(ChatFormatting.GRAY)
                );
        }
    }

}

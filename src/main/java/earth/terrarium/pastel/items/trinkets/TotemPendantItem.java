package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.PastelCommon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class TotemPendantItem extends PastelTrinketItem {

    public TotemPendantItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/totem_pendant"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.totem_pendant.tooltip")
                             .withStyle(ChatFormatting.GRAY));
    }


}

package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.PastelCommon;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class NeatRingItem extends PastelTrinketItem {


    public NeatRingItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/neat_ring"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.neat_ring.tooltip"));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

}

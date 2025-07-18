package earth.terrarium.pastel.items.trinkets;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class PuffCircletItem extends AzureDikeTrinketItem {

    public static final float PROJECTILE_DEFLECTION_COST = 4;
    public static final float FALL_DAMAGE_NEGATING_COST = 2;

    public PuffCircletItem(Properties settings, ResourceLocation unlockIdentifier) {
        super(settings, unlockIdentifier);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.puff_circlet.tooltip"));
        tooltip.add(Component.translatable("item.pastel.puff_circlet.tooltip2"));
    }

    @Override
    public int maxAzureDike(ItemStack stack) {
        return 4;
    }

}

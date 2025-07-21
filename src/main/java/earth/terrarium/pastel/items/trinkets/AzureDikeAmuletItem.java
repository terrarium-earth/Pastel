package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.energy.storage.FixedSingleInkStorage;
import earth.terrarium.pastel.api.item.AzureDikeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class AzureDikeAmuletItem extends InkDrainTrinketItem implements AzureDikeItem {

    public AzureDikeAmuletItem(Properties settings, ResourceLocation unlockIdentifier) {
        super(settings, unlockIdentifier, InkColors.BLUE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("item.pastel.azure_dike_provider.tooltip", maxAzureDike(stack)));
        super.appendHoverText(stack, context, tooltip, type);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        recalculate(slotContext.entity());
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        recalculate(slotContext.entity());
    }

    @Override
    public int maxAzureDike(ItemStack stack) {
        FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
        long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
        if (storedInk < 100) {
            return 0;
        } else {
            return getDike(storedInk);
        }
    }

    public int getDike(long storedInk) {
        if (storedInk < 100) {
            return 0;
        } else {
            return 2 + 2 * (int) (Math.log(storedInk / 100.0f) / Math.log(8));
        }
    }

}

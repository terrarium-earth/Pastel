package de.dafuqs.spectrum.items.food.beverages;

import de.dafuqs.spectrum.items.ItemWithTooltip;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class EvernectarItem extends ItemWithTooltip {

    public EvernectarItem(Properties settings, String tooltip) {
        super(settings, tooltip);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}

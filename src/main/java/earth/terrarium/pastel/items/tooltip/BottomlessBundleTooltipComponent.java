package earth.terrarium.pastel.items.tooltip;

import earth.terrarium.pastel.api.gui.PastelTooltipComponent;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BottomlessBundleTooltipComponent implements PastelTooltipComponent {

    private static final int MAX_DISPLAYED_SLOTS = 5;
    private final List<ItemStack> itemStacks;

    private final int displayedSlotCount;
    private final boolean drawDots;

    public BottomlessBundleTooltipComponent(ItemStorageTooltipData data) {
        var storage = data.storage();

        long amount = storage.getCount();
        long maxCount = storage.stackSize();
        double totalStacks = (float) amount / (float) maxCount;
        this.displayedSlotCount = Math.max(2, Math.min(MAX_DISPLAYED_SLOTS + 1, (int) Math.ceil(totalStacks) + 1));

        this.itemStacks = NonNullList.withSize(5, ItemStack.EMPTY);
        for (int i = 0; i < Math.min(5, displayedSlotCount + 1); i++) {
            var stackAmount = Math.min(Math.min(maxCount, amount - i * maxCount), Integer.MAX_VALUE);
            this.itemStacks.set(i, storage.stack((int) stackAmount));
        }
        drawDots = totalStacks > MAX_DISPLAYED_SLOTS;
    }

    @Override
    public int getHeight() {
        return 20 + 2 + 4;
    }

    @Override
    public int getWidth(Font textRenderer) {
        return displayedSlotCount * 20 + 2 + 4;
    }

    @Override
    public void renderImage(Font textRenderer, int x, int y, GuiGraphics context) {
        int n = x + 1;
        int o = y + 1;

        for (int i = 0; i < Math.min(MAX_DISPLAYED_SLOTS + 1, displayedSlotCount); i++) {
            if (i == displayedSlotCount - 1) {
                if (displayedSlotCount == MAX_DISPLAYED_SLOTS + 1) {
                    if (drawDots) {
                        PastelTooltipComponent.drawDottedSlot(context, n + 5 * 18, o);
                    } else {
                        PastelTooltipComponent.drawSlot(context, n + i * 18, o, i, ItemStack.EMPTY, textRenderer);
                    }
                } else {
                    if (itemStacks.size() - 1 < i) {
                        PastelTooltipComponent.drawSlot(context, n + i * 18, o, i, ItemStack.EMPTY, textRenderer);
                    } else {
                        PastelTooltipComponent.drawSlot(context, n + i * 18, o, i, itemStacks.get(i), textRenderer);
                    }
                }
            } else {
                PastelTooltipComponent.drawSlot(context, n + i * 18, o, i, itemStacks.get(i), textRenderer);
            }
        }
        PastelTooltipComponent.drawOutline(context, x, y, displayedSlotCount, 1);
    }

}

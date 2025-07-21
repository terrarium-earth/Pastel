package earth.terrarium.pastel.render.item;

import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;
import org.jetbrains.annotations.Nullable;

public class SlotEffectDecorator implements IItemDecorator {

    @Override
    public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
        var background = getEffect(stack);
        var bar = getBar(stack);
        var drawn = false;

        if (background != null)
            drawn = renderBackground(g, stack, x, y, background);

        if (bar != null)
            drawn = renderBar(g, stack, x, y, bar) || drawn;

        return drawn;
    }

    private static boolean renderBar(GuiGraphics g, ItemStack stack, int x, int y, ExtendedItemBar bar) {
        if (bar.barCount(stack) == 0)
            return false;

        var rendered = false;

        for (int i = 0; i < bar.barCount(stack); i++) {
            var signature = bar.getSignature(Minecraft.getInstance().player, stack, i);

            if (signature == ExtendedItemBar.PASS)
                continue;

            int k = x + signature.xPos();
            int l = y + signature.yPos();
            g.fill(
                RenderType.guiOverlay(), k, l, k + signature.length(), l + signature.backgroundHeight(),
                signature.backgroundColor()
            );
            g.fill(
                RenderType.guiOverlay(), k, l, k + signature.fill(), l + signature.fillHeight(), signature.fillColor());
            rendered = true;
        }

        return rendered;
    }

    private static boolean renderBackground(
        GuiGraphics g, ItemStack stack, int x, int y, SlotBackgroundEffect backgroundEffectProvider) {
        var player = Minecraft.getInstance().player;
        var delta = Minecraft.getInstance()
                             .getTimer()
                             .getGameTimeDeltaPartialTick(false);

        var type = backgroundEffectProvider.backgroundType(player, stack);
        var opacity = backgroundEffectProvider.getEffectOpacity(player, stack, delta);
        var color = (backgroundEffectProvider.getBackgroundColor(player, stack, delta) & 0x00FFFFFF) | (Math.round(
            opacity * 255) << 24);
        var transColor = color & 0x00FFFFFF;

        var time = Minecraft.getInstance().player.level()
                                                 .getGameTime() % 864000;
        var bounce = Math.sin((time + delta) / 20F) * 0.4F + 0.5F;
        var alpha = (int) Math.round(bounce * 255F);

        switch (type) {
            case NONE: {
                return false;
            }
            case BORDER_FADE: {
                g.fillGradient(x, y, x + 1, y + 15, transColor, color);
                g.fillGradient(x + 15, y, x + 16, y + 15, transColor, color);
                g.fillGradient(x, y + 15, x + 16, y + 16, color, color);
                break;
            }
            case FULL_PACKAGE:
            case PULSE: {
                g.fillGradient(x, y, x + 16, y + 16, transColor, transColor | (alpha << 24));
                if (type == SlotBackgroundEffect.SlotEffect.PULSE)
                    break;
            }
            case BORDER:
                g.renderOutline(x, y, 16, 16, color);
        }

        return true;
    }

    private static @Nullable SlotBackgroundEffect getEffect(ItemStack stack) {
        if (stack.getItem() instanceof SlotBackgroundEffect prov)
            return prov;

        else if (stack.getItem() instanceof BlockItem blockItem &&
                 blockItem.getBlock() instanceof SlotBackgroundEffect prov)
            return prov;

        return null;
    }

    private static @Nullable ExtendedItemBar getBar(ItemStack stack) {
        if (stack.getItem() instanceof ExtendedItemBar bar)
            return bar;
        return null;
    }
}

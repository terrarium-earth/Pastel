package earth.terrarium.pastel.compat.emi.widgets;

import dev.emi.emi.api.widget.ButtonWidget;
import dev.emi.emi.api.widget.WidgetTooltipHolder;
import dev.emi.emi.runtime.EmiDrawContext;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;

public class SaneButtonWidget extends ButtonWidget implements WidgetTooltipHolder<SaneButtonWidget> {

    private BiFunction<Integer, Integer, List<ClientTooltipComponent>> tooltipSupplier = (mouseX, mouseY) -> List.of();

    public SaneButtonWidget(
        int x, int y, int width, int height, int u, int v, ResourceLocation texture, BooleanSupplier isActive,
        ClickAction action
    ) {
        super(x, y, width, height, u, v, texture, isActive, action);
    }

    @Override
    public void render(GuiGraphics draw, int mouseX, int mouseY, float delta) {
        EmiDrawContext context = EmiDrawContext.wrap(draw);
        context.drawTexture(texture, this.x, this.y, this.u, this.v, this.width, this.height);
    }

    @Override
    public SaneButtonWidget tooltip(BiFunction<Integer, Integer, List<ClientTooltipComponent>> tooltipSupplier) {
        this.tooltipSupplier = tooltipSupplier;
        return this;
    }

    public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
        return tooltipSupplier.apply(mouseX, mouseY);
    }

}

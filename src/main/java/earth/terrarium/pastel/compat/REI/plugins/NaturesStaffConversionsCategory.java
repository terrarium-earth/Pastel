package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.registries.PastelItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(
    Dist.CLIENT
)
public class NaturesStaffConversionsCategory extends GatedDisplayCategory<NaturesStaffConversionsDisplay> {

    @Override
    public CategoryIdentifier<? extends NaturesStaffConversionsDisplay> getCategoryIdentifier() {
        return PastelPlugins.NATURES_STAFF;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(PastelItems.NATURES_STAFF.get());
    }

    @Override
    public Component getTitle() {
        return PastelItems.NATURES_STAFF
            .get()
            .getDescription();
    }

    @Override
    public void setupWidgets(
        Point startPoint,
        Rectangle bounds,
        List<Widget> widgets,
        @NotNull NaturesStaffConversionsDisplay display
    ) {
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets
            .add(
                Widgets
                    .createSlot(new Point(startPoint.x + 4, startPoint.y + 5))
                    .entries(
                        display
                            .getInputEntries()
                            .get(0)
                    )
                    .markInput()
            );
        widgets
            .add(
                Widgets
                    .createSlot(new Point(startPoint.x + 61, startPoint.y + 5))
                    .entries(
                        display
                            .getOutputEntries()
                            .get(0)
                    )
                    .disableBackground()
                    .markInput()
            );
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }

}

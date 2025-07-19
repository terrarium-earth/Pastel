package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.api.energy.color.InkColor;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

public interface GemstoneColor extends StringRepresentable {

    int getColor();

    Item getPowder();

    InkColor getInkColor();

    int getOffset();
}

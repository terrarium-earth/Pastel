package earth.terrarium.pastel.api.item;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

public interface GemstoneColor extends StringRepresentable {

    int getColor();

    Item getGemstonePowderItem();

}

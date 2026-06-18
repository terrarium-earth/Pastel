package earth.terrarium.pastel.items.conditional;

import earth.terrarium.pastel.api.item.GemstoneColor;
import net.minecraft.world.item.Item;

public class GemstonePowderItem extends Item {

    protected final GemstoneColor gemstoneColor;

    public GemstonePowderItem(Properties settings, GemstoneColor gemstoneColor) {
        super(settings);
        this.gemstoneColor = gemstoneColor;
    }

}

package earth.terrarium.pastel.helpers.level.collections;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

// if we ever port to 26.2 or higher, we can replace these explicit definitions
// with a constructor that converts a ColorCollection to a PastelInkColorCollection
public class VanillaColorCollections {
    public static final PastelInkColorCollection<Item> CONCRETE_ITEMS = new PastelInkColorCollection<>(
        Items.CYAN_CONCRETE,
        Items.LIGHT_BLUE_CONCRETE,
        Items.BLUE_CONCRETE,
        Items.PURPLE_CONCRETE,
        Items.MAGENTA_CONCRETE,
        Items.PINK_CONCRETE,
        Items.RED_CONCRETE,
        Items.ORANGE_CONCRETE,
        Items.YELLOW_CONCRETE,
        Items.LIME_CONCRETE,
        Items.GREEN_CONCRETE,
        Items.BROWN_CONCRETE,
        Items.BLACK_CONCRETE,
        Items.GRAY_CONCRETE,
        Items.LIGHT_GRAY_CONCRETE,
        Items.WHITE_CONCRETE
    );

    public static final PastelInkColorCollection<Item> CONCRETE_POWDER_ITEMS = new PastelInkColorCollection<>(
        Items.CYAN_CONCRETE_POWDER,
        Items.LIGHT_BLUE_CONCRETE_POWDER,
        Items.BLUE_CONCRETE_POWDER,
        Items.PURPLE_CONCRETE_POWDER,
        Items.MAGENTA_CONCRETE_POWDER,
        Items.PINK_CONCRETE_POWDER,
        Items.RED_CONCRETE_POWDER,
        Items.ORANGE_CONCRETE_POWDER,
        Items.YELLOW_CONCRETE_POWDER,
        Items.LIME_CONCRETE_POWDER,
        Items.GREEN_CONCRETE_POWDER,
        Items.BROWN_CONCRETE_POWDER,
        Items.BLACK_CONCRETE_POWDER,
        Items.GRAY_CONCRETE_POWDER,
        Items.LIGHT_GRAY_CONCRETE_POWDER,
        Items.WHITE_CONCRETE_POWDER
    );
}

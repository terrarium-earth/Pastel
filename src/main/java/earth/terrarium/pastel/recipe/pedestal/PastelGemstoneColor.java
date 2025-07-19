package earth.terrarium.pastel.recipe.pedestal;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.core.Registry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

public enum PastelGemstoneColor implements GemstoneColor, StringRepresentable {
    CYAN("cyan", InkColors.CYAN, 0),
    MAGENTA("magenta", InkColors.MAGENTA, 1),
    YELLOW("yellow", InkColors.YELLOW, 2),
    BLACK("black", InkColors.BLACK, 3),
    WHITE("white", InkColors.WHITE, 4);

    private final int color;
    private final InkColor inkColor;
    private final int offset;

    PastelGemstoneColor(String name, InkColor color, int offset) {
        this.offset = offset;
        Registry.register(PastelRegistries.GEMSTONE_COLOR, PastelCommon.locate(name), this);
        this.color = color.getColorInt();
        this.inkColor = color;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public InkColor getInkColor() {
        return inkColor;
    }

    @Override
    public Item getPowder() {
        switch (this) {
            case CYAN -> {
                return PastelItems.TOPAZ_POWDER.get();
            }
            case MAGENTA -> {
                return PastelItems.AMETHYST_POWDER.get();
            }
            case YELLOW -> {
                return PastelItems.CITRINE_POWDER.get();
            }
            case BLACK -> {
                return PastelItems.ONYX_POWDER.get();
            }
            case WHITE -> {
                return PastelItems.MOONSTONE_POWDER.get();
            }
            default -> throw new RuntimeException("Tried getting powder item for a color which does not have one");
        }
    }

    @Override
    public String getSerializedName() {
        return name();
    }
}

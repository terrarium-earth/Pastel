package earth.terrarium.pastel.recipe.pedestal;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.core.Registry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

public enum BuiltinGemstoneColor implements GemstoneColor, StringRepresentable {
	CYAN("cyan", InkColors.CYAN_COLOR),
	MAGENTA("magenta", InkColors.MAGENTA_COLOR),
	YELLOW("yellow", InkColors.YELLOW_COLOR),
	BLACK("black", InkColors.BLACK_COLOR),
	WHITE("white", InkColors.WHITE_COLOR);
	
	private final int color;
	
	BuiltinGemstoneColor(String name, int color) {
		Registry.register(PastelRegistries.GEMSTONE_COLOR, PastelCommon.locate(name), this);
		this.color = color;
	}

	@Override
	public int getColor() {
		return this.color;
	}

	@Override
	public Item getGemstonePowderItem() {
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
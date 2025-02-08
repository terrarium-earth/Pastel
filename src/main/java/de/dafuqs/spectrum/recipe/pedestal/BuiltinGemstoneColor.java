package de.dafuqs.spectrum.recipe.pedestal;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

public enum BuiltinGemstoneColor implements GemstoneColor, StringIdentifiable {
	CYAN("cyan", DyeColor.CYAN),
	MAGENTA("magenta", DyeColor.MAGENTA),
	YELLOW("yellow", DyeColor.YELLOW),
	BLACK("black", DyeColor.BLACK),
	WHITE("white", DyeColor.WHITE);

	private final DyeColor dyeColor;
	
	BuiltinGemstoneColor(String name, DyeColor dyeColor) {
		Registry.register(SpectrumRegistries.GEMSTONE_COLOR, SpectrumCommon.locate(name), this);
		this.dyeColor = dyeColor;
	}

	public static BuiltinGemstoneColor of(DyeColor color) {
		switch (color) {
			case CYAN -> {
				return BuiltinGemstoneColor.CYAN;
			}
			case MAGENTA -> {
				return BuiltinGemstoneColor.MAGENTA;
			}
			case YELLOW -> {
				return BuiltinGemstoneColor.YELLOW;
			}
			case BLACK -> {
				return BuiltinGemstoneColor.BLACK;
			}
			case WHITE -> {
				return BuiltinGemstoneColor.WHITE;
			}
			default -> throw new RuntimeException("Tried getting powder item for a color which does not have one");
		}
	}

	@Override
	public DyeColor getDyeColor() {
		return this.dyeColor;
	}

	@Override
	public Item getGemstonePowderItem() {
		switch (this) {
			case CYAN -> {
				return SpectrumItems.TOPAZ_POWDER;
			}
			case MAGENTA -> {
				return SpectrumItems.AMETHYST_POWDER;
			}
			case YELLOW -> {
				return SpectrumItems.CITRINE_POWDER;
			}
			case BLACK -> {
				return SpectrumItems.ONYX_POWDER;
			}
			case WHITE -> {
				return SpectrumItems.MOONSTONE_POWDER;
			}
			default -> throw new RuntimeException("Tried getting powder item for a color which does not have one");
		}
	}

	@Override
	public String asString() {
		return name();
	}
}
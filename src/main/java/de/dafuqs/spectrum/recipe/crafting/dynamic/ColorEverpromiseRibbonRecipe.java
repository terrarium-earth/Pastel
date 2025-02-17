package de.dafuqs.spectrum.recipe.crafting.dynamic;

import de.dafuqs.spectrum.items.*;
import de.dafuqs.spectrum.items.magic_items.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.minecraft.world.*;

public class ColorEverpromiseRibbonRecipe extends SpecialCraftingRecipe {
	
	public ColorEverpromiseRibbonRecipe() {
		super(CraftingRecipeCategory.MISC);
	}
	
	@Override
	public boolean matches(CraftingRecipeInput input, World world) {
		boolean ribbonFound = false;
		boolean pigmentFound = false;
		
		for (int i = 0; i < input.getSize(); ++i) {
			ItemStack itemStack = input.getStackInSlot(i);
			if (!itemStack.isEmpty()) {
				if (itemStack.getItem() instanceof EverpromiseRibbonItem) {
					if (!itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
						return false;
					}
					if (ribbonFound) {
						return false;
					} else {
						ribbonFound = true;
					}
				} else if (itemStack.getItem() instanceof PigmentItem) {
					if (pigmentFound) {
						return false;
					} else {
						pigmentFound = true;
					}
				} else {
					return false;
				}
			}
		}
		
		return ribbonFound && pigmentFound;
	}
	
	@Override
	public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registryLookup) {
		ItemStack ribbon = null;
		PigmentItem pigment = null;
		
		
		for (int i = 0; i < input.getSize(); ++i) {
			ItemStack stack = input.getStackInSlot(i);
			if (stack.getItem() instanceof EverpromiseRibbonItem) {
				ribbon = stack;
			}
			if (stack.getItem() instanceof PigmentItem pigmentItem) {
				pigment = pigmentItem;
			}
		}
		
		if (ribbon == null || pigment == null) {
			return ItemStack.EMPTY;
		}
		
		ribbon = ribbon.copy();
		ribbon.setCount(1);
		
		Text text = ribbon.getName();
		if (text instanceof MutableText mutableText) {
			TextColor newColor = TextColor.fromRgb(pigment.getInkColor().getColorInt());
			Text newName = mutableText.setStyle(mutableText.getStyle().withColor(newColor));
			ribbon.set(DataComponentTypes.CUSTOM_NAME, newName);
		}
		
		return ribbon;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.COLOR_EVERPROMISE_RIBBON_SERIALIZER;
	}
	
}

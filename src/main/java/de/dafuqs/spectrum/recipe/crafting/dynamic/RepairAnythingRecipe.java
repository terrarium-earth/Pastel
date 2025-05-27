package de.dafuqs.spectrum.recipe.crafting.dynamic;

import de.dafuqs.spectrum.registries.SpectrumItemTags;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RepairAnythingRecipe extends CustomRecipe {
	
	private static final Ingredient MOONSTRUCK_NECTAR = Ingredient.of(SpectrumItems.MOONSTRUCK_NECTAR.get());
	
	public RepairAnythingRecipe() {
		super(CraftingBookCategory.MISC);
	}
	
	@Override
	public boolean matches(CraftingInput input, Level world) {
		boolean nectarFound = false;
		boolean itemFound = false;
		
		for (int j = 0; j < input.size(); ++j) {
			ItemStack itemStack = input.getItem(j);
			if (!itemStack.isEmpty()) {
				if (MOONSTRUCK_NECTAR.test(itemStack)) {
					if (nectarFound) {
						return false;
					}
					nectarFound = true;
				} else if (itemStack.isDamageableItem() && itemStack.isDamaged() && !itemStack.is(SpectrumItemTags.INDESTRUCTIBLE_BLACKLISTED)) {
					if (itemFound) {
						return false;
					}
					itemFound = true;
				}
			}
		}
		
		return nectarFound && itemFound;
	}
	
	@Override
	public ItemStack assemble(CraftingInput input, HolderLookup.Provider registryLookup) {
		ItemStack itemStack = ItemStack.EMPTY;
		for (int j = 0; j < input.size(); ++j) {
			itemStack = input.getItem(j);
			if (!itemStack.isEmpty() && !MOONSTRUCK_NECTAR.test(itemStack)) {
				break;
			}
		}
		
		if (itemStack.isDamageableItem() && itemStack.isDamaged() && !itemStack.is(SpectrumItemTags.INDESTRUCTIBLE_BLACKLISTED)) {
			ItemStack returnStack = itemStack.copy();
			int damage = returnStack.getDamageValue();
			int maxDamage = returnStack.getMaxDamage();
			
			int newDamage = Math.max(0, damage - maxDamage / 3);
			returnStack.setDamageValue(newDamage);
			return returnStack;
		} else {
			return ItemStack.EMPTY;
		}
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.REPAIR_ANYTHING_SERIALIZER;
	}
	
}

package earth.terrarium.pastel.recipe.crafting.dynamic;

import earth.terrarium.pastel.blocks.present.PresentBlock;
import earth.terrarium.pastel.blocks.present.PresentBlockItem;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class WrapPresentRecipe extends CustomRecipe {
	
	public WrapPresentRecipe() {
		super(CraftingBookCategory.MISC);
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = NonNullList.withSize(1, Ingredient.EMPTY);
		list.set(0, Ingredient.of(PastelBlocks.PRESENT.get().asItem().getDefaultInstance()));
		return list;
	}
	
	@Override
	public ItemStack getResultItem(HolderLookup.Provider registryLookup) {
		ItemStack stack = PastelBlocks.PRESENT.get().asItem().getDefaultInstance();
		PresentBlockItem.wrap(stack, PresentBlock.WrappingPaper.RED, Map.of());
		return stack;
	}
	
	@Override
	public boolean matches(@NotNull CraftingInput input, Level world) {
		boolean presentItemFound = false;
		boolean wrappingItemFound = false;
		
		for (int j = 0; j < input.size(); ++j) {
			ItemStack itemStack = input.getItem(j);
			if (!itemStack.isEmpty()) {
				if (itemStack.getItem() instanceof PresentBlockItem) {
					if (presentItemFound || PresentBlockItem.isWrapped(itemStack)) {
						return false;
					}
					presentItemFound = true;
				} else if (!wrappingItemFound && getPresentVariantForStack(itemStack) != null) {
					wrappingItemFound = true;
				} else if (!(itemStack.getItem() instanceof PigmentItem)) {
					return false;
				}
			}
		}
		
		return presentItemFound;
	}
	
	@Override
	public ItemStack assemble(@NotNull CraftingInput input, HolderLookup.Provider registryLookup) {
		ItemStack presentStack = ItemStack.EMPTY;
		PresentBlock.WrappingPaper wrappingPaper = PresentBlock.WrappingPaper.RED;
		Map<Integer, Integer> colors = new HashMap<>();
		
		for (int j = 0; j < input.size(); ++j) {
			ItemStack stack = input.getItem(j);
			if (stack.getItem() instanceof PresentBlockItem) {
				presentStack = stack.copy();
			} else if (stack.getItem() instanceof PigmentItem pigmentItem) {
				int color = pigmentItem.getInkColor().getColorInt();
				if (colors.containsKey(color)) {
					colors.put(color, colors.get(color) + 1);
				} else {
					colors.put(color, 1);
				}
			}
			PresentBlock.WrappingPaper stackWrappingPaper = getPresentVariantForStack(stack);
			if (stackWrappingPaper != null) {
				wrappingPaper = stackWrappingPaper;
			}
		}
		
		if (!presentStack.isEmpty()) {
			PresentBlockItem.wrap(presentStack, wrappingPaper, colors);
		}
		return presentStack;
	}
	
	public @Nullable PresentBlock.WrappingPaper getPresentVariantForStack(@NotNull ItemStack stack) {
		Item item = stack.getItem();
		if (item == Items.RED_DYE) {
			return PresentBlock.WrappingPaper.RED;
		} else if (item == Items.BLUE_DYE) {
			return PresentBlock.WrappingPaper.BLUE;
		} else if (item == Items.CYAN_DYE) {
			return PresentBlock.WrappingPaper.CYAN;
		} else if (item == Items.GREEN_DYE) {
			return PresentBlock.WrappingPaper.GREEN;
		} else if (item == Items.PURPLE_DYE) {
			return PresentBlock.WrappingPaper.PURPLE;
		} else if (item == Items.CAKE) {
			return PresentBlock.WrappingPaper.CAKE;
		} else if (stack.is(ItemTags.FLOWERS)) {
			return PresentBlock.WrappingPaper.STRIPED;
		} else if (item == Items.FIREWORK_STAR) {
			return PresentBlock.WrappingPaper.STARRY;
		} else if (item == Items.SNOWBALL) {
			return PresentBlock.WrappingPaper.WINTER;
		} else if (item == Items.SPORE_BLOSSOM) {
			return PresentBlock.WrappingPaper.PRIDE;
		}
		return null;
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 1;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.WRAP_PRESENT_SERIALIZER;
	}
	
}

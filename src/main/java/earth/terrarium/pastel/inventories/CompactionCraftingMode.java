package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.api.item.ItemReference;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public enum CompactionCraftingMode {
    X1(1, 1),
    X2(2, 2),
    X3(3, 3);

    private static final Map<CompactionCraftingMode, CompactionRecipeCache> CACHE = new EnumMap<>(
        CompactionCraftingMode.class);

    private final int width;
    private final int height;

    CompactionCraftingMode(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return width * height;
    }

    public CompactionCraftingMode next() {
        return CompactionCraftingMode.values()[(this.ordinal() + 1) % values().length];
    }

    public CraftingInput.Positioned createRecipeInput(ItemStack variant) {
        ItemStack stack = variant;
        List<ItemStack> inputs = new ArrayList<>(getSize());
        for (int i = 0; i < getSize(); i++) {
            inputs.add(stack);
        }
        return CraftingInput.ofPositioned(width, height, inputs);
    }

    public CompactionRecipeCache getCache() {
        return CACHE.computeIfAbsent(this, m -> new CompactionRecipeCache());
    }

    public static void clearCache() {
        CACHE.clear();
    }
    public static class CompactionRecipeCache {
        private final Map<ItemReference, Optional<RecipeHolder<CraftingRecipe>>> cache = new HashMap<>();

        public Optional<Optional<RecipeHolder<CraftingRecipe>>> get(ItemReference input) {
            return Optional.ofNullable(cache.get(input));
        }

        public void put(ItemReference input, Optional<RecipeHolder<CraftingRecipe>> recipe) {
            cache.put(input, recipe);
        }
    }
}


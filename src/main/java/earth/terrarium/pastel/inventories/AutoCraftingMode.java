package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.api.item.ItemReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AutoCraftingMode {
	X1(1, 1),
	X2(2, 2),
	X3(3, 3);

	private static final Map<AutoCraftingMode, Map<ResourceLocation, ItemReference>> CACHE = new EnumMap<>(AutoCraftingMode.class);

	private final int width;
	private final int height;
	
	AutoCraftingMode(int width, int height) {
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
	
	public AutoCraftingMode next() {
		return AutoCraftingMode.values()[(this.ordinal() + 1) % values().length];
	}
	
	public CraftingInput.Positioned createRecipeInput(ItemStack variant) {
		ItemStack stack = variant;
		List<ItemStack> inputs = new ArrayList<>(getSize());
		for (int i = 0; i < getSize(); i++) {
			inputs.add(stack);
		}
		return CraftingInput.ofPositioned(width, height, inputs);
	}

	public static Map<ResourceLocation, ItemReference> getCache(AutoCraftingMode mode) {
		return CACHE.computeIfAbsent(mode, m -> new HashMap<>());
	}

	public static void clearCache() {
		CACHE.clear();
	}
}


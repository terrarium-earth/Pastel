package earth.terrarium.pastel.recipe.titration_barrel.dynamic;

import earth.terrarium.pastel.capabilities.item.*;
import earth.terrarium.pastel.registries.PastelItems;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationData;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CheongRecipe extends TitrationBarrelRecipe {

    public static final Item TAPPING_ITEM = Items.GLASS_BOTTLE;
    public static final int MIN_FERMENTATION_TIME_HOURS = 4;
    public static final ItemStack OUTPUT_STACK = getDefaultStackWithCount(PastelItems.CHEONG.get(), 4);
    public static final ItemStack OUTPUT_STACK_MERMAIDS = getDefaultStackWithCount(PastelItems.MERMAIDS_JAM.get(), 4);

    public static final List<IngredientStack> INGREDIENT_STACKS = new ArrayList<>() {{
        add(IngredientStack.ofTag(PastelItemTags.FRUITS, 8));
        add(IngredientStack.ofItems(Items.SUGAR, 16));
    }};

    public CheongRecipe() {
        super(
            "", false, Optional.empty(), INGREDIENT_STACKS, FluidIngredient.of(Fluids.WATER),
            OUTPUT_STACK, TAPPING_ITEM, MIN_FERMENTATION_TIME_HOURS, FermentationData.DEFAULT
        );
    }

    @Override
    public ItemStack tap(FriendlyStackHandler inventory, long secondsFermented, float downfall) {
        ItemStack result = inventory.hasAnyOf(Collections.singleton(PastelItems.MERMAIDS_GEM.get()))
                           ? OUTPUT_STACK_MERMAIDS.copy()
                           : OUTPUT_STACK.copy();
        result.setCount(1);
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.TITRATION_BARREL_CHEONG;
    }

}

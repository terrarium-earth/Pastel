package earth.terrarium.pastel.recipe.pedestal.dynamic;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.pedestal.PedestalRecipeInput;
import earth.terrarium.pastel.entity.entity.EnderCanvasEntity;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.RawShapedPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapedPedestalRecipe;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Optional;

public class EnderCanvasLargeRecipe extends ShapedPedestalRecipe {

    public EnderCanvasLargeRecipe() {
        super(
            "",
            false,
            Optional.of(PastelAdvancements.Unlocks.Items.ENDER_SPLICE),
            PedestalTier.ADVANCED,
            new ShapedRecipePattern(3, 3, generateInputs(), Optional.empty()),
            Map
                .of(
                    PastelGemstoneColor.MAGENTA,
                    0,
                    PastelGemstoneColor.CYAN,
                    0,
                    PastelGemstoneColor.YELLOW,
                    0
                ),
            PastelItems.ENDER_CANVAS.get().getDefaultInstance(),
            0.0F,
            20,
            true,
            true
        );
    }

    private static NonNullList<Ingredient> generateInputs() {
        return NonNullList
            .of(
                Ingredient.EMPTY,
                Ingredient.of(Items.STICK),
                Ingredient.of(ItemTags.WOOL),
                Ingredient.of(Items.STICK),
                Ingredient.of(ItemTags.WOOL),
                Ingredient.of(PastelItems.ENDER_CANVAS.get()),
                Ingredient.of(ItemTags.WOOL),
                Ingredient.of(Items.STICK),
                Ingredient.of(ItemTags.WOOL),
                Ingredient.of(Items.STICK)
            );
    }

    @Override
    public ItemStack assemble(PedestalRecipeInput input, HolderLookup.Provider wrapperLookup) {
        ItemStack canvas = input.getItem(4);
        var copy = canvas.copy();
        copy.set(PastelDataComponentTypes.ENDER_CANVAS_VARIANT, EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE);
        return copy;
    }

    @Override
    public boolean matches(PedestalRecipeInput inv, Level world) {
        return super.matches(inv, world) && inv
            .getItem(4)
            .get(PastelDataComponentTypes.ENDER_CANVAS_VARIANT) == EnderCanvasEntity.EnderCanvasVariant.LANDSCAPESMALL;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.PEDESTAL_ENDER_CANVAS_LARGE;
    }
}

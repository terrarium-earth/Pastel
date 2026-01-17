package earth.terrarium.pastel.recipe.pedestal.dynamic;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.pedestal.PedestalRecipeInput;
import earth.terrarium.pastel.components.EnderSpliceComponent;
import earth.terrarium.pastel.entity.entity.EnderCanvasEntity;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.RawShapedPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapedPedestalRecipe;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Map;
import java.util.Optional;

public class EnderCanvasLargeRecipe extends ShapedPedestalRecipe {

    public EnderCanvasLargeRecipe() {
        super(
            "", false, Optional.of(PastelAdvancements.Unlocks.Items.ENDER_SPLICE), PedestalTier.ADVANCED,
            new RawShapedPedestalRecipe(3, 3, generateInputs(), Optional.empty()), Map.of(
                PastelGemstoneColor.MAGENTA, 0, PastelGemstoneColor.CYAN, 0, PastelGemstoneColor.YELLOW, 0),
            PastelItems.ENDER_CANVAS.get().getDefaultInstance(), 0.0F, 20, true, true
        );
    }

    private static NonNullList<IngredientStack> generateInputs() {
        return NonNullList.of(
            IngredientStack.EMPTY,
            IngredientStack.ofItems(Items.STICK),
            IngredientStack.ofTag(ItemTags.WOOL),
            IngredientStack.ofItems(Items.STICK),
            IngredientStack.ofTag(ItemTags.WOOL),
            IngredientStack.ofItems(PastelItems.ENDER_CANVAS.get()),
            IngredientStack.ofTag(ItemTags.WOOL),
            IngredientStack.ofItems(Items.STICK),
            IngredientStack.ofTag(ItemTags.WOOL),
            IngredientStack.ofItems(Items.STICK)
        );
    }

    @Override
    public ItemStack assemble(PedestalRecipeInput input, HolderLookup.Provider wrapperLookup) {
        ItemStack canvas = input.getItem(4);
        canvas.set(PastelDataComponentTypes.ENDER_CANVAS_VARIANT, EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE);
        return canvas.copy();
    }

    @Override
    public boolean matches(PedestalRecipeInput inv, Level world) {
        return super.matches(inv, world) && inv.getItem(4)
                                               .get(PastelDataComponentTypes.ENDER_CANVAS_VARIANT) ==
                                            EnderCanvasEntity.EnderCanvasVariant.LANDSCAPESMALL;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.PEDESTAL_ENDER_CANVAS_LARGE;
    }
}

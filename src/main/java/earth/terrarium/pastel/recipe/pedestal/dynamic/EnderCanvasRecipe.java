package earth.terrarium.pastel.recipe.pedestal.dynamic;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.pedestal.PedestalRecipeInput;
import earth.terrarium.pastel.components.EnderSpliceComponent;
import earth.terrarium.pastel.entity.entity.EnderCanvasEntity;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.items.item_frame.EnderCanvasItem;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.RawShapedPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapedPedestalRecipe;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Optional;

public class EnderCanvasRecipe extends ShapedPedestalRecipe {
    public EnderCanvasRecipe() {
        super(
            "",
            false,
            Optional.of(PastelAdvancements.Unlocks.Items.ENDER_SPLICE),
            PedestalTier.ADVANCED,
            new RawShapedPedestalRecipe(3, 3, generateInputs(), Optional.empty()),
            Map
                .of(
                    PastelGemstoneColor.MAGENTA,
                    2,
                    PastelGemstoneColor.CYAN,
                    3,
                    PastelGemstoneColor.YELLOW,
                    1
                ),
            PastelItems.ENDER_CANVAS
                .get()
                .getDefaultInstance(),
            2.0F,
            240,
            true,
            true
        );
    }

    private static NonNullList<IngredientStack> generateInputs() {
        return NonNullList
            .of(
                IngredientStack.EMPTY,
                IngredientStack.ofItems(Items.STICK),
                IngredientStack.ofTag(ItemTags.WOOL),
                IngredientStack.ofItems(Items.STICK),
                IngredientStack.ofItems(PastelItems.NEOLITH.get()),
                IngredientStack.ofItems(PastelItems.ENDER_SPLICE.get()),
                IngredientStack.ofItems(PastelItems.NEOLITH.get()),
                IngredientStack.ofItems(Items.STICK),
                IngredientStack.ofTag(ItemTags.WOOL),
                IngredientStack.ofItems(Items.STICK)
            );
    }

    @Override
    public ItemStack assemble(PedestalRecipeInput input, HolderLookup.Provider wrapperLookup) {
        ItemStack splice = input.getItem(4);
        EnderSpliceComponent spliceData = splice
            .getComponents()
            .getOrDefault(
                PastelDataComponentTypes.ENDER_SPLICE,
                EnderSpliceComponent.DEFAULT
            );
        var out = this.output.copy();
        out.set(PastelDataComponentTypes.ENDER_SPLICE, spliceData);
        if (Ench.hasEnchantment(wrapperLookup, PastelEnchantments.RESONANCE, splice))
            out.enchant(wrapperLookup.holderOrThrow(PastelEnchantments.RESONANCE), 1);
        out
            .set(
                PastelDataComponentTypes.ENDER_CANVAS_VARIANT,
                spliceData
                    .targetGameProfile()
                    .isPresent()
                        ? EnderCanvasEntity.EnderCanvasVariant.PORTRAIT
                        : EnderCanvasEntity.EnderCanvasVariant.LANDSCAPESMALL
            );
        return out;
    }

    @Override
    public boolean matches(PedestalRecipeInput inv, Level world) {
        EnderSpliceComponent spliceData = inv
            .getItem(4)
            .getComponents()
            .getOrDefault(
                PastelDataComponentTypes.ENDER_SPLICE,
                EnderSpliceComponent.DEFAULT
            );
        return super.matches(inv, world) && (spliceData
            .pos()
            .isPresent() && spliceData
                .dimension()
                .isPresent()) || spliceData
                    .targetGameProfile()
                    .isPresent();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.PEDESTAL_ENDER_CANVAS;
    }
}

package earth.terrarium.pastel.recipe.pedestal.dynamic;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.pedestal.PedestalRecipeInput;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.RawShapedPedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.ShapedPedestalRecipe;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class StarCandyRecipe extends ShapedPedestalRecipe {

    public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("unlocks/food/star_candy");

    public static final float PURPLE_STAR_CANDY_CHANCE = 0.01F;

    public StarCandyRecipe() {
        super(
            "",
            false,
            Optional.of(UNLOCK_IDENTIFIER),
            PedestalTier.BASIC,
            new ShapedRecipePattern(3, 3, generateInputs(), Optional.empty()),
            Map.of(PastelGemstoneColor.YELLOW, 1),
            PastelItems.STAR_CANDY.get().getDefaultInstance(),
            1.0F,
            20,
            false,
            false
        );
    }

    @Override
    public ItemStack assemble(PedestalRecipeInput input, HolderLookup.Provider wrapperLookup) {
        @Nullable Player player = input.getPlayer();
        if (player != null && player.getRandom().nextFloat() < PURPLE_STAR_CANDY_CHANCE + player
            .getAttributeValue(Attributes.LUCK)) {
            return PastelItems.ENCHANTED_STAR_CANDY.get().getDefaultInstance();
        }
        return this.output.copy();
    }

    private static NonNullList<Ingredient> generateInputs() {
        return NonNullList
            .of(
                Ingredient.EMPTY,
                Ingredient.of(Items.SUGAR),
                Ingredient.of(Items.SUGAR),
                Ingredient.of(Items.SUGAR),
                Ingredient.of(PastelItems.STARDUST.get()),
                Ingredient.of(PastelItems.STARDUST.get()),
                Ingredient.of(PastelItems.STARDUST.get()),
                Ingredient.of(PastelItems.AMARANTH_GRAINS.get()),
                Ingredient.of(PastelItems.AMARANTH_GRAINS.get()),
                Ingredient.of(PastelItems.AMARANTH_GRAINS.get())
            );
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.PEDESTAL_STAR_CANDY;
    }

}

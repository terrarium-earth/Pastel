package earth.terrarium.pastel.recipe.fluid_converting;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DragonrotConvertingRecipe extends FluidConvertingRecipe {

    public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("hidden/interact_with_dragonrot");
    private static final Set<Item> outputItems = new HashSet<>();

    public DragonrotConvertingRecipe(
        String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier,
        @NotNull Ingredient inputIngredient, ItemStack outputItemStack
    ) {
        super(group, secret, requiredAdvancementIdentifier, inputIngredient, outputItemStack);
        outputItems.add(outputItemStack.getItem());
    }

    public static boolean isExistingOutputItem(@NotNull ItemStack itemStack) {
        return outputItems.contains(itemStack.getItem());
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(PastelItems.DRAGONROT_BUCKET.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.DRAGONROT_CONVERTING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return PastelRecipeTypes.DRAGONROT_CONVERTING;
    }

    @Override
    public ResourceLocation getRecipeTypeUnlockIdentifier() {
        return UNLOCK_IDENTIFIER;
    }

    @Override
    public String getRecipeTypeShortID() {
        return "dragonrot_converting";
    }

}

package earth.terrarium.pastel.data.recipe.builder.enchanter;

import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.recipe.enchanter.EnchanterCraftingRecipe;
import earth.terrarium.pastel.registries.PastelResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

// NOTE: Crafting & Upgrade don't share a base class because they don't really need to
public final class EnchanterCraftingRecipeBuilder extends GatedRecipeBuilder<EnchanterCraftingRecipeBuilder> {
    private final Ingredient centerInput;
    private final List<Ingredient> ingredientList = new ArrayList<>();
    private int craftingTime = 200;
    private int requiredExperience = 0;
    private boolean noDiscounts = false;
    private boolean copyComponents = false;
    private final String defaultName;

    public EnchanterCraftingRecipeBuilder(
            Ingredient centerInput,
            ItemStack result
    ) {
        this(centerInput, result, result.getItemHolder().getKey().location().getPath());
    }

    public EnchanterCraftingRecipeBuilder(
            Ingredient centerInput,
            ItemStack result,
            String defaultName
    ) {
        super(result);
        this.defaultName = defaultName;
        this.centerInput = centerInput;
    }

    public static EnchanterCraftingRecipeBuilder forEnchantment(HolderLookup.Provider lookup, ResourceKey<Enchantment> enchantment) {
        var stack = new ItemStack(Items.ENCHANTED_BOOK);
        var enchant = lookup.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);
        stack.enchant(enchant, 1);
        var ret = new EnchanterCraftingRecipeBuilder(Ingredient.of(Items.BOOK), stack, enchantment.location().getPath());
        ret.neoCondition(
                new PastelResourceConditions.EnchantmentsExistResourceCondition(List.of(enchantment))
        );
        return ret;
    }

    @Override
    public String getDefaultName() {
        return this.defaultName;
    }


    public EnchanterCraftingRecipeBuilder requires(Ingredient ingredient) {
        this.ingredientList.add(ingredient);
        return this;
    }

    public EnchanterCraftingRecipeBuilder requires(ItemLike item) {
        return requires(Ingredient.of(item));
    }

    public EnchanterCraftingRecipeBuilder requires(TagKey<Item> tag) {
        return requires(Ingredient.of(tag));
    }

    public EnchanterCraftingRecipeBuilder craftingTime(int value) {
        this.craftingTime = value;
        return this;
    }

    public EnchanterCraftingRecipeBuilder requiredExperience(int value) {
        this.requiredExperience = value;
        return this;
    }

    public EnchanterCraftingRecipeBuilder noDiscounts() {
        this.noDiscounts = true;
        return this;
    }

    public EnchanterCraftingRecipeBuilder copyComponents() {
        this.copyComponents = true;
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        if (ingredientList.isEmpty() || 8 % ingredientList.size() != 0) {
            throw new IllegalStateException("Must specify 8 ingredients (or a divisor of 8 ingredients) for enchanter crafting!");
        }

        var finalList = new ArrayList<Ingredient>();
        finalList.add(centerInput);
        for (var x = 0; x < (8 / ingredientList.size()); x++) {
            finalList.addAll(ingredientList);
        }

        saveHelperGated(
                recipeOutput,
                id,
                daId ->
                        new EnchanterCraftingRecipe(
                                this.group,
                                this.secret,
                                daId,
                                finalList,
                                this.result,
                                this.craftingTime,
                                this.requiredExperience,
                                this.noDiscounts,
                                this.copyComponents
                        )
        );
    }
}

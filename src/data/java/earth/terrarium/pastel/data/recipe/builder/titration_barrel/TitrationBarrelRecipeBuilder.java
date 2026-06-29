package earth.terrarium.pastel.data.recipe.builder.titration_barrel;

import earth.terrarium.pastel.components.InfusedBeverageComponent;
import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationData;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationStatusEffectEntry;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class TitrationBarrelRecipeBuilder extends GatedRecipeBuilder<TitrationBarrelRecipeBuilder> {
    private final NonNullList<SizedIngredient> ingredients = NonNullList.create();
    private final FluidIngredient fluid;
    private final String defaultName;
    private Item item = Items.AIR;
    private int minFermentationTimeHours = 24;
    private float fermentationSpeedMod = 1f;
    private float angelsSharePercentPerMcDay = 0.1f;
    private final List<FermentationStatusEffectEntry> statusEffectEntries = new ArrayList<>();

    public TitrationBarrelRecipeBuilder(
            String name,
            FluidIngredient fluid,
            ItemStack result
    ) {
        super(result);
        this.fluid = fluid;
        this.defaultName = name;
    }

    public TitrationBarrelRecipeBuilder(
            FluidIngredient fluid,
            ItemStack result
    ) {
        this(nameFromResult(result.getItem()), fluid, result);
    }

    public TitrationBarrelRecipeBuilder(
            Fluid fluid,
            ItemStack result
    ) {
        this(FluidIngredient.of(fluid), result);
    }

    @Override
    public String getDefaultName() {
        return this.defaultName;
    }

    public static TitrationBarrelRecipeBuilder infusedBeverage(Fluid fluid, InfusedBeverageComponent component, int count) {
        return infusedBeverage(FluidIngredient.of(fluid), component, count);
    }

    public static TitrationBarrelRecipeBuilder infusedBeverage(FluidIngredient fluid, InfusedBeverageComponent component, int count) {
        var stack = new ItemStack(PastelItems.INFUSED_BEVERAGE, count, DataComponentPatch.builder().set(PastelDataComponentTypes.INFUSED_BEVERAGE, component).build());

        return new TitrationBarrelRecipeBuilder(component.variant(), fluid, stack);
    }

    public TitrationBarrelRecipeBuilder requires(SizedIngredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public TitrationBarrelRecipeBuilder requires(Ingredient ingredient, int count) {
        return requires(new SizedIngredient(ingredient, count));
    }

    public TitrationBarrelRecipeBuilder requires(Ingredient ingredient) {
        return requires(ingredient, 1);
    }

    public TitrationBarrelRecipeBuilder requires(ItemLike item, int count) {
        return requires(Ingredient.of(item), count);
    }

    public TitrationBarrelRecipeBuilder requires(ItemLike item) {
        return requires(item, 1);
    }

    public TitrationBarrelRecipeBuilder requires(TagKey<Item> tag, int count) {
        return requires(Ingredient.of(tag), count);
    }

    public TitrationBarrelRecipeBuilder requires(TagKey<Item> tag) {
        return requires(tag, 1);
    }

    public TitrationBarrelRecipeBuilder minFermentationTimeHours(int value) {
        this.minFermentationTimeHours = value;
        return this;
    }

    public TitrationBarrelRecipeBuilder fermentationSpeedMod(float value) {
        this.fermentationSpeedMod = value;
        return this;
    }

    public TitrationBarrelRecipeBuilder angelsSharePercentPerMcDay(float value) {
        this.angelsSharePercentPerMcDay = value;
        return this;
    }

    public TitrationBarrelRecipeBuilder tappingItem(ItemLike item) {
        this.item = item.asItem();
        return this;
    }


    public FermentationStatusEffectEntryBuilderForBarrel statusEffect(Holder<MobEffect> effect, int baseDuration) {
        return new FermentationStatusEffectEntryBuilderForBarrel(effect, baseDuration);
    }

    public TitrationBarrelRecipeBuilder acceptEffect(FermentationStatusEffectEntry entry) {
        this.statusEffectEntries.add(entry);
        return this;
    }

    public final class FermentationStatusEffectEntryBuilderForBarrel extends FermentationStatusEffectEntryBuilder<FermentationStatusEffectEntryBuilderForBarrel> {

        public FermentationStatusEffectEntryBuilderForBarrel(
                Holder<MobEffect> statusEffect,
                int baseDuration
        ) {
            super(statusEffect, baseDuration);
        }

        public TitrationBarrelRecipeBuilder submit() {
            statusEffectEntries.add(this.build());
            return TitrationBarrelRecipeBuilder.this;
        }
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        saveHelperGated(
                recipeOutput,
                id,
                daId ->
                        new TitrationBarrelRecipe(
                                this.group,
                                this.secret,
                                daId,
                                this.ingredients,
                                this.fluid,
                                this.result,
                                this.item,
                                this.minFermentationTimeHours,
                                new FermentationData(this.fermentationSpeedMod, this.angelsSharePercentPerMcDay, this.statusEffectEntries)
                        )
        );
    }
}

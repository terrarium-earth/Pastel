package earth.terrarium.pastel.data.recipe.builder.titration_barrel;

import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationData;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationStatusEffectEntry;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
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
    private Item item = Items.AIR;
    private int minFermentationTimeHours = 24;
    private float fermentationSpeedMod = 1f;
    private float angelsSharePercentPerMcDay = 0.1f;
    private final List<FermentationStatusEffectEntry> statusEffectEntries = new ArrayList<>();

    public TitrationBarrelRecipeBuilder(
            FluidIngredient fluid,
            ItemStack result
    ) {
        super(result);
        this.fluid = fluid;
    }

    public TitrationBarrelRecipeBuilder(
            Fluid fluid,
            ItemStack result
    ) {
        this(FluidIngredient.of(fluid), result);
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

    public FermentationStatusEffectEntryBuilder statusEffect(MobEffect effect, int baseDuration) {
        return new FermentationStatusEffectEntryBuilder(effect, baseDuration);
    }

    public FermentationStatusEffectEntryBuilder statusEffect(Holder<MobEffect> effect, int baseDuration) {
        return statusEffect(effect.value(), baseDuration);
    }

    private static IngredientStack sizedToStack(SizedIngredient sized) {
        return new IngredientStack(sized.ingredient(), sized.count());
    }

    public final class FermentationStatusEffectEntryBuilder {
        private final MobEffect statusEffect;
        private final int baseDuration;
        private final List<FermentationStatusEffectEntry.StatusEffectPotencyEntry> potencyEntries = new ArrayList<>();

        public FermentationStatusEffectEntryBuilder(
                MobEffect statusEffect,
                int baseDuration
        ) {
            this.statusEffect = statusEffect;
            this.baseDuration = baseDuration;
        }

        public FermentationStatusEffectEntryBuilder simplePotencyEntry(int potency) {
            new FermentationStatusEffectPotencyEntryBuilder(potency).submit();
            return this;
        }

        public FermentationStatusEffectPotencyEntryBuilder potencyEntry(int potency) {
            return new FermentationStatusEffectPotencyEntryBuilder(potency);
        }


        public TitrationBarrelRecipeBuilder submit() {
            statusEffectEntries.add(new FermentationStatusEffectEntry(this.statusEffect, this.baseDuration, this.potencyEntries));
            return TitrationBarrelRecipeBuilder.this;
        }

        public final class FermentationStatusEffectPotencyEntryBuilder {
            private float minAlc = 0.0f;
            private float minThickness = 0.0f;
            private final int potency;

            public FermentationStatusEffectPotencyEntryBuilder(int potency) {
                this.potency = potency;
            }

            public FermentationStatusEffectPotencyEntryBuilder minAlc(float value) {
                this.minAlc = value;
                return this;
            }

            public FermentationStatusEffectPotencyEntryBuilder minThickness(float value) {
                this.minThickness = value;
                return this;
            }


            public FermentationStatusEffectEntryBuilder submit() {
                potencyEntries.add(new FermentationStatusEffectEntry.StatusEffectPotencyEntry(this.minAlc, this.minThickness, this.potency));
                return FermentationStatusEffectEntryBuilder.this;
            }
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
                                this.ingredients.stream().map(TitrationBarrelRecipeBuilder::sizedToStack).collect(Collectors.toCollection(NonNullList::create)),
                                this.fluid,
                                this.result,
                                this.item,
                                this.minFermentationTimeHours,
                                new FermentationData(this.fermentationSpeedMod, this.angelsSharePercentPerMcDay, this.statusEffectEntries)
                        )
        );
    }
}

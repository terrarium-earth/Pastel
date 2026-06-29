package earth.terrarium.pastel.data.recipe.builder.fusion_shrine;

import earth.terrarium.pastel.api.predicate.location.CommandPredicate;
import earth.terrarium.pastel.api.predicate.location.MoonPhasePredicate;
import earth.terrarium.pastel.api.predicate.location.TimeOfDayPredicate;
import earth.terrarium.pastel.api.predicate.location.WeatherPredicate;
import earth.terrarium.pastel.api.predicate.location.WorldConditionsPredicate;
import earth.terrarium.pastel.api.recipe.FusionShrineRecipeWorldEffect;
import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.helpers.interaction.TimeHelper;
import earth.terrarium.pastel.recipe.fusion_shrine.FusionShrineRecipe;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LightPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class FusionShrineRecipeBuilder extends GatedRecipeBuilder<FusionShrineRecipeBuilder> {
    private final NonNullList<SizedIngredient> ingredients = NonNullList.create();

    private final FluidIngredient fluid;

    private float experience = 0.0f;

    private int craftingTime = 200;

    private boolean disableYieldUpgrades = false;

    private final List<WorldConditionsPredicate> worldConditionsPredicates = new ArrayList<>();

    private FusionShrineRecipeWorldEffect startCraftingEffect = FusionShrineRecipeWorldEffect.NOTHING;

    private FusionShrineRecipeWorldEffect finishCraftingEffect = FusionShrineRecipeWorldEffect.NOTHING;

    private final List<FusionShrineRecipeWorldEffect> duringCraftingEffects = new ArrayList<>();

    @Nullable private Component description = null;

    private boolean copyComponents = false;

    private boolean playFinishedCraftingEffects = true;

    public FusionShrineRecipeBuilder(
        FluidIngredient fluid,
        ItemStack result
    ) {
        super(result);
        this.fluid = fluid;
    }

    public FusionShrineRecipeBuilder(
        Fluid fluid,
        ItemStack result
    ) {
        this(FluidIngredient.of(fluid), result);
    }

    public FusionShrineRecipeBuilder experience(float value) {
        this.experience = value;
        return this;
    }

    public FusionShrineRecipeBuilder craftingTime(int value) {
        this.craftingTime = value;
        return this;
    }

    public FusionShrineRecipeBuilder requires(SizedIngredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public FusionShrineRecipeBuilder requires(Ingredient ingredient, int count) {
        return requires(new SizedIngredient(ingredient, count));
    }

    public FusionShrineRecipeBuilder requires(Ingredient ingredient) {
        return requires(ingredient, 1);
    }

    public FusionShrineRecipeBuilder requires(ItemLike item, int count) {
        return requires(Ingredient.of(item), count);
    }

    public FusionShrineRecipeBuilder requires(ItemLike item) {
        return requires(item, 1);
    }

    public FusionShrineRecipeBuilder requires(TagKey<Item> tag, int count) {
        return requires(Ingredient.of(tag), count);
    }

    public FusionShrineRecipeBuilder requires(TagKey<Item> tag) {
        return requires(tag, 1);
    }

    public FusionShrineRecipeBuilder disableYieldUpgrades() {
        this.disableYieldUpgrades = true;
        return this;
    }

    public WorldConditionBuilder worldCondition() {
        return new WorldConditionBuilder();
    }

    public FusionShrineRecipeBuilder dontPlayFinishEffects() {
        this.playFinishedCraftingEffects = false;
        return this;
    }

    public FusionShrineRecipeBuilder copyComponents() {
        this.copyComponents = true;
        return this;
    }

    public FusionShrineRecipeBuilder translateDescription(String langId) {
        this.description = Component.translatable(langId);
        return this;
    }

    public FusionShrineRecipeBuilder description(Component component) {
        this.description = component;
        return this;
    }

    public FusionShrineRecipeBuilder startCrafting(FusionShrineRecipeWorldEffect worldEffect) {
        this.startCraftingEffect = worldEffect;
        return this;
    }

    public FusionShrineRecipeBuilder finishCrafting(FusionShrineRecipeWorldEffect worldEffect) {
        this.finishCraftingEffect = worldEffect;
        return this;
    }

    public FusionShrineRecipeBuilder duringCrafting(FusionShrineRecipeWorldEffect worldEffect) {
        this.duringCraftingEffects.add(worldEffect);
        return this;
    }

    public FusionShrineRecipeBuilder padCraftEffect() {
        this.duringCraftingEffects.add(FusionShrineRecipeWorldEffect.NOTHING);
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        saveHelperGated(
            recipeOutput,
            id,
            daId -> new FusionShrineRecipe(
                this.group,
                this.secret,
                daId,
                ingredients,
                this.fluid,
                this.result,
                this.experience,
                this.craftingTime,
                this.disableYieldUpgrades,
                this.playFinishedCraftingEffects,
                this.copyComponents,
                this.worldConditionsPredicates,
                this.startCraftingEffect,
                this.duringCraftingEffects,
                this.finishCraftingEffect,
                Objects.requireNonNullElse(this.description, Component.empty())
            )
        );
    }

    public final class WorldConditionBuilder {
        @Nullable private MoonPhasePredicate moonPhase = null;

        @Nullable private TimeOfDayPredicate timeOfDay = null;

        @Nullable private WeatherPredicate weather = null;

        @Nullable private CommandPredicate command = null;

        @Nullable private HolderSet<Biome> biomes = null;

        @Nullable private HolderSet<Structure> structures = null;

        @Nullable private ResourceKey<Level> dimension = null;

        @Nullable private LightPredicate light = null;

        @Nullable private BlockPredicate block = null;

        @Nullable private FluidPredicate fluid = null;

        @Nullable private Boolean smokey = null;

        @Nullable private Boolean canSeeSky = null;

        public WorldConditionBuilder() {

        }

        public WorldConditionBuilder moonPhase(MoonPhasePredicate moonPhase) {
            this.moonPhase = moonPhase;
            return this;
        }

        public WorldConditionBuilder timeOfDay(TimeHelper.TimeOfDay name) {
            this.timeOfDay = new TimeOfDayPredicate(name, MinMaxBounds.Ints.between(name.from, name.to - 1));
            return this;
        }

        public WorldConditionBuilder timeOfDay(int minimum, int maximumInclusive) {
            this.timeOfDay = new TimeOfDayPredicate(null, MinMaxBounds.Ints.between(minimum, maximumInclusive));
            return this;
        }

        public WorldConditionBuilder timeOfDay(int exact) {
            this.timeOfDay = new TimeOfDayPredicate(null, MinMaxBounds.Ints.exactly(exact));
            return this;
        }

        public WorldConditionBuilder weather(WeatherPredicate weather) {
            this.weather = weather;
            return this;
        }

        public WorldConditionBuilder command(String command) {
            this.command = new CommandPredicate(command);
            return this;
        }

        // TODO: the rest (they are likely unused)

        public FusionShrineRecipeBuilder submit() {
            var built = new WorldConditionsPredicate(
                Optional.ofNullable(moonPhase),
                Optional.ofNullable(timeOfDay),
                Optional.ofNullable(weather),
                Optional.ofNullable(command),
                Optional.ofNullable(biomes),
                Optional.ofNullable(structures),
                Optional.ofNullable(dimension),
                Optional.ofNullable(light),
                Optional.ofNullable(block),
                Optional.ofNullable(fluid),
                Optional.ofNullable(smokey),
                Optional.ofNullable(canSeeSky)
            );

            worldConditionsPredicates.add(built);
            return FusionShrineRecipeBuilder.this;
        }
    }
}

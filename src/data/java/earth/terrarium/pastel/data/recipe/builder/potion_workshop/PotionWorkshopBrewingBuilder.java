package earth.terrarium.pastel.data.recipe.builder.potion_workshop;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.recipe.potion_workshop.PotionRecipeEffect;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopBrewingRecipe;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.Optional;

public final class PotionWorkshopBrewingBuilder extends PotionWorkshopRecipeBuilder<PotionWorkshopBrewingBuilder> {
    boolean applicableToPotions = true;
    boolean applicableToTippedArrows = true;
    boolean applicableToPotionFillables = true;
    boolean applicableToWeapons = true;
    int baseDurationTicks = 1600;
    float baseYield = (float) PotionWorkshopBrewingRecipe.BASE_POTION_COUNT_ON_BREWING;
    int potencyHardCap = -1;
    float potencyModifier = 1f;
    final Holder<MobEffect> statusEffect;
    final InkColor inkColor;
    final int inkCost;

    public PotionWorkshopBrewingBuilder(
            Holder<MobEffect> statusEffect,
            InkColor inkColor,
            int inkCost
    ) {
        super(ItemStack.EMPTY);
        this.statusEffect = statusEffect;
        this.inkColor = inkColor;
        this.inkCost = inkCost;
    }

    public PotionWorkshopBrewingBuilder notApplicableToPotions() {
        this.applicableToPotions = false;
        return this;
    }

    public PotionWorkshopBrewingBuilder notApplicableToTippedArrows() {
        this.applicableToTippedArrows = false;
        return this;
    }

    public PotionWorkshopBrewingBuilder notApplicableToPotionFillables() {
        this.applicableToPotionFillables = false;
        return this;
    }

    public PotionWorkshopBrewingBuilder notApplicableToWeapons() {
        this.applicableToWeapons = false;
        return this;
    }

    public PotionWorkshopBrewingBuilder baseDurationTicks(int value) {
        this.baseDurationTicks = value;
        return this;
    }

    public PotionWorkshopBrewingBuilder baseYield(float yield) {
        this.baseYield = yield;
        return this;
    }

    public PotionWorkshopBrewingBuilder potencyHardCap(int cap) {
        this.potencyHardCap = cap;
        return this;
    }

    public PotionWorkshopBrewingBuilder potencyModifier(float value) {
        this.potencyModifier = value;
        return this;
    }

    @Override
    public String getDefaultName() {
        return statusEffect.getKey().location().getPath();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var effect = new PotionRecipeEffect(
                applicableToPotions,
                applicableToTippedArrows,
                applicableToPotionFillables,
                applicableToWeapons,
                baseDurationTicks,
                baseYield,
                potencyHardCap,
                potencyModifier,
                statusEffect,
                inkColor,
                inkCost
        );
        var recipe = new PotionWorkshopBrewingRecipe(
                this.group,
                this.secret,
                this.getRequiredAdvancement(),
                // nothing overrides the crafting time?
                200,
                this.ingredient1,
                Optional.ofNullable(this.ingredient2),
                Optional.ofNullable(this.ingredient3),
                effect
        );

        saveHelper(recipeOutput, id, recipe);
    }
}

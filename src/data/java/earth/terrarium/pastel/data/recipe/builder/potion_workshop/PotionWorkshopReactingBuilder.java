package earth.terrarium.pastel.data.recipe.builder.potion_workshop;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.recipe.potion_workshop.PotionMod;
import earth.terrarium.pastel.recipe.potion_workshop.PotionRecipeEffect;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopReactingRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class PotionWorkshopReactingBuilder extends GatedRecipeBuilder<PotionWorkshopReactingBuilder> {
    private final Item item;
    private final List<PotionMod> modifiers = new ArrayList<>();

    public PotionWorkshopReactingBuilder(Item item) {
        super(ItemStack.EMPTY);
        this.item = item;
    }

    public PotionModBuilder modifier() {
        return new PotionModBuilder();
    }

    @Override
    public String getDefaultName() {
        return BuiltInRegistries.ITEM.getKey(this.item).getPath();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var recipe = new PotionWorkshopReactingRecipe(
                this.group,
                this.secret,
                this.getRequiredAdvancement(),
                this.item,
                this.modifiers
        );

        saveHelper(recipeOutput, id, recipe);
    }

    // NOTE: if this used scala (or even kotlin)
    // this could just be replaced with a function with default arguments
    public class PotionModBuilder {
        int flatDurationBonusTicks = 0;

        float flatPotencyBonus = 0.0F;

        float durationMultiplier = 1.0F;

        float potencyMultiplier = 1.0F;

        float flatPotencyBonusPositiveEffects = 0.0F;

        float flatPotencyBonusNegativeEffects = 0.0F;

        int flatDurationBonusPositiveEffects = 0;

        int flatDurationBonusNegativeEffects = 0;

        float additionalRandomPositiveEffectCount = 0;

        float additionalRandomNegativeEffectCount = 0;

        float chanceToAddLastEffect = 0.0F;

        float lastEffectDurationMultiplier = 1.0F;

        float lastEffectPotencyMultiplier = 1.0F;

        float yield = 0;

        int additionalDrinkDurationTicks = 0;

        PotionMod.PotionFlags flags = new PotionMod.PotionFlags(
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                List.of()
        );

        PotionModBuilder() {

        }

        public PotionModBuilder flatDurationBonusTicks(int value) {
            this.flatDurationBonusTicks = value;
            return this;
        }

        public PotionModBuilder flatPotencyBonus(float value) {
            this.flatPotencyBonus = value;
            return this;
        }

        public PotionModBuilder durationMultiplier(float value) {
            this.durationMultiplier = value;
            return this;
        }

        public PotionModBuilder potencyMultiplier(float value) {
            this.potencyMultiplier = value;
            return this;
        }

        public PotionModBuilder flatPotencyBonusPositiveEffects(float value) {
            this.flatPotencyBonusPositiveEffects = value;
            return this;
        }

        public PotionModBuilder flatPotencyBonusNegativeEffects(float value) {
            this.flatPotencyBonusNegativeEffects = value;
            return this;
        }

        public PotionModBuilder flatDurationBonusPositiveEffects(int value) {
            this.flatDurationBonusPositiveEffects = value;
            return this;
        }

        public PotionModBuilder flatDurationBonusNegativeEffects(int value) {
            this.flatDurationBonusNegativeEffects = value;
            return this;
        }

        public PotionModBuilder additionalRandomPositiveEffectCount(float value) {
            this.additionalRandomPositiveEffectCount = value;
            return this;
        }

        public PotionModBuilder additionalRandomNegativeEffectCount(float value) {
            this.additionalRandomNegativeEffectCount = value;
            return this;
        }

        public PotionModBuilder chanceToAddLastEffect(float value) {
            this.chanceToAddLastEffect = value;
            return this;
        }

        public PotionModBuilder lastEffectDurationMultiplier(float value) {
            this.lastEffectDurationMultiplier = value;
            return this;
        }

        public PotionModBuilder lastEffectPotencyMultiplier(float value) {
            this.lastEffectPotencyMultiplier = value;
            return this;
        }

        public PotionModBuilder yield(float value) {
            this.yield = value;
            return this;
        }

        public PotionModBuilder additionalDrinkDurationTicks(int value) {
            this.additionalDrinkDurationTicks = value;
            return this;
        }

        public PotionFlagsBuilder flags() {
            return new PotionFlagsBuilder();
        }

        public class PotionFlagsBuilder {
            boolean makeSplashing = false;
            boolean makeLingering = false;
            boolean noParticles = false;
            boolean unidentifiable = false;
            boolean makeEffectsPositive = false;
            boolean potentDecreasingEffect = false;
            boolean negateDecreasingDuration = false;
            boolean randomColor = false;
            boolean incurable = false;
            final List<Tuple<PotionRecipeEffect, Float>> additionalEffects = new ArrayList<>();

            public PotionFlagsBuilder makeSplashing() {
                this.makeSplashing = true;
                return this;
            }

            public PotionFlagsBuilder makeLingering() {
                this.makeLingering = true;
                return this;
            }

            public PotionFlagsBuilder noParticles() {
                this.noParticles = true;
                return this;
            }

            public PotionFlagsBuilder unidentifiable() {
                this.unidentifiable = true;
                return this;
            }

            public PotionFlagsBuilder makeEffectsPositive() {
                this.makeEffectsPositive = true;
                return this;
            }

            public PotionFlagsBuilder potentDecreasingEffect() {
                this.potentDecreasingEffect = true;
                return this;
            }

            public PotionFlagsBuilder negateDecreasingDuration() {
                this.negateDecreasingDuration = true;
                return this;
            }

            public PotionFlagsBuilder randomColor() {
                this.randomColor = true;
                return this;
            }

            public PotionFlagsBuilder incurable() {
                this.incurable = true;
                return this;
            }

            public PotionModBuilder submit() {
                flags = new PotionMod.PotionFlags(
                        this.makeSplashing,
                        this.makeLingering,
                        this.noParticles,
                        this.unidentifiable,
                        this.makeEffectsPositive,
                        this.potentDecreasingEffect,
                        this.negateDecreasingDuration,
                        this.randomColor,
                        this.incurable,
                        this.additionalEffects
                );
                return PotionModBuilder.this;
            }

            public PotionRecipeEffectBuilder additionalEffect(Holder<MobEffect> effect, float chance, InkColor color, int inkCost) {
                return new PotionRecipeEffectBuilder(chance, color, inkCost, effect);
            }

            // turtles all the way down
            // only used by nectardew burgeon?
            public class PotionRecipeEffectBuilder {
                final float chance;
                final InkColor color;
                int baseDurationTicks = 1600;
                final Holder<MobEffect> effect;
                float potencyMod = 1.0f;
                int potencyHardCap = -1;
                final int inkCost;

                PotionRecipeEffectBuilder(float chance, InkColor color, int inkCost, Holder<MobEffect> effect) {
                    this.chance = chance;
                    this.color = color;
                    this.effect = effect;
                    this.inkCost = inkCost;

                }

                public PotionRecipeEffectBuilder baseDurationTicks(int value) {
                    this.baseDurationTicks = value;
                    return this;
                }

                public PotionRecipeEffectBuilder potencyModifier(float value) {
                    this.potencyMod = value;
                    return this;
                }

                public PotionRecipeEffectBuilder potencyHardCap(int value) {
                    this.potencyHardCap = value;
                    return this;
                }

                public PotionFlagsBuilder submit() {
                    var effect = new PotionRecipeEffect(
                            true,
                            true,
                            true,
                            true,
                            this.baseDurationTicks,
                            // unused in reacting?
                            3.0f,
                            this.potencyHardCap,
                            this.potencyMod,
                            this.effect,
                            this.color,
                            this.inkCost
                    );
                    additionalEffects.add(new Tuple<>(effect, this.chance));

                    return PotionFlagsBuilder.this;
                }
            }
        }

        public PotionWorkshopReactingBuilder submit() {
            modifiers.add(new PotionMod(
                    flatDurationBonusTicks,
                    flatPotencyBonus,
                    durationMultiplier,
                    potencyMultiplier,
                    flatPotencyBonusPositiveEffects,
                    flatPotencyBonusNegativeEffects,
                    flatDurationBonusPositiveEffects,
                    flatDurationBonusNegativeEffects,
                    additionalRandomPositiveEffectCount,
                    additionalRandomNegativeEffectCount,
                    chanceToAddLastEffect,
                    lastEffectDurationMultiplier,
                    lastEffectPotencyMultiplier,
                    yield,
                    additionalDrinkDurationTicks,
                    flags
            ));
            return PotionWorkshopReactingBuilder.this;
        }
    }
}

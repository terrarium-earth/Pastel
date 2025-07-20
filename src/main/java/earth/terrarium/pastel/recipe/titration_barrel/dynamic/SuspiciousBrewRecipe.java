package earth.terrarium.pastel.recipe.titration_barrel.dynamic;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import earth.terrarium.pastel.components.BeverageComponent;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.interaction.TimeHelper;
import earth.terrarium.pastel.recipe.FluidRecipeInput;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationData;
import earth.terrarium.pastel.recipe.titration_barrel.TitrationBarrelRecipe;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SuspiciousBrewRecipe extends TitrationBarrelRecipe {

    public static final Item TAPPING_ITEM = Items.GLASS_BOTTLE;
    public static final int MIN_FERMENTATION_TIME_HOURS = 4;
    public static final ItemStack OUTPUT_STACK = getDefaultStackWithCount(PastelItems.SUSPICIOUS_BREW.get(), 4);
    public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("unlocks/food/suspicious_brew");
    public static final List<IngredientStack> INGREDIENT_STACKS = new ArrayList<>() {{
        add(IngredientStack.ofTag(ItemTags.SMALL_FLOWERS, 1));
        add(IngredientStack.ofTag(ItemTags.SMALL_FLOWERS, 1));
        add(IngredientStack.ofTag(ItemTags.SMALL_FLOWERS, 1));
        add(IngredientStack.ofTag(ItemTags.SMALL_FLOWERS, 1));
    }};

    public SuspiciousBrewRecipe() {
        super(
            "", false, Optional.of(UNLOCK_IDENTIFIER), INGREDIENT_STACKS, FluidIngredient.of(Fluids.WATER),
            OUTPUT_STACK, TAPPING_ITEM, MIN_FERMENTATION_TIME_HOURS, new FermentationData(1.25F, 0.01F, List.of())
        );
    }

    @Override
    public ItemStack getPreviewTap(int timeMultiplier) {
        ItemStack flowerStack = Items.POPPY.getDefaultInstance();
        flowerStack.setCount(4);
        return tapWith(List.of(flowerStack), 1.0F, this.minFermentationTimeHours * 60L * 60L * timeMultiplier, 0.4F);
    }

    @Override
    public ItemStack tap(FriendlyStackHandler inventory, long secondsFermented, float downfall) {
        List<ItemStack> stacks = new ArrayList<>();
        int itemCount = 0;
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stacks.add(stack);
                itemCount += stack.getCount();
            }
        }
        float thickness = getThickness(itemCount);
        return tapWith(stacks, thickness, secondsFermented, downfall);
    }

    public ItemStack tapWith(List<ItemStack> stacks, float thickness, long secondsFermented, float downfall) {
        float ageIngameDays = TimeHelper.minecraftDaysFromSeconds(secondsFermented);
        double alcPercent = getAlcPercent(
            this.fermentationData.fermentationSpeedMod(), thickness, downfall, ageIngameDays);
        if (alcPercent >= 100) {
            return PastelItems.PURE_ALCOHOL.get()
                                           .getDefaultInstance();
        } else {
            // add up all stew effects with their durations from the input stacks
            var stewEffects = new HashMap<Holder<MobEffect>, Double>();
            for (var stack : stacks) {
                var stewEffectsComponent = SuspiciousStewEffects.EMPTY;
                if (stack.getItem() instanceof SuspiciousEffectHolder sussyBakka) // IN THIS WORL YOU ARE EITHER A
                    // SUSSY BAKKA OR A BUSSY SUKKA
                    stewEffectsComponent = sussyBakka.getSuspiciousEffects();

                for (var effect : stewEffectsComponent.effects()) {
                    var key = effect.effect();
                    var duration = effect.duration() * (Support.logBase(2, 1 + stack.getCount()));
                    stewEffects.put(key, stewEffects.getOrDefault(key, 0d) + duration);
                }
            }

            var finalStatusEffects = new ArrayList<MobEffectInstance>();
            double clampedAlcPercent = Mth.clamp(
                alcPercent, 1D,
                20D
            ); // a too high number will cause issues with the effects length exceeding the integer limit, lol
            for (var entry : stewEffects.entrySet()) {
                var finalDurationTicks = entry.getValue() * Math.pow(2, clampedAlcPercent);
                finalStatusEffects.add(new MobEffectInstance(entry.getKey(), (int) finalDurationTicks, 0));
            }

            ItemStack outputStack = OUTPUT_STACK.copy();
            outputStack.setCount(1);
            outputStack.set(
                PastelDataComponentTypes.BEVERAGE,
                new BeverageComponent((long) ageIngameDays, (int) alcPercent, thickness)
            );
            outputStack.set(
                DataComponents.POTION_CONTENTS,
                new PotionContents(Optional.empty(), Optional.empty(), finalStatusEffects)
            );
            return outputStack;
        }
    }

    @Override
    public boolean matches(FluidRecipeInput<FluidTank> recipeInput, Level world) {
        boolean flowerFound = false;
        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof FlowerBlock) {
                    flowerFound = true;
                } else {
                    return false;
                }
            }
        }

        return flowerFound;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.TITRATION_BARREL_SUSPICIOUS_BREW;
    }

}

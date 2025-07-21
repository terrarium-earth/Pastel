package earth.terrarium.pastel.recipe.titration_barrel.dynamic;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.recipe.FluidRecipeInput;
import earth.terrarium.pastel.recipe.titration_barrel.FermentationData;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelMobEffects;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JadeWineRecipe extends SweetenableTitrationBarrelRecipe {

    public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("unlocks/food/jade_wine");
    public static final int MIN_FERMENTATION_TIME_HOURS = 24;
    public static final ItemStack OUTPUT_STACK = getDefaultStackWithCount(PastelItems.JADE_WINE.get(), 4);
    public static final Item TAPPING_ITEM = Items.GLASS_BOTTLE;
    public static final List<IngredientStack> INGREDIENT_STACKS = new ArrayList<>() {{
        add(IngredientStack.ofItems(PastelItems.GERMINATED_JADE_VINE_BULB.get()));
        add(IngredientStack.ofItems(PastelItems.JADE_VINE_PETALS.get(), 3));
    }};

    public JadeWineRecipe() {
        super(
            "", false, Optional.of(UNLOCK_IDENTIFIER), INGREDIENT_STACKS, FluidIngredient.of(Fluids.WATER),
            OUTPUT_STACK, TAPPING_ITEM, MIN_FERMENTATION_TIME_HOURS, new FermentationData(0.075F, 0.01F, List.of())
        );
    }

    @Override
    public ItemStack tap(FriendlyStackHandler inventory, long secondsFermented, float downfall) {
        int bulbCount = InventoryHelper.getItemCountInInventory(inventory, PastelItems.GERMINATED_JADE_VINE_BULB.get());
        int petalCount = InventoryHelper.getItemCountInInventory(inventory, PastelItems.JADE_VINE_PETALS.get());
        boolean nectar = InventoryHelper.getItemCountInInventory(inventory, PastelItems.MOONSTRUCK_NECTAR.get()) > 0;

        float thickness = getThickness(bulbCount, petalCount);
        return tapWith(bulbCount, petalCount, nectar, thickness, secondsFermented, downfall);
    }

    @Override
    protected @NotNull List<MobEffectInstance> getEffects(boolean nectar, double bloominess, double alcPercent) {
        List<MobEffectInstance> effects = new ArrayList<>();

        int effectDuration = 1200;
        if (alcPercent >= 80) {
            effects.add(new MobEffectInstance(PastelMobEffects.PROJECTILE_REBOUND, effectDuration));
            effectDuration *= 2;
        }
        if (alcPercent >= 70) {
            effects.add(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, effectDuration));
            effectDuration *= 2;
        }
        if (alcPercent >= 60) {
            effects.add(new MobEffectInstance(MobEffects.DIG_SPEED, effectDuration));
            effectDuration *= 2;
        }
        if (alcPercent >= 40) {
            effects.add(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, effectDuration));
            effectDuration *= 2;
        }
        if (alcPercent >= 20) {
            effects.add(new MobEffectInstance(PastelMobEffects.NOURISHING, effectDuration));
            effectDuration *= 2;
        }
        if (nectar) {
            effects.add(new MobEffectInstance(PastelMobEffects.IMMUNITY, effectDuration));
        }

        int nectarMod = nectar ? 3 : 1;
        effectDuration = 1200;
        int alcAfterBloominess = (int) (alcPercent / (nectarMod + bloominess));
        if (alcAfterBloominess >= 40) {
            effects.add(new MobEffectInstance(MobEffects.BLINDNESS, effectDuration));
            effectDuration *= 2;
        }
        if (alcAfterBloominess >= 30) {
            effects.add(new MobEffectInstance(MobEffects.POISON, effectDuration));
            effectDuration *= 2;
        }
        if (alcAfterBloominess >= 20) {
            effects.add(new MobEffectInstance(MobEffects.CONFUSION, effectDuration));
            effectDuration *= 2;
        }
        if (alcAfterBloominess >= 10) {
            effects.add(new MobEffectInstance(MobEffects.WEAKNESS, effectDuration));
        }
        return effects;
    }

    @Override
    public boolean matches(FluidRecipeInput<FluidTank> recipeInput, Level world) {
        boolean bulbsFound = false;

        for (int i = 0; i < recipeInput.size(); i++) {
            ItemStack stack = recipeInput.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (stack.is(PastelItems.GERMINATED_JADE_VINE_BULB.get())) {
                bulbsFound = true;
            } else if (!stack.is(PastelItems.JADE_VINE_PETALS.get()) && !stack.is(
                PastelItems.MOONSTRUCK_NECTAR.get())) {
                return false;
            }
        }

        return bulbsFound;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.TITRATION_BARREL_JADE_WINE;
    }

}
